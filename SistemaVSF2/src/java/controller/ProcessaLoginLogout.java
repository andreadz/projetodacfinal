/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import dao.*;
import java.util.ArrayList;

/**
 *
 * @author André
 */
@WebServlet(name = "ProcessaLoginLogout", urlPatterns = {"/ProcessaLoginLogout"})
public class ProcessaLoginLogout extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        ClienteDAO dao = new ClienteDAO();
        ContaDAO daoconta = new ContaDAO();
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        if ("acessar".equals(action)) {
            String agencia = request.getParameter("agencia").isEmpty() ? "" : request.getParameter("agencia");
            int numConta = request.getParameter("numConta").isEmpty() ? 0 : Integer.parseInt(request.getParameter("numConta"));
            String senha = request.getParameter("senha").isEmpty() ? "" : request.getParameter("senha");
            Cliente cliente = dao.login(agencia, numConta, senha);
            Conta conta = daoconta.pegarContaByCliente(agencia, numConta, cliente);            
            if (cliente == null) {
                request.setAttribute("msg", "Login e/ou senha incorretos.");
            }else if(!conta.getStatusConta()){
                request.setAttribute("msg", "Conta inativa.");
            } 
            else {
                HttpSession session = request.getSession();
                ArrayList<Conta> contas = daoconta.pegarTodasContasByCliente(cliente);
                double totalSaldos = totalSaldos = somaSaldos(contas);                
                if(contas.size() > 0){                    
                    session.setAttribute("contas", contas);
                } 
                request.setAttribute("totalSaldos", totalSaldos);
                request.setAttribute("qtdeContas", contas.size());
                session.setAttribute("conta", conta);
                session.setAttribute("cliente", cliente);
                request.setAttribute("msg", "");
                rd = getServletContext().getRequestDispatcher("/portal.jsp");
            }
        }
        else if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            request.setAttribute("msg", "Logout realizado com sucesso.");
        }
        else {
            request.setAttribute("msg", "Login e senha incorretos.");
        }
        rd.forward(request, response);
    }
    
    public double somaSaldos(ArrayList<Conta> contas){
        double somaSaldoContas = 0.00;
        for (Conta conta : contas) {
            somaSaldoContas = somaSaldoContas + conta.getSaldo();
        }
        return somaSaldoContas;
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
