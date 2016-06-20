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
                <form method="POST" action="PortalAdmin?action=buscarCliente">
                    <div>
                        Buscar por CPF
                        <input type="text" name="buscaCPF" maxlength="11" />
                    </div>
                    <div>
                        Buscar cliente por nome
                        <input type="text" name="buscaNome" maxlength="30" />
                    </div>
                    <input type="submit" value="Buscar" />
                </form>
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
                <c:if test="${clientesNome.size() != 0}">
                    <div>
                        <table class="table-striped table-bordered">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nome</th>
                                        <c:if test="${clientesNome.cpf != null}">
                                        <th>CPF</th>
                                        </c:if>
                                        <c:if test="${clientesNome.cnpj != null}">
                                        <th>CNPJ</th>
                                        </c:if>
                                    <th>Status</th>
                                    <th colspan="2"></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="clientesNome" items="${clientesNome}">
                                    <c:url var="ativar" value="PortalAdmin?action=requerAtivarCliente" >
                                        <c:param name="idCliente" value="${clientesNome.id}" />
                                    </c:url>
                                    <c:url var="negativar" value="PortalAdmin?action=requerNegativarCliente" >
                                        <c:param name="idCliente" value="${clientesNome.id}" />
                                    </c:url>
                                    <tr>                                        
                                        <td style="padding: 10px;">${clientesNome.id}</td>
                                        <td style="padding: 10px;">${clientesNome.nome}</td>
                                        <c:choose>
                                            <c:when test="${clientesNome.cpf != null}">
                                                <td style="padding: 10px;">CPF</td>
                                            </c:when>
                                            <c:when test="${clientesNome.cnpj != null}">
                                                <td style="padding: 10px;">CNPJ</td>
                                            </c:when>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${clientesNome.statusDOR == true}">
                                                <td style="padding: 10px;">Negativado</td>
                                                <td style="padding: 10px;"><a href="${ativar}">Ativar</a></td>
                                            </c:when>
                                            <c:when test="${clientesNome.statusDOR == false}">
                                                <td style="padding: 10px;">Ativo</td>
                                                <td style="padding: 10px;"><a href="${negativar}" >Negativar</a></td>
                                            </c:when>
                                        </c:choose>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>  
                </c:if>
                <c:if test="${clienteBusca.cpf != null}">
                    <div>
                        <table class="table-striped table-bordered">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nome</th>
                                        <c:if test="${clientesNome.cpf != null}">
                                        <th>CPF</th>
                                        </c:if>
                                        <c:if test="${clientesNome.cnpj != null}">
                                        <th>CNPJ</th>
                                        </c:if>
                                    <th>Histórico</th>
                                    <th>Situação</th>
                                </tr>
                            </thead>
                            <tbody>                               
                                <c:url var="ativar" value="PortalAdmin?action=requerAtivarCliente" >
                                    <c:param name="idCliente" value="${clienteBusca.id}" />
                                </c:url>
                                <c:url var="negativar" value="PortalAdmin?action=requerNegativarCliente" >
                                    <c:param name="idCliente" value="${clienteBusca.id}" />
                                </c:url>
                                <c:url var="relatorioHistorico" value="RelatorioController?action=historico">
                                    <c:param name="idCliente" value="${clienteBusca.id}" />
                                </c:url>
                                <c:url var="relatorioEmpresa" value="RelatorioController?action=empresa">
                                    <c:param name="idCliente" value="${clienteBusca.id}" />
                                </c:url>
                                <tr>                                        
                                    <td style="padding: 10px;">${clienteBusca.id}</td>
                                    <td style="padding: 10px;">${clienteBusca.nome}</td>                                   
                                    <c:choose>
                                        <c:when test="${clientesNome.cpf != null}">
                                            <td style="padding: 10px;">${clienteBusca.cpf}</td>
                                        </c:when>
                                        <c:when test="${clientesNome.cnpj != null}">
                                            <td style="padding: 10px;">${clienteBusca.cnpj}</td>
                                        </c:when>
                                    </c:choose>
                                    <td><a href="${relatorioHistorico}">Histórico</a></td>
                                    <td><a href="${relatorioEmpresa}">Relatório</a></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>  
                </c:if>
                ${msg}
            </div>
        </div>
    </body>
</html>
