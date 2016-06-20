/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import dao.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Cliente;

/**
 *
 * @author Andre
 */
@WebServlet(name = "Cadastros", urlPatterns = {"/Cadastros"})
public class Cadastros extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher rd = request.getRequestDispatcher("");
        String action = request.getParameter("action");
        ClienteDAO daoCli = new ClienteDAO();
        ContaDAO daoConta = new ContaDAO();
        ArrayList<Conta> contas;
        String cnpjTeste = "";
            String cpfTeste = "";
//        if (session.getAttribute("cliente") == null) {
//            request.setAttribute("msg", "Usuário e não senha informados.");
//            rd = getServletContext().getRequestDispatcher("/index.jsp");
//        }

        if ("cadcliente".equals(action)) {
            String nome = request.getParameter("nome");
            String rg = request.getParameter("rg");
            String endereco = request.getParameter("endereco") + " ," + request.getParameter("numero") + " " + request.getParameter("complemento");
            String cep = request.getParameter("cep");
            String telefone = request.getParameter("telefone");
            String senha = request.getParameter("senha");
            double renda = Double.parseDouble(request.getParameter("renda"));
            String email = request.getParameter("email");
            String tipoConta = request.getParameter("tipoConta");
            String cnpj = null;
            String cpf = null;
            if (tipoConta.equals("J")) {
                cnpj = request.getParameter("cnpj");
            } else {
                cpf = request.getParameter("cpf");
            }

            Cliente cliente = new Cliente(0, cpf, cnpj, nome, rg, endereco, cep, telefone, email, senha, renda);
            cliente = daoCli.cadastrarCliente(cliente);
            session.setAttribute("cliente", cliente);
            if (null == cliente.getCnpj()) {
                cpfTeste = cliente.getCpf();
                cnpjTeste = "1";
            } else {
                cnpjTeste = cliente.getCnpj();
                cpfTeste = "1";
            }

            Client client = ClientBuilder.newClient();
            Cliente clienteTeste = client.target("http://localhost:8084/SistemaDOR2/webresources/WsSistemaDOR/VerificaNegativado/" + cpfTeste + "/" + cnpjTeste).
                    request(MediaType.APPLICATION_JSON).get(Cliente.class);
            if (clienteTeste == null || !clienteTeste.getStatusDOR()) {
                request.setAttribute("msg", "Cliente cadastrado com sucesso, realize cadastro de conta "
                        + "selecionando agência.");
                rd = getServletContext().getRequestDispatcher("/cadastroconta.jsp");
            } else {
                request.setAttribute("msg", "Cliente cadastrado com sucesso, porém não foi possível realizar cadastro"
                        + " de conta corrente, pois consta pendências no sistema de Devedores Originalmente Regulares. "
                        + " Favor regularizar pendências para posteriormente realizar continuação"
                        + " de cadastro de conta corrente.");
                rd = getServletContext().getRequestDispatcher("/index.jsp");
            }
        } else if ("cadconta".equals(action)) {
            String agencia = request.getParameter("agencia");
            Cliente cliente = (Cliente) session.getAttribute("cliente");
            
            if (null == cliente.getCnpj()) {
                cpfTeste = cliente.getCpf();
                cnpjTeste = "1";
            } else {
                cnpjTeste = cliente.getCnpj();
                cpfTeste = "1";
            }

            double limite = verificaLimite(cliente.getRenda());
            Client client = ClientBuilder.newClient();
            Cliente clienteTeste = client.target("http://localhost:8084/SistemaDOR2/webresources/WsSistemaDOR/VerificaNegativado/" + cpfTeste + "/" + cnpjTeste).
                    request(MediaType.APPLICATION_JSON).get(Cliente.class);
            if (clienteTeste == null || !clienteTeste.getStatusDOR()) {
                Conta conta = daoConta.criarConta(agencia, cliente, limite);
                request.setAttribute("msg", "Conta cadastrada com sucesso.");
                request.setAttribute("conta", conta);
            } else {
                request.setAttribute("msg", "Não é possível realizar cadastro"
                        + " de conta corrente, pois consta pendências no sistema de Devedores Originalmente Regulares."
                        + " Favor regularizar pendências para posteriormente realizar"
                        + " cadastro de conta-corrente.");
            }
            rd = getServletContext().getRequestDispatcher("/index.jsp");
        } else if ("cadastroContaNova".equals(action)) {
            String agencia = request.getParameter("agencia");
            Cliente cliente = (Cliente) session.getAttribute("cliente");
            double limite = verificaLimite(cliente.getRenda());
            if (null == cliente.getCnpj()) {
                cpfTeste = cliente.getCpf();
                cnpjTeste = "1";
            } else {
                cnpjTeste = cliente.getCnpj();
                cpfTeste = "1";
            }
            
            Client client = ClientBuilder.newClient();
            Cliente clienteTeste = client.target("http://localhost:8084/SistemaDOR2/webresources/WsSistemaDOR/VerificaNegativado/" + cpfTeste + "/" + cnpjTeste).
                    request(MediaType.APPLICATION_JSON).get(Cliente.class);
            if (clienteTeste == null || !clienteTeste.getStatusDOR()) {
                contas = (ArrayList<Conta>) session.getAttribute("contas");
                Conta contaNova = daoConta.criarConta(agencia, cliente, limite);
                request.setAttribute("msg", "Nova Conta cadastrada com sucesso.");
                contas.add(contaNova);
                session.setAttribute("contas", contas);
            } else {
                request.setAttribute("msg", "Não é possível  realizar cadastro de nova "
                        + " conta corrente, pois consta pendências no sistema de Devedores Originalmente Regulares. "
                        + " Favor regularizar pendências para posteriormente realizar"
                        + " cadastro de conta-corrente.");
            }
            rd = getServletContext().getRequestDispatcher("/portal.jsp");
        }
        rd.forward(request, response);
    }

    public double verificaLimite(double renda) {
        double limite;
        if (renda < 1000.0) {
            limite = 0;
        } else {
            limite = renda / 2;
        }
        return limite;
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
