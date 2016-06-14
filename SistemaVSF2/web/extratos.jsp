<%-- 
    Document   : extratos
    Created on : Jun 13, 2016, 2:50:43 PM
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
                        <form>
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
                                    <th>Data Trans.</th>
                                    <th>Tipo Transação</th>
                                    <th>Valor</th>
                                    <th>Conta Operação</th>
                                    <th>Conta Beneficiada</th>
                                    <th>Saldo </th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="transac" items="${transacoes}">
                                    <tr>
                                        <td>${transac.dataTransacao}</td>
                                        <c:choose>
                                            <c:when test="${transac.tipoTransacao} == 1">
                                                <td>Depósito</td>
                                            </c:when>
                                            <c:when test="${transac.tipoTransacao} == 2">
                                                <td>Transferência para minha conta</td>
                                            </c:when>
                                            <c:when test="${transac.tipoTransacao} == 3">
                                                <td>Transferência para terceiros</td>
                                            </c:when>
                                            <c:when test="${transac.tipoTransacao} == 4">
                                                <td>Saque</td>
                                            </c:when>
                                        </c:choose>                                        
                                        <td>${transac.valor}</td>
                                        <td>${transac.idConta1}</td>
                                        <td>${transac.idConta2}</td>
                                        <td>${transac.saldoConta}</td>
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
