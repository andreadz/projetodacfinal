/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.ClienteDAO;
import dao.HistoricoDAO;
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
import models.Historico;
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
        Historico historico = new Historico();
        HistoricoDAO daoHistorico = new HistoricoDAO();
        ArrayList<Cliente> clientesNome;
        ArrayList<Historico> historicos;
        Cliente clienteBusca;
        Usuario usuario = new Usuario();
        Usuario usuarioSessao = (Usuario) session.getAttribute("usuario");
        ArrayList<Usuario> usuarios;
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/portalAdmin.jsp");

        if (usuarioSessao == null) {
            request.setAttribute("msg", "Não há nenhuma sessão inicializada.");
            rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        } else if ("requerEditarUsuario".equals(action)) {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            usuario = daoUsuario.pegarUsuarioById(idUsuario);
            request.setAttribute("usuario", usuario);
            rd = getServletContext().getRequestDispatcher("/editarUsuario.jsp");
        } else if ("requerExcluirUsuario".equals(action)) {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            usuario = daoUsuario.pegarUsuarioById(idUsuario);
            request.setAttribute("usuario", usuario);
            rd = getServletContext().getRequestDispatcher("/excluirUsuario.jsp");
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
            request.setAttribute("msg", "Exclusão realizada com sucesso.");

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
        } //Ações para Negativar ou Inativar Clientes
        else if ("buscarCliente".equals(action)) {
            String nome = request.getParameter("buscaNome").isEmpty() ? "" : request.getParameter("buscaNome");
            String cpf = request.getParameter("buscaCPF").isEmpty() ? "" : request.getParameter("buscaCPF");
            if (!nome.isEmpty()) {
                clientesNome = daoCliente.buscaByNome(nome);
                if (clientesNome.isEmpty()) {
                    request.setAttribute("msg", "nenhum cliente encontrado.");
                }
                session.setAttribute("clientesNome", clientesNome);
                session.removeAttribute("clienteBusca");
            } else {
                clienteBusca = daoCliente.buscarByCpf(cpf);
                if (clienteBusca == null) {
                    request.setAttribute("msg", "nenhum cliente encontrado.");
                }
                session.setAttribute("clienteBusca", clienteBusca);
                session.removeAttribute("clientesNome");
            }
            rd = getServletContext().getRequestDispatcher("/clientes.jsp");
        } else if ("requerAtivarCliente".equals(action)) {
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            clienteBusca = daoCliente.pegarClienteById(idCliente);
            session.setAttribute("clienteBusca", clienteBusca);
            rd = getServletContext().getRequestDispatcher("/ativarCliente.jsp");
        } else if ("requerNegativarCliente".equals(action)) {
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            clienteBusca = daoCliente.pegarClienteById(idCliente);
            session.setAttribute("clienteBusca", clienteBusca);
            rd = getServletContext().getRequestDispatcher("/inativarCliente.jsp");
        } else if ("ativarCliente".equals(action)) {
            String empresa = request.getParameter("nomeEmpresa");
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
//            String nome = request.getParameter("nome");
//            String email = request.getParameter("email");
//            int perfil = Integer.parseInt(request.getParameter("perfilUsuario"));
//            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            clienteBusca = daoCliente.pegarClienteById(idCliente);
            clienteBusca.setStatusDOR(Boolean.FALSE);
            daoCliente.ativarCliente(clienteBusca);
            historico.setEmpresa(empresa);
            daoHistorico.cadastroReativacaoByDOR(clienteBusca, historico);
            session.setAttribute("clienteBusca", clienteBusca);
            request.setAttribute("msg", "Ativação realizada com sucesso.");
        } else if ("inativarCliente".equals(action)) {
            String empresa = request.getParameter("nomeEmpresa");
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
//            String email = request.getParameter("email");
//            int perfil = Integer.parseInt(request.getParameter("perfilUsuario"));
//            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            clienteBusca = daoCliente.pegarClienteById(idCliente);
            clienteBusca.setStatusDOR(Boolean.TRUE);
            daoCliente.inativarCliente(clienteBusca);
            historico.setEmpresa(empresa);
            daoHistorico.cadastroInativacaoByDOR(clienteBusca, historico);
            session.setAttribute("clienteBusca", clienteBusca);
            request.setAttribute("msg", "Ativação realizada com sucesso.");
        }
         else if ("historico".equals(action)) {
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
//            String email = request.getParameter("email");
//            int perfil = Integer.parseInt(request.getParameter("perfilUsuario"));
//            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            clienteBusca = daoCliente.pegarClienteById(idCliente);
            historicos = daoHistorico.historicoByCliente(idCliente);
            session.setAttribute("clienteBusca", clienteBusca);
            session.setAttribute("historicos", historicos);
            if(historicos.isEmpty()){
                request.setAttribute("msg", "Nenhum histórico encontrado.");
            }
            else{
                rd = getServletContext().getRequestDispatcher("/historicos.jsp");
            }            
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
