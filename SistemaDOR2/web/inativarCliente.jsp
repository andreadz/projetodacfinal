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
        <div class="container " role="main" style="padding-top: 100px;">
            <div class="jumbotron" style="padding-left: 250px;">
                <h2>DOR</h2>
               
                    <form action="PortalAdmin?action=inativarCliente" method="POST">
                        <div >
                            <table >
                                <label  >Reativação de cliente</label>
                                <tbody >
                                    <tr>                                        
                                        <td>
                                            <label>Nome Completo</label>
                                        </td> 
                                    </tr>
                                    <tr>                                        
                                        <td >
                                            <input class="form-control" type="text"  disabled="true" value="${clienteBusca.nome}" />
                                        </td>
                                    </tr>                                    
                                    <tr> 
                                <c:choose>
                                    <c:when test="${clienteBusca.cpf != null}">
                                        <label>CPF</label>
                                        <td style="padding: 10px;">
                                            <input type="text" class="form-control" disabled="true" value="${clienteBusca.cpf}" class="form-control" />
                                        </td>
                                    </c:when>
                                    <c:when test="${clienteBusca.cnpj != null}">
                                        <label>CNPJ</label>
                                        <td style="padding: 10px;">
                                            <input type="text"  class="form-control" disabled="true" value="${clienteBusca.cnpj}"  />
                                        </td>
                                    </c:when>
                                </c:choose>
                                <td>
                                    <label>Situação</label>
                                    <input type="text" disabled="true" placeholder="Ativo" class="form-control"/>
                                </td>
                                <td>&nbsp; &nbsp;</td>
                                <td>
                                    <label>Empresa</label>
                                    <input type="text"  name="nomeEmpresa" required="required" class="form-control" />
                                </td>
                                </tr>    
                                <tr><td>&nbsp; &nbsp;</td></tr>
                                <tr>
                                    <td>
                                        <a href="portalAdmin.jsp" class="btn btn-default">Cancelar</a>
                                        
                                        <input type="submit" class="btn btn-sm btn-primary" value="Ativar" />
                                    </td>
                                </tr>
                                <input type="hidden"  class="form-control"name="idCliente" value="${clienteBusca.id}"  />
                                </tbody>
                            </table>
                    </form>
                    ${msg}
                </div>
        
        </div>
    </div>
</body>
</html>
