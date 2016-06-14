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
        ContaDAO daoConta = new ContaDAO();        
        TransacaoDAO daoTrans = new TransacaoDAO();
        ArrayList<Conta> contas;
        ArrayList<Transacao> transacoes;
        Cliente cliente = (Cliente) session.getAttribute("cliente");
        if ("extratos".equals(action)) {            
            int periodo = request.getParameter("extrato")== null || request.getParameter("extrato").isEmpty() ? 0 : Integer.parseInt(request.getParameter("extrato"));  
            Conta conta = (Conta) session.getAttribute("conta");
            if(periodo == 30){
                transacoes = daoTrans.pegarTransacoes(periodo, conta.getId());
            }
            else if(periodo == 15){
                transacoes = daoTrans.pegarTransacoes(periodo, conta.getId());
            }
            else{
                transacoes = daoTrans.pegarTransacoes(conta.getId());
            }
            
            request.setAttribute("msg", "");
            request.setAttribute("transacoes", transacoes);
            rd = getServletContext().getRequestDispatcher("/extratos.jsp");
        }
        else if ("todasContas".equals(action)) {
            if (cliente == null) {
                request.setAttribute("msg", "Login e/ou senha incorretos.");
                rd.forward(request, response);
            } else {
                contas = daoConta.pegarTodasContasByCliente(cliente);              
                if(contas.size() > 0){                    
                    session.setAttribute("contas", contas);
                } 
                rd = getServletContext().getRequestDispatcher("/todasContas.jsp");
            }
        }
        else if ("sacar".equals(action)) {
            if (cliente == null) {
                request.setAttribute("msg", "Login e/ou senha incorretos.");
                rd.forward(request, response);
            } else {
                contas = daoConta.pegarTodasContasByCliente(cliente);              
                if(contas.size() > 0){                    
                    session.setAttribute("contas", contas);
                } 
                rd = getServletContext().getRequestDispatcher("/todasContas.jsp");
            }
        }
        else if ("depositar".equals(action)) {
            if (cliente == null) {
                request.setAttribute("msg", "Login e/ou senha incorretos.");
                rd.forward(request, response);
            } else {
                contas = daoConta.pegarTodasContasByCliente(cliente);              
                if(contas.size() > 0){                    
                    session.setAttribute("contas", contas);
                } 
                rd = getServletContext().getRequestDispatcher("/todasContas.jsp");
            }
        }
        else if ("transferir".equals(action)) {
            if (cliente == null) {
                request.setAttribute("msg", "Login e/ou senha incorretos.");
                rd.forward(request, response);
            } else {
                contas = daoConta.pegarTodasContasByCliente(cliente);              
                if(contas.size() > 0){                    
                    session.setAttribute("contas", contas);
                } 
                rd = getServletContext().getRequestDispatcher("/todasContas.jsp");
            }
        }
        else{
            request.setAttribute("msg", "Não há nenhuma sessão inicializada.");
            rd = getServletContext().getRequestDispatcher("/index.jsp");
        }        
        rd.forward(request, response);
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
