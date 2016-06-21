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
                        <c:if test="${clienteBusca.cnpj != null}">
                            Histórico de Negativação para: ${clienteBusca.nome} do CNPJ ${clienteBusca.cnpj}
                        </c:if>
                        <c:if test="${clienteBusca.cpf != null}">
                            Histórico de Negativação para: ${clienteBusca.nome} do CPF: ${clienteBusca.cpf}
                        </c:if>
                        <table class="table-striped table-bordered">
                            <thead>
                                <tr>
                                    <th>Histórico</th> 
                                    <th>Data Inclusão</th>
                                    <th>Data Exclusão</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="hists" items="${historicos}">
                                    <tr>                                        
                                        <td style="padding: 10px;">${hists.id}</td> 
                                        <td style="padding: 10px;">${hists.dataInclusao}</td>  
                                        <td style="padding: 10px;">${hists.dataExclusao}</td> 
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                            <a href="portalAdmin.jsp" class="btn btn-default">Voltar</a>
                    </div>  
                ${msg}
            </div>
        </div>
    </body>
</html>
