/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import dao.ClienteDAO;
import dao.HistoricoDAO;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Historico;
import vsf.Cliente;

/**
 * REST Web Service
 *
 * @author sdgdsgd
 */
@Path("WsSistemaDOR")
public class WsSistemaDORResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WsSistemaDORResource
     */
    public WsSistemaDORResource() {
    }

    /**
     * Retrieves representation of an instance of teste.WsSistemaDORResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of WsSistemaDORResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    @GET
    @Path("/VerificaNegativado/{cpf}/{cnpj}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Cliente getClienteNegativado(@PathParam("cpf") String cpf, @PathParam("cnpj") String cnpj) {
        ClienteDAO daoCliente = new ClienteDAO();
        Cliente cliente = new Cliente();
        if (cpf.equals("1")) {
            cliente.setCnpj(cnpj);
        } else {
            cliente.setCpf(cpf);
        }
        cliente = daoCliente.verificaRestricaoDOR(cliente);
        return cliente;
    }

    @POST
    @Path("/CadastrarClienteNegativado")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inserirClienteNegativado(Cliente cliente) {
        ClienteDAO daoCliente = new ClienteDAO();
        if(daoCliente.cadastrarCliente(cliente)){
            cliente.setStatusDOR(Boolean.TRUE);
        }
        Historico historico = new Historico();
        historico.setEmpresa("");
        HistoricoDAO daoHistorico = new HistoricoDAO();
        daoHistorico.cadastrarHistorico(cliente, historico);
        return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*").entity(cliente).build();
    }
    
    @PUT
    @Path("/AlteraParaNegativado")
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response negativarCliente(Cliente cliente){
        ClienteDAO daoFunc = new ClienteDAO();
        if(daoFunc.inativarCliente(cliente)){
            cliente.setStatusDOR(Boolean.TRUE);
        }
        Historico historico = new Historico();
        historico.setEmpresa("");
        HistoricoDAO daoHistorico = new HistoricoDAO();
        daoHistorico.cadastrarHistorico(cliente, historico);
        return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*").entity(cliente).build();
    }
    @PUT
    @Path("/Reativar")
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response reativarCliente(Cliente cliente){
        ClienteDAO daoFunc = new ClienteDAO();
        if(daoFunc.ativarCliente(cliente)){
            cliente.setStatusDOR(Boolean.FALSE);
        }        
        HistoricoDAO daoHistorico = new HistoricoDAO();
        daoHistorico.cadastroReativacao(cliente);
        return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*").entity(cliente).build();
    }
}
