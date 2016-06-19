/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import dao.*;
import models.*;
import vsf.*;

/**
 * REST Web Service
 *
 * @author sdgdsgd
 */
@Path("WsDor")
public class WsDorResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WsDorResource
     */
    public WsDorResource() {
    }

    /**
     * Retrieves representation of an instance of ws.WsDorResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    @GET
    @Path("/VerificaNegativado/{cliente}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Response getClienteNegativado(@PathParam("cliente") Cliente cliente) {
        ClienteDAO daoCliente = new ClienteDAO();  
        
        cliente = daoCliente.verificaRestricaoDOR(cliente);
       return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*").entity(cliente).build();
    }

    /**
     * PUT method for updating or creating an instance of WsDorResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
