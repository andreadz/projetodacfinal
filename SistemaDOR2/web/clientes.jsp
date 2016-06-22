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
                <h3>Clientes</h3>
                <c:if test="${clienteBusca == null}">
                    <div>
                        <table class="table-striped table-bordered">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nome</th>                                        
                                    <th>Status</th>
                                    <th>Histórico</th>
                                    <th>Ação</th>
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
                                    <c:url var="relatorioHistorico" value="RelatoriosController?action=RelHistorico">
                                        <c:param name="idCliente" value="${clientesNome.id}" />
                                    </c:url>
                                    <c:url var="relatorioEmpresa" value="RelatoriosController?action=RelEmpresa">
                                        <c:param name="idCliente" value="${clientesNome.id}" />
                                    </c:url>
                                    <tr>                                        
                                        <td style="padding: 10px;">${clientesNome.id}</td>
                                        <td style="padding: 10px;">${clientesNome.nome}</td>                                        
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
                                        <td><a href="${relatorioHistorico}">Histórico</a></td>
                                        <td><a href="${relatorioEmpresa}">Relatório</a></td>
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
                                    <th>CPF</th>
                                    <th>Status</th>  
                                    <th>Ação</th>
                                    <th>Histórico</th>
                                    <th>Situação/Empresa</th>
                                </tr>
                            </thead>
                            <tbody>                               
                                <c:url var="ativar" value="PortalAdmin?action=requerAtivarCliente" >
                                    <c:param name="idCliente" value="${clienteBusca.id}" />
                                </c:url>
                                <c:url var="negativar" value="PortalAdmin?action=requerNegativarCliente" >
                                    <c:param name="idCliente" value="${clienteBusca.id}" />
                                </c:url>
                                <%-- <c:url var="relatorioHistorico" value="PortalAdmin?action=historico">
                                    <c:param name="idCliente" value="${clienteBusca.id}" />
                                </c:url> --%>
                                <c:url var="relatorioHistorico" value="RelatoriosController?action=RelHistorico">
                                    <c:param name="idCliente" value="${clienteBusca.id}" />
                                </c:url>
                                <c:url var="relatorioEmpresa" value="RelatoriosController?action=RelEmpresa">
                                    <c:param name="idCliente" value="${clienteBusca.id}" />
                                </c:url>
                                <tr>                                        
                                    <td style="padding: 10px;">${clienteBusca.id}</td>
                                    <td style="padding: 10px;">${clienteBusca.nome}</td>                                 
                                    <td style="padding: 10px;">${clienteBusca.cpf}</td>                                 

                                    <c:choose>
                                        <c:when test="${clienteBusca.statusDOR == true}">
                                            <td style="padding: 10px;">Negativado</td>
                                            <td style="padding: 10px;"><a href="${ativar}">Ativar</a></td>
                                        </c:when>
                                        <c:when test="${clienteBusca.statusDOR == false}">
                                            <td style="padding: 10px;">Ativo</td>
                                            <td style="padding: 10px;"><a href="${negativar}" >Negativar</a></td>
                                        </c:when>
                                    </c:choose>
                                    <td><a href="${relatorioHistorico}">Histórico</a></td>
                                    <td><a href="${relatorioEmpresa}">Relatório</a></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>  
                </c:if>
                <c:if test="${clienteBusca.cnpj != null}">
                    <div>
                        <table class="table-striped table-bordered">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nome</th>  
                                    <th>CNPJ</th>
                                    <th>Status</th>  
                                    <th>Ação</th>
                                    <th>Histórico</th>
                                    <th>Situação/Empresa</th>
                                </tr>
                            </thead>
                            <tbody>                               
                                <c:url var="ativar" value="PortalAdmin?action=requerAtivarCliente" >
                                    <c:param name="idCliente" value="${clienteBusca.id}" />
                                </c:url>
                                <c:url var="negativar" value="PortalAdmin?action=requerNegativarCliente" >
                                    <c:param name="idCliente" value="${clienteBusca.id}" />
                                </c:url>
                                <%-- <c:url var="relatorioHistorico" value="PortalAdmin?action=historico">
                                     <c:param name="idCliente" value="${clienteBusca.id}" />
                                 </c:url> --%>
                                <c:url var="relatorioHistorico" value="RelatoriosController?action=RelHistorico">
                                    <c:param name="idCliente" value="${clienteBusca.id}" />
                                </c:url>
                                <c:url var="relatorioEmpresa" value="RelatoriosController?action=RelEmpresa">
                                    <c:param name="idCliente" value="${clienteBusca.id}" />
                                </c:url>
                                <tr>                                        
                                    <td style="padding: 10px;">${clienteBusca.id}</td>
                                    <td style="padding: 10px;">${clienteBusca.nome}</td>                                 
                                    <td style="padding: 10px;">${clienteBusca.cnpj}</td>                                 

                                    <c:choose>
                                        <c:when test="${clienteBusca.statusDOR == true}">
                                            <td style="padding: 10px;">Negativado</td>
                                            <td style="padding: 10px;"><a href="${ativar}">Ativar</a></td>
                                        </c:when>
                                        <c:when test="${clienteBusca.statusDOR == false}">
                                            <td style="padding: 10px;">Ativo</td>
                                            <td style="padding: 10px;"><a href="${negativar}" >Negativar</a></td>
                                        </c:when>
                                    </c:choose>
                                    <td><a href="${relatorioHistorico}">Histórico</a></td>
                                    <td><a href="${relatorioEmpresa}">Relatório</a></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>  
                </c:if>
                <a href="portalAdmin.jsp" class="btn btn-default">Voltar</a>
                ${msg}
            </div>
        </div>
    </body>
</html>

