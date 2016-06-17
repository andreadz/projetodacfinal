<%-- 
    Document   : portalAdmin
    Created on : 16/06/2016, 15:50:11
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
            <div class="jumbotron" style="padding-left: 250px;">
                <h3>DOR - Devedores Originalmente Regulares</h3>
                <div>
                    <form>
                        <table>
                            <tbody>
                                <tr>
                                    <td style="padding: 10px;">
                                        Olá, <c:out value="${usuario.nome}"  />                                            
                                    </td>
                                    <td style="padding: 10px;">
                                        <a href="ProcessaLoginLogout?action=logout" >Logout</a> <br/>
                                    </td>                                        
                                </tr>                                    
                            </tbody>
                        </table>
                    </form>
                </div>
                <div>
                    <table class="table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>E-mail</th>
                                <th>Perfil</th>
                                <th colspan="2">Ação</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="userDAO" items="${usuarios}">
                                <c:url var="editar" value="PortalAdmin?action=requerEditarUsuario" >
                                    <c:param name="idUsuario" value="${userDAO.id}" />
                                </c:url>
                                <c:url var="excluir" value="PortalAdmin?action=requerExcluirUsuario" >
                                    <c:param name="idUsuario" value="${userDAO.id}" />
                                </c:url>
                                <tr>                                        
                                    <td style="padding: 10px;">${userDAO.id}</td>
                                    <td style="padding: 10px;">${userDAO.nome}</td>
                                    <td style="padding: 10px;">${userDAO.email}</td>
                                    <c:choose>
                                        <c:when test="${userDAO.perfil == 1}">
                                            <td style="padding: 10px;">Administrador</td>
                                        </c:when>
                                        <c:when test="${userDAO.perfil == 2}">
                                            <td style="padding: 10px;">Usuário</td>
                                        </c:when>
                                    </c:choose> 
                                    <td style="padding: 10px;"><a href="${editar}" >Editar</a></td>
                                    <td style="padding: 10px;"><a href="${excluir}">Excluir</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <a href="novoUsuario.jsp" class="btn btn-primary" >Novo Usuário</a> 
                </div>
                <div>
                    <c:url var="relatorios" value="relatorios.jsp" />
                    <c:url var="clientes" value="clientes.jsp" />
                    <a href="${relatorios}" >Relatórios</a> |
                    <a href="${clientes}"  > Clientes</a> 
                </div>
                ${msg}
            </div>
        </div>
    </body>
</html>
