<%-- 
    Document   : todasContas
    Created on : Jun 13, 2016, 2:50:33 PM
    Author     : Andre
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="bootstrap-3.3.6-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="bootstrap-3.3.6-dist/js/bootstrap.min.js" type="text/javascript"></script>
        <title>Banco VSF</title>
    </head>
    <body>
         <div class="container theme-showcase" role="main" style="padding-top: 100px;">
            <div class="jumbotron" style="padding-left: 250px;">
                <h3>Banco VSF - Virtude do Sistema Financeiro</h1>
                    <div>
                        <form action="Portal?action=acessar" method="POST">
                            <table>
                                <tbody>
                                    <tr>
                                        <td style="padding: 10px;">
                                            Olá, <c:out value="${cliente.nome}"  />                                            
                                        </td>
                                        <td style="padding: 10px;">
                                            <c:out value="${conta.numAgencia}"  />
                                        </td>
                                        <td style="padding: 10px;">
                                            <c:out value="${conta.numConta}"  />
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
                        <table>
                            <thead>
                                <tr>
                                    <th style="padding: 5px;">Conta Atual</th>
                                    <th style="padding: 5px;">Saldo</th>
                                    <th style="padding: 5px;">Limite</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="contaCli" items="${contas}" >                                
                                <tr>
                                    <td style="padding: 5px;"> ${contaCli.numConta} </td>
                                    <td style="padding: 5px;"> ${contaCli.saldo} </td>
                                    <td style="padding: 5px;"> ${contaCli.limite} </td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>                       
                    </div>
                    <div>
                        <a href="todasContas.jsp" >Todas Contas</a> |
                        <a href="extratos.jsp" >Extratos</a> | 
                        <a href="transferencias.jsp" >Transferências</a> |
                        <a href="depositos.jsp" >Depósito</a> | 
                        <c:if test="${conta.tipoConta == 'J'}" > 
                            <a href="saques.jsp" >Saque</a> | 
                        </c:if> 
                            <a href="Portal?action=encerramento" >Encerramento Conta-Corrente</a>
                    </div>
            </div>
        </div>
    </body>
</html>
