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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        int quantidadeDias;

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
        } //        else if ("todasContas".equals(action)) {
        //            contas = daoConta.pegarTodasContasByCliente(cliente);
        //            if (contas.size() > 0) {
        //                session.setAttribute("contas", contas);
        //            }
        //            rd = getServletContext().getRequestDispatcher("/todasContas.jsp");
        //
        //        } 
        else if ("sacar".equals(action)) {
            double valor = Double.parseDouble(request.getParameter("valor"));
            if (!verificaSaldo(conta, valor)) {
                request.setAttribute("msg", "Valor de saque é maior que o saldo e limite disponíveis.");
                rd = getServletContext().getRequestDispatcher("/saques.jsp");
            } else {
                conta = descontoSaldo(conta, valor);
                //verificaDataNegativacao(conta, dataAtual);
                conta = daoConta.sacar(conta);

                trans.setTipoTransacao(4);
                trans.setValor(valor);
                trans.setDataTransacao(new java.sql.Date(dataAtual.getTime()));
                trans.setIdConta1(conta.getId());
                trans.setIdConta2(0);
                trans.setSaldoConta(conta.getSaldo());
                daoTrans.salvarTransacao(trans);
                session.setAttribute("conta", conta);
                request.setAttribute("msg", "Saque realizado com sucesso, motoboy passará receber o dinheiro");
                contas = (ArrayList<Conta>) session.getAttribute("contas");
                //quantidadeDias = daoConta.verificaStatusDOR(cliente, conta);
                ArrayList<Integer> quantidades = daoConta.verificaStatusDOR(cliente);
                if (contas.size() > 1) {
                    for (int i = 0; i < quantidades.size(); i++) {
                        if (quantidades.get(i) >= 10) {
                            cliente = insereClienteDOR(conta.getCliente());
                            if (!cliente.getStatusDOR()) {
                                request.setAttribute("mensagemDOR", "Erro ao incluir cliente no sistema DOR.");
                            } else {
                                request.setAttribute("mensagemDOR", "Você está incluso na lista de Devedores do DOR.");
                            }
                            i = quantidades.size();
                        }
                    }
                } else {
                    for (int i = 0; i < quantidades.size(); i++) {
                        if (quantidades.get(i) >= 10) {
                            cliente = insereClienteDOR(conta.getCliente());
                            if (!cliente.getStatusDOR()) {
                                request.setAttribute("mensagemDOR", "Erro ao incluir cliente no sistema DOR.");
                            } else {
                                request.setAttribute("mensagemDOR", "Você está incluso na lista de Devedores do DOR.");
                            }
                            i = quantidades.size();
                        }
                    }
                }
                //fazer verificar no BANCO de dados se SALDO<0 AND dataNegativacao BETWEEN  dataAtual-10 AND dataAtual
                //se houver, então negativa no DOR, senão não faz nada.
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
                
                //quantidadeDias = daoConta.verificaStatusDOR(cliente, conta);
                if (conta.getSaldo() >= 0) {
                    boolean verifica = liberaClienteDOR(cliente);
                    if (verifica) {
                        request.setAttribute("mensagemDOR", "Você foi removido da lista de Devedores do DOR após depósito.");
                        session.setAttribute("conta", conta);
                    }
                }
                
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
               
                quantidadeDias = daoConta.verificaStatusDOR(cliente, conta);
                if (quantidadeDias >= 10) {
                    cliente = insereClienteDOR(conta.getCliente());
                    if (!cliente.getStatusDOR()) {
                        request.setAttribute("mensagemDOR", "Erro ao incluir cliente no sistema DOR.");
                    } else {
                        request.setAttribute("mensagemDOR", "Você está incluso na lista de Devedores do DOR.");
                    }
                }
                clienteRecebeTransf = daoCliente.clienteByConta(contaRecebeTransf.getNumAgencia(), contaRecebeTransf.getNumConta());
                //quantidadeDias = daoConta.verificaStatusDOR(clienteRecebeTransf, contaRecebeTransf);

                if (contaRecebeTransf.getSaldo() >= 0) {
                    boolean verifica = liberaClienteDOR(cliente);
                    if (verifica) {
                        request.setAttribute("mensagemDOR", "Cliente que recebeu depósito foi removido da lista de Devedores do DOR.");
                    }
                }
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

                    quantidadeDias = daoConta.verificaStatusDOR(cliente, conta);
                    if (quantidadeDias >= 10) {
                        cliente = insereClienteDOR(conta.getCliente());
                        if (!cliente.getStatusDOR()) {
                            request.setAttribute("mensagemDOR", "Erro ao incluir cliente no sistema DOR.");
                        } else {
                            request.setAttribute("mensagemDOR", "Você está incluso na lista de Devedores do DOR.");
                        }
                    }
                    clienteRecebeTransf = daoCliente.clienteByConta(contaRecebeTransf.getNumAgencia(), contaRecebeTransf.getNumConta());
                    quantidadeDias = daoConta.verificaStatusDOR(clienteRecebeTransf, contaRecebeTransf);

                    if (contaRecebeTransf.getSaldo() >= 0) {
                        boolean verifica = liberaClienteDOR(cliente);
                        if (verifica) {
                            request.setAttribute("mensagemDOR", "Cliente que recebeu transferência foi removido da lista de Devedores do DOR.");
                        }
                    }
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
                
                quantidadeDias = daoConta.verificaStatusDOR(cliente, conta);
                if (quantidadeDias >= 10) {
                    cliente = insereClienteDOR(conta.getCliente());
                    if (!cliente.getStatusDOR()) {
                        request.setAttribute("mensagemDOR", "Erro ao incluir cliente no sistema DOR.");
                    } else {
                        request.setAttribute("mensagemDOR", "Você está incluso na lista de Devedores do DOR.");
                    }
                }
                clienteRecebeTransf = daoCliente.clienteByConta(contaRecebeTransf.getNumAgencia(), contaRecebeTransf.getNumConta());
                quantidadeDias = daoConta.verificaStatusDOR(clienteRecebeTransf, contaRecebeTransf);

                if (contaRecebeTransf.getSaldo() >= 0) {
                    boolean verifica = liberaClienteDOR(cliente);
                    if (verifica) {
                        request.setAttribute("mensagemDOR", "Cliente que recebeu depósito foi removido da lista de Devedores do DOR.");
                    }
                }
                
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
        } else {
            contas = daoConta.pegarTodasContasByCliente(cliente);
            if (contas.size() > 0) {
                session.setAttribute("contas", contas);
            }   
            rd = getServletContext().getRequestDispatcher("/todasContas.jsp");
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

//    public Boolean dataNegativacaoMaior10(Conta conta, Date dataAtual){
//        if(conta.getDataNegativacao().getD){
//            
//        }
//    }
    public Boolean liberaClienteDOR(Cliente cliente) {
        String cnpj = "";
        String cpf = "";
        if (null == cliente.getCnpj()) {
            cpf = cliente.getCpf();
            cnpj = "1";
        } else {
            cnpj = cliente.getCnpj();
            cpf = "1";
        }
        Client client = ClientBuilder.newClient();
        Cliente clienteTeste = client.target("http://localhost:8084/SistemaDOR2/webresources/WsSistemaDOR/VerificaNegativado/" + cpf + "/" + cnpj).
                request(MediaType.APPLICATION_JSON).get(Cliente.class);

        if (clienteTeste == null || !clienteTeste.getStatusDOR()) {
            return false;
        } else {
            Response resp = client.target("http://localhost:8084/SistemaDOR2/webresources/WsSistemaDOR/AlteraParaNegativado/")
                    .request(MediaType.APPLICATION_JSON).put(Entity.json(cliente));
            clienteTeste = resp.readEntity(Cliente.class);
            return true;
        }
    }

    public Cliente insereClienteDOR(Cliente cliente) {
        String cnpj = "";
        String cpf = "";
        if (null == cliente.getCnpj()) {
            cpf = cliente.getCpf();
            cnpj = "1";
        } else {
            cnpj = cliente.getCnpj();
            cpf = "1";
        }
        Client client = ClientBuilder.newClient();
        Cliente clienteTeste = client.target("http://localhost:8084/SistemaDOR2/webresources/WsSistemaDOR/VerificaNegativado/" + cpf + "/" + cnpj).
                request(MediaType.APPLICATION_JSON).get(Cliente.class);

        if (clienteTeste == null) {
            Response resp = client.target("http://localhost:8084/SistemaDOR2/webresources/WsSistemaDOR/CadastrarClienteNegativado")
                    .request(MediaType.APPLICATION_JSON).post(Entity.json(cliente));
            clienteTeste = resp.readEntity(Cliente.class);
        } else if (!clienteTeste.getStatusDOR()) {
            Response resp = client.target("http://localhost:8084/SistemaDOR2/webresources/WsSistemaDOR/AlteraParaNegativado/")
                    .request(MediaType.APPLICATION_JSON).put(Entity.json(cliente));
            clienteTeste = resp.readEntity(Cliente.class);
        }

        //return clienteNegativado;
        return clienteTeste;
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
