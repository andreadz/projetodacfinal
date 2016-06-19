/**
 * @author André
 */
package controller;

import javax.ws.rs.client.*;
import java.io.IOException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import models.*;
import dao.ClienteDAO;

@WebServlet(name = "Verificacoes", urlPatterns = {"/Verificacoes"})
public class Verificacoes extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher rd = request.getRequestDispatcher("");        
        String tipoPessoa = request.getParameter("tipoPessoa");
        String cpf = request.getParameter("cpf").isEmpty() ? "" : request.getParameter("cpf");
        String cnpj = request.getParameter("cnpj").isEmpty() ? "" : request.getParameter("cnpj");
        String action = request.getParameter("action");
        ClienteDAO dao = new ClienteDAO();
        if ("verificaNegativado".equals(action)) {
            Client client = ClientBuilder.newClient();
            Response retorno = client.target("http://localhost:8080/SistemaDOR2/webresources/ClienteVerificacao").request(MediaType.APPLICATION_JSON).get();
            request.setAttribute("cliente", retorno.readEntity(Cliente.class));
            rd = request.getRequestDispatcher("erro.jsp");
        }
        if ("verificaExistencia".equals(action)) {
            boolean verifica;
            if(tipoPessoa.equals("J")){
                verifica = dao.verificaUsuarioExistenteJ(cnpj);                
            }
            else{
                verifica = dao.verificaUsuarioExistenteF(cpf);
            }
            
            if (verifica) {
                request.setAttribute("msg", "Usuário existente, favor logar no sistema"); 
                rd = request.getRequestDispatcher("index.jsp");
            } else {
                request.setAttribute("tipoPessoa", tipoPessoa);
                request.setAttribute("cnpj", cnpj);
                request.setAttribute("cpf", cpf);
                rd = request.getRequestDispatcher("cadastro.jsp");
            }
        }        
        rd.forward(request, response);
    }

//    protected Boolean verificaExistencia(String cnpjCpf) {
//        
//        Boolean verifica = false;
//        if (cnpjCpf.length() == 11) {
//            return verifica = !dao.verificaUsuarioExistenteF(cnpjCpf) ? verifica : true;
//        }
//        return verifica = !dao.verificaUsuarioExistenteJ(cnpjCpf) ? verifica : true;
//    }    
   

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
