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
                <div>
                    <form action="PortalAdmin?action=ativarCliente" method="POST">
                        <div class="panel panel-default">
                            <table class="panel-heading">
                                <labe class="panel-title">Reativação de cliente</labe>
                                <tbody class="panel-body">
                                    <tr>                                        
                                        <td class="col-md-2">
                                            <label>Nome Completo</label>
                                        </td> 
                                    </tr>
                                    <tr>                                        
                                        <td class="col-md-2">
                                            <input type="text"  disabled="true" value="${clienteBusca.nome}" />
                                        </td>
                                    </tr>                                    
                                    <tr> 
                                <c:choose>
                                    <c:when test="${clienteBusca.cpf != null}">
                                        <label>CPF</label>
                                        <td style="padding: 10px;">
                                            <input type="text" disabled="true" value="${clienteBusca.cpf}" />
                                        </td>
                                    </c:when>
                                    <c:when test="${clienteBusca.cnpj != null}">
                                        <label>CNPJ</label>
                                        <td style="padding: 10px;">
                                            <input type="text"  disabled="true" value="${clienteBusca.cnpj}" />
                                        </td>
                                    </c:when>
                                </c:choose>
                                <td>
                                    <label>Situação</label>
                                    <input type="text" disabled="true" placeholder="Negativado" />
                                </td>
                                <td>
                                    <label>Empresa</label>
                                    <input type="text"  name="nomeEmpresa" required="required"  />
                                </td>
                                </tr>    
                                <tr>
                                    <td>
                                       <a href="portalAdmin.jsp" class="btn btn-default">Cancelar</a>
                                        <input type="submit" class="btn btn-sm btn-primary" value="Ativar" />
                                    </td>
                                </tr>
                                <input type="hidden" name="idCliente" value="${clienteBusca.id}" />
                                </tbody>
                            </table>
                    </form>
                    ${msg}
                </div>
            </div>
        </div>
    </div>
</body>
</html>
