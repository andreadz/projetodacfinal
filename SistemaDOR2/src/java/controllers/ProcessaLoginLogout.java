/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import vsf.*;
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
        UsuarioDAO daoUsuario = new UsuarioDAO();
        ClienteDAO daoCliente = new ClienteDAO();
        ArrayList<Usuario> usuarios;
        ArrayList<Cliente> clientes;
       // ArrayList<Historicos> historicos;       
        HttpSession session;
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        
        if ("acessar".equals(action)) {
            String email = request.getParameter("email").isEmpty() ? "" : request.getParameter("email");
            String senha = request.getParameter("senha").isEmpty() ? "" : request.getParameter("senha");
            Usuario usuario = daoUsuario.login(email, senha);
            //Cliente cliente = daoCliente.login(email, senha);
            if (usuario == null) {
                request.setAttribute("msg", "Login e/ou senha incorretos.");
            } else if (usuario.getPerfil() == 1) {
                session = request.getSession();
                usuarios = daoUsuario.todosUsuariosAtivos();
               // clientes = daoCliente.todosClientes();
                //historicos = daoUsuario.todosHistoricos();
                session.setAttribute("usuario", usuario);
                session.setAttribute("usuarios", usuarios);                
                //.setAttribute("clientes", clientes);                
                //session.setAttribute("historicos", historicos);                
                
                rd = getServletContext().getRequestDispatcher("/portalAdmin.jsp");
            } else {
                session = request.getSession();
                clientes = daoCliente.todosClientes();
                //historicos = daoUsuario.todosHistoricos();
                session.setAttribute("usuario", usuario);
                session.setAttribute("clientes", clientes);
                //session.setAttribute("historicos", historicos);
                
                rd = getServletContext().getRequestDispatcher("/portalFuncionario.jsp");
            }
        } else if ("logout".equals(action)) {
            session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            request.setAttribute("msg", "Logout realizado com sucesso.");
        } else {
            request.setAttribute("msg", "Login e senha incorretos.");
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
