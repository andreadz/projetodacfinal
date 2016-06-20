/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.ClienteDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Usuario;
import vsf.Cliente;

/**
 *
 * @author sdgdsgd
 */
@WebServlet(name = "PortalAdmin", urlPatterns = {"/PortalAdmin"})
public class PortalAdmin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        UsuarioDAO daoUsuario = new UsuarioDAO();
        ClienteDAO daoCliente = new ClienteDAO();
        HttpSession session = request.getSession();
        Usuario usuario = new Usuario();
        Usuario usuarioSessao = (Usuario) session.getAttribute("usuario");
        ArrayList<Usuario> usuarios;
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/paginasCRUDAdmin/portalAdmin.jsp");

        if (usuarioSessao == null) {
            request.setAttribute("msg", "Não há nenhuma sessão inicializada.");
            rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        } else if ("requerEditarUsuario".equals(action)) {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            usuario = daoUsuario.pegarUsuarioById(idUsuario);
            request.setAttribute("usuario", usuario);
            rd = getServletContext().getRequestDispatcher("/paginasCRUDAdmin/editarUsuario.jsp");
        } else if ("requerExcluirUsuario".equals(action)) {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            usuario = daoUsuario.pegarUsuarioById(idUsuario);
            request.setAttribute("usuario", usuario);
            rd = getServletContext().getRequestDispatcher("/paginasCRUDAdmin/excluirUsuario.jsp");
        } else if ("editarUsuario".equals(action)) {
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            int perfil = Integer.parseInt(request.getParameter("perfilUsuario"));
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setPerfil(perfil);
            usuario.setId(idUsuario);
            daoUsuario.atualizarUsuario(usuario);
            usuarios = daoUsuario.todosUsuariosAtivos();
            session.setAttribute("usuarios", usuarios);
            request.setAttribute("msg", "Alteração realizada com sucesso.");
        } else if ("excluirUsuario".equals(action)) {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            usuario.setId(idUsuario);
            usuario.setStatusUsuario(Boolean.FALSE);
            daoUsuario.inativarUsuario(usuario);
            usuarios = daoUsuario.todosUsuariosAtivos();
            session.setAttribute("usuarios", usuarios);
            request.setAttribute("msg", "Inativação realizada com sucesso.");

        } else if ("cadUsuario".equals(action)) {
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            int perfil = Integer.parseInt(request.getParameter("perfilUsuario"));
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuario.setPerfil(perfil);
            usuario.setStatusUsuario(true);
            daoUsuario.cadastrarUsuario(usuario);
            request.setAttribute("msg", "Cadastro realizado com sucesso.");
            usuarios = daoUsuario.todosUsuariosAtivos();
            session.setAttribute("usuarios", usuarios);
        } else if ("buscarCliente".equals(action)) {
            String nome = request.getParameter("buscaNome").isEmpty() ? "" : request.getParameter("buscaNome");
            String cpf = request.getParameter("buscaCPF").isEmpty() ? "" : request.getParameter("buscaCPF");
            if (nome.isEmpty()) {
                ArrayList<Cliente> clientesNome;
                clientesNome = daoCliente.buscaByNome(nome);
                if (clientesNome.isEmpty()) {
                    request.setAttribute("msg", "nenhum cliente com esse nome encontrado.");
                }
                request.setAttribute("clientesNome", clientesNome);
            } else {
                Cliente clienteBusca;
                clienteBusca = daoCliente.buscarByCpf(cpf);
                if (clienteBusca == null) {
                    request.setAttribute("msg", "nenhum cliente encontrado para este CPF.");
                }
                request.setAttribute("clienteBusca", clienteBusca);
            }
        }

        //Ações para Negativar ou Inativar Clientes
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
