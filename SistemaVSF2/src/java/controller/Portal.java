/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import org.joda.time.LocalDate;

/**
 *
 * @author Andre
 */
@WebServlet(name = "Portal", urlPatterns = {"/Portal"})
public class Portal extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher rd = request.getRequestDispatcher("");
        String action = request.getParameter("action");
        Conta conta = (Conta) session.getAttribute("conta");
        Cliente cliente = (Cliente) session.getAttribute("cliente");

        ClienteDAO daoCliente = new ClienteDAO();
        ContaDAO daoConta = new ContaDAO();
        TransacaoDAO daoTrans = new TransacaoDAO();
        Date dataAtual = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dataAtual);
        dataAtual.setTime(c.getTime().getTime());

        Transacao trans = new Transacao();
        Conta contaRecebeTransf;
        Cliente clienteRecebeTransf;
        ArrayList<Conta> contas;
        ArrayList<Transacao> transacoes;

        if (cliente == null) {
            request.setAttribute("msg", "Não há nenhuma sessão inicializada.");
            rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }
        if ("extratos".equals(action)) {
            int periodo = request.getParameter("extrato") == null || request.getParameter("extrato").isEmpty() ? 0 : Integer.parseInt(request.getParameter("extrato"));
            if (periodo == 30 || periodo == 15) {
                transacoes = daoConta.extrato(periodo, conta.getId());
            } else {
                transacoes = daoConta.extrato(conta.getId());
            }
            request.setAttribute("msg", "");
            request.setAttribute("transacoes", transacoes);
            rd = getServletContext().getRequestDispatcher("/extratos.jsp");
        } else if ("todasContas".equals(action)) {
            contas = daoConta.pegarTodasContasByCliente(cliente);
            if (contas.size() > 0) {
                session.setAttribute("contas", contas);
            }
            rd = getServletContext().getRequestDispatcher("/todasContas.jsp");

        } else if ("sacar".equals(action)) {
            double valor = Double.parseDouble(request.getParameter("valor"));
            if (!verificaSaldo(conta, valor)) {
                request.setAttribute("msg", "Valor de saque é maior que o saldo e limite disponíveis.");
                rd = getServletContext().getRequestDispatcher("/saques.jsp");
            } else {
                conta = descontoSaldo(conta, valor);
                //verificaDataNegativacao(conta, dataAtual);
                daoConta.sacar(conta);

                trans.setTipoTransacao(4);
                trans.setValor(valor);
                trans.setDataTransacao(new java.sql.Date(dataAtual.getTime()));
                trans.setIdConta1(conta.getId());
                trans.setIdConta2(0);
                trans.setSaldoConta(conta.getSaldo());
                daoTrans.salvarTransacao(trans);
                session.setAttribute("conta", conta);
                request.setAttribute("msg", "Saque realizado com sucesso, motoboy passará receber o dinheiro");
                rd = getServletContext().getRequestDispatcher("/saques.jsp");
            }
        } else if ("depositar".equals(action)) {
            double valor = Double.parseDouble(request.getParameter("valor"));
            String agenciaDeposito = request.getParameter("agenciaDeposito");
            int contaDeposito = Integer.parseInt(request.getParameter("contaDeposito"));
            contaRecebeTransf = daoConta.pegarContaByConta(agenciaDeposito, contaDeposito);

            if (contaRecebeTransf == null || !contaRecebeTransf.getStatusConta()) {
                request.setAttribute("msg", "Conta informada para depósito inexistente ou inativa.");
            } else if (conta.getNumAgencia().equals(contaRecebeTransf.getNumAgencia()) && conta.getNumConta() == contaRecebeTransf.getNumConta()) {
                conta = contaRecebeValor(conta, valor);
                contaRecebeTransf = null;
                //verificaDataNegativacao(conta, dataAtual);
                daoConta.depositar(conta, contaRecebeTransf);

                trans.setTipoTransacao(1);
                trans.setValor(valor);
                trans.setDataTransacao(new java.sql.Date(dataAtual.getTime()));
                trans.setIdConta1(conta.getId());
                trans.setIdConta2(conta.getId());
                trans.setSaldoConta(conta.getSaldo());
                daoTrans.salvarTransacao(trans);
                session.setAttribute("conta", conta);
                request.setAttribute("msg", "Depósito realizado com sucesso, motoboy passará receber o dinheiro");
            } else if (!verificaSaldo(conta, valor)) {
                request.setAttribute("msg", "Valor de depósito é maior que o saldo e limite disponíveis.");
            } else {
                conta = descontoSaldo(conta, valor);
                contaRecebeTransf = contaRecebeValor(contaRecebeTransf, valor);
                //conta = verificaDataNegativacao(conta, dataAtual);
                //contaRecebeTransf =verificaDataNegativacao(contaRecebeTransf, dataAtual);
                daoConta.depositar(conta, contaRecebeTransf);

                trans.setTipoTransacao(1);
                trans.setValor(valor);
                trans.setDataTransacao(new java.sql.Date(dataAtual.getTime()));
                trans.setIdConta1(conta.getId());
                trans.setIdConta2(contaRecebeTransf.getId());
                trans.setSaldoConta(conta.getSaldo());
                daoTrans.salvarTransacao(trans);
                session.setAttribute("conta", conta);
                request.setAttribute("msg", "Depósito realizado com sucesso, motoboy passará receber o dinheiro");
            }
            rd = getServletContext().getRequestDispatcher("/depositos.jsp");

        } else if ("transferir".equals(action)) {
            double valor = Double.parseDouble(request.getParameter("valor"));
            if (!verificaSaldo(conta, valor)) {
                request.setAttribute("msg", "Valor de transferência maior que o saldo e limite disponíveis.");
                rd = getServletContext().getRequestDispatcher("/transferencias.jsp");
                rd.forward(request, response);
            } else {
                String agenciaTransferencia = request.getParameter("contaTransferir").substring(0, 4);
                int contaTransferencia = Integer.parseInt(request.getParameter("contaTransferir").substring(5));
                contaRecebeTransf = daoConta.pegarContaByConta(agenciaTransferencia, contaTransferencia);
                if (contaRecebeTransf == null || !contaRecebeTransf.getStatusConta()) {
                    request.setAttribute("msg", "Conta informada para transferência inexistente ou inativa.");
                    rd = getServletContext().getRequestDispatcher("/transferencias.jsp");
                    rd.forward(request, response);
                } else {
                    conta = descontoSaldo(conta, valor);
                    contaRecebeTransf = contaRecebeValor(contaRecebeTransf, valor);
                    daoConta.transferir(conta, contaRecebeTransf);
                    //verificaDataNegativacao(conta, dataAtual);
                    trans.setTipoTransacao(2);
                    trans.setValor(valor);
                    trans.setDataTransacao(new java.sql.Date(dataAtual.getTime()));
                    trans.setIdConta1(conta.getId());
                    trans.setIdConta2(0);
                    trans.setSaldoConta(conta.getSaldo());
                    daoTrans.salvarTransacao(trans);
                    session.setAttribute("conta", conta);
                    request.setAttribute("msg", "Transferência realizada com sucesso");
                    rd = getServletContext().getRequestDispatcher("/transferencias.jsp");
                }
            }
        } else if ("confirmaTransferencia".equals(action)) {
            double valor = Double.parseDouble(request.getParameter("valor"));
            if (!verificaSaldo(conta, valor)) {
                request.setAttribute("msg", "Valor de transferência maior que o saldo e limite disponíveis.");
            } else {
                String agenciaDestino = request.getParameter("agenciaDestino");
                int contaDestino = Integer.parseInt(request.getParameter("contaDestino"));

                contaRecebeTransf = daoConta.pegarContaByConta(agenciaDestino, contaDestino);
                clienteRecebeTransf = daoCliente.clienteByConta(agenciaDestino, contaDestino);

                request.setAttribute("contaRecebeTransf", contaRecebeTransf);
                request.setAttribute("valorTransferencia", valor);
                request.setAttribute("clienteRecebeTransf", clienteRecebeTransf);
            }
            rd = getServletContext().getRequestDispatcher("/transfTerceiros.jsp");

        } else if ("transferirTerceiros".equals(action)) {
            double valor = Double.parseDouble(request.getParameter("valorTransferencia"));
            String agenciaDestino = request.getParameter("agenciaContaDestino").substring(0, 4);
            int contaDestino = Integer.parseInt(request.getParameter("agenciaContaDestino").substring(5));
//                String agenciaDestino = request.getParameter("agenciaDestino");
//                int contaDestino = Integer.parseInt(request.getParameter("contaDestino"));
            contaRecebeTransf = daoConta.pegarContaByConta(agenciaDestino, contaDestino);
            if (contaRecebeTransf == null || !contaRecebeTransf.getStatusConta()) {
                request.setAttribute("msg", "Conta informada para transferência inexistente ou inativa.");
            } else {
                conta = descontoSaldo(conta, valor);
                contaRecebeTransf = contaRecebeValor(contaRecebeTransf, valor);
                daoConta.transferir(conta, contaRecebeTransf);
                //verificaDataNegativacao(conta, dataAtual);
                trans.setTipoTransacao(3);
                trans.setValor(valor);
                trans.setDataTransacao(new java.sql.Date(dataAtual.getTime()));
                trans.setIdConta1(conta.getId());
                trans.setIdConta2(contaRecebeTransf.getId());
                trans.setSaldoConta(conta.getSaldo());
                daoTrans.salvarTransacao(trans);
                session.setAttribute("conta", conta);
                request.setAttribute("msg", "Transferência realizada com sucesso");
            }

            rd = getServletContext().getRequestDispatcher("/transfTerceiros.jsp");
        } else if ("encerrar".equals(action)) {
            conta.setStatusConta(Boolean.FALSE);
            daoConta.encerrarConta(conta);

            trans.setTipoTransacao(5);
            trans.setValor(0);
            trans.setDataTransacao(new java.sql.Date(dataAtual.getTime()));
            trans.setIdConta1(conta.getId());
            trans.setIdConta2(0);
            trans.setSaldoConta(conta.getSaldo());
            daoTrans.salvarTransacao(trans);

            session.removeAttribute("conta");
            request.setAttribute("msg", "Conta encerrada com sucesso!");
            rd = getServletContext().getRequestDispatcher("/index.jsp");
        }
        rd.forward(request, response);
    }

    public Boolean verificaSaldo(Conta conta, double valor) {
        double valorFinal = conta.getSaldo() - valor;
        double limiteN = -conta.getLimite();
        if (valorFinal < limiteN) {
            return false;
        } else if (valor > conta.getSaldo() + conta.getLimite()) {
            return false;
        } else {
            return true;
        }
    }

    public Conta verificaDataNegativacao(Conta conta, Date dataAtual) {
        if (conta.getSaldo() < 0 && conta.getDataNegativacao() == null) {
            conta.setDataNegativacao(new java.util.Date(dataAtual.getTime()));
        } else {
            conta.setDataNegativacao(null);
        }
        return conta;
    }

    public Conta descontoSaldo(Conta conta, double valor) {
        double valorFinal = conta.getSaldo() - valor;
        conta.setSaldo(valorFinal);
        return conta;
    }

    public Conta contaRecebeValor(Conta contaRecebe, double valor) {
        double valorFinal = contaRecebe.getSaldo() + valor;
        contaRecebe.setSaldo(valorFinal);
        return contaRecebe;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
