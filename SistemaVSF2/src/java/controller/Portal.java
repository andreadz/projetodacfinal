/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;

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
        
        ContaDAO daoConta = new ContaDAO();
        TransacaoDAO daoTrans = new TransacaoDAO();
        
        Conta contaRecebeTransf;
        ArrayList<Conta> contas;
        ArrayList<Transacao> transacoes;
        
        if (cliente == null) {
            request.setAttribute("msg", "Não há nenhuma sessão inicializada.");
            rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }
        if ("extratos".equals(action)) {
            int periodo = request.getParameter("extrato") == null || request.getParameter("extrato").isEmpty() ? 0 : Integer.parseInt(request.getParameter("extrato"));
            if (periodo == 30) {
                transacoes = daoTrans.pegarTransacoes(periodo, conta.getId());
            } else if (periodo == 15) {
                transacoes = daoTrans.pegarTransacoes(periodo, conta.getId());
            } else {
                transacoes = daoTrans.pegarTransacoes(conta.getId());
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
            contas = daoConta.pegarTodasContasByCliente(cliente);
            if (contas.size() > 0) {
                session.setAttribute("contas", contas);
            }
            rd = getServletContext().getRequestDispatcher("/todasContas.jsp");
        } else if ("depositar".equals(action)) {
            contas = daoConta.pegarTodasContasByCliente(cliente);
            if (contas.size() > 0) {
                session.setAttribute("contas", contas);
            }
            rd = getServletContext().getRequestDispatcher("/todasContas.jsp");
        } else if ("transferir".equals(action)) {
            double valor = Double.parseDouble(request.getParameter("valor"));
            if (!verificaSaldo(conta, valor)) {
                request.setAttribute("msg", "Valor de transferência maior que o saldo e limite disponíveis.");
                rd = getServletContext().getRequestDispatcher("/transferencias.jsp");
                rd.forward(request, response);
            }               
            String agenciaTransferencia = request.getParameter("contaTransferir").substring(0, 3);
            int contaTransferencia = Integer.parseInt(request.getParameter("contaTransferir").substring(5));
            contaRecebeTransf = daoConta.pegarContaByConta(agenciaTransferencia, contaTransferencia);
            if(contaRecebeTransf == null || !contaRecebeTransf.getStatusConta()){
                request.setAttribute("msg", "Conta informada para transferência inexistente.");
                rd = getServletContext().getRequestDispatcher("/transferencias.jsp");
                rd.forward(request, response);
            }
            conta = operacoesSaqueTransf(conta, valor);      
            contaRecebeTransf = contaRecebeValor(contaRecebeTransf, valor);
            daoConta.transferir(conta, contaRecebeTransf);
            request.setAttribute("msg", "Transferência realizada com sucesso");
            rd = getServletContext().getRequestDispatcher("/transferencias.jsp");

        } else if ("transferirTerceiros".equals(action)) {
            double valor = Double.parseDouble(request.getParameter("valor"));
            if (!verificaSaldo(conta, valor)) {
                request.setAttribute("msg", "Valor de transferência maior que o saldo e limite disponíveis.");
                rd = getServletContext().getRequestDispatcher("/transferencias.jsp");
                rd.forward(request, response);
            }
            String agenciaDestino = request.getParameter("agenciaDestino");
            int contaDestino = Integer.parseInt(request.getParameter("contaDestino"));
            contaRecebeTransf = daoConta.pegarContaByConta(agenciaDestino, contaDestino);
            if(contaRecebeTransf == null || !contaRecebeTransf.getStatusConta()){
                request.setAttribute("msg", "Conta informada para transferência inexistente ou inativa.");
                rd = getServletContext().getRequestDispatcher("/transferencias.jsp");
                rd.forward(request, response);
            }
            conta = operacoesSaqueTransf(conta, valor);      
            contaRecebeTransf = contaRecebeValor(contaRecebeTransf, valor);
            daoConta.transferir(conta, contaRecebeTransf);
            request.setAttribute("msg", "Transferência realizada com sucesso");
            rd = getServletContext().getRequestDispatcher("/transfTerceiros.jsp");
        } 
        rd.forward(request, response);
    }

    public Boolean verificaSaldo(Conta conta, double valor) {
        if (valor > conta.getSaldo() + conta.getLimite()) {
            return false;
        }
        return true;
    }
    public Conta operacoesSaqueTransf(Conta conta, double valor) {
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
