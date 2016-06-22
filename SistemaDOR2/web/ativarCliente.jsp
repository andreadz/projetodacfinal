<%-- 
    Document   : excluirUsuario
    Created on : 16/06/2016, 16:18:52
    Author     : sdgdsgd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link href="bootstrap-3.3.6-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="bootstrap-3.3.6-dist/js/bootstrap.min.js" type="text/javascript"></script>
        <title>DOR</title>
    </head>
    <body>
        <div class="container theme-showcase" role="main" style="padding-top: 100px;">
            <div class="" style="padding-left: 250px;">
                <h2>DOR</h2>
                <div class="jumbotron" >
                    <form action="PortalAdmin?action=ativarCliente" method="POST">
                       
                            <table >
                                <label>Reativação de cliente</label>
                                <tbody class="panel-body">
                                    <tr>                                        
                                        <td >
                                            <label>Nome Completo</label>
                                        </td> 
                                    </tr>
                                    <tr>                                        
                                        <td >
                                            <input type="text"  disabled="true" value="${clienteBusca.nome}" class="form-control" />
                                        </td>
                                    </tr>                                    
                                    <tr><td>&nbsp; &nbsp;</td></tr>
                                    <tr> 
                                        <c:choose>
                                            <c:when test="${clienteBusca.cpf != null}">
                                        <label>CPF</label>
                                        <td >
                                            <input type="text" disabled="true" value="${clienteBusca.cpf}" class="form-control" />
                                        </td>
                                    </c:when>
                                    <c:when test="${clienteBusca.cnpj != null}">
                                        <label>CNPJ</label>
                                        <td >
                                            <input type="text"  disabled="true" value="${clienteBusca.cnpj}" class="form-control" />
                                        </td>
                                    </c:when>
                                </c:choose>
                                        <td>&nbsp; &nbsp;</td>
                                <td >
                                    <label>Situação</label>
                                    <input type="text" disabled="true" placeholder="Negativado" class="form-control" />
                                </td>
                                <td>&nbsp; &nbsp;</td>
                                
                                <td>
                                    <label>Empresa</label>
                                    <input type="text"  name="nomeEmpresa" required="required" class="form-control" maxlength="50"/>
                                </td>
                                </tr>    
                                <tr><td>&nbsp; &nbsp;</td></tr>
                                <tr>
                                    <td>
                                        <a href="portalAdmin.jsp" class="btn  btn-default">Cancelar</a>
                                    &nbsp; &nbsp;
                                        <input type="submit" class="btn btn-sm btn-primary" value="Ativar"  />
                                    </td>
                                </tr>
                                <input type="hidden" name="idCliente" value="${clienteBusca.id}" class="form-control" />
                                </tbody>
                            </table>
                        </div>
                    </form>
                    ${msg}

                </div>
            </div>
        </div>
    </body>
</html>
