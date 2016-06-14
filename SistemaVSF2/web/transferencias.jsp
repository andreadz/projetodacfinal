<%-- 
    Document   : transferencias
    Created on : Jun 13, 2016, 2:50:56 PM
    Author     : Andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <form method="POST" action="Portal?action=transferir">
                            <select name="contaTransferir">
                                <c:forEach  var="minhaConta" items="${contas}">
                                    <c:if test="${minhaConta.numConta == conta.numConta}">
                                        <option disabled="true" value="${minhaConta.id}">${minhaConta.numAgencia}  -  ${minhaConta.numConta}</option>
                                    </c:if>
                                    <c:if test="${minhaConta.numConta != conta.numConta}">
                                        <option value="${minhaConta.id}">${minhaConta.numAgencia}  -  ${minhaConta.numConta}</option>
                                    </c:if>
                                </c:forEach>                            
                            </select>    
                            
                            </br>
                            Valor: <input type="text" name="valor" /></br>
                            Token: <input type="text" name="token" /></br>
                            <input type="reset" class="btn btn-sm btn-default" value="Cancelar" /> &nbsp;
                            <input type="submit" class="btn btn-sm btn-primary" value="Enviar" />
                        </form>
                    </div>
                    <div>
                        <c:url var="allContas" value="Portal?action=todasContas" />
                        <c:url var="transfers" value="transferencias.jsp" />
                        <c:url var="transfTerc" value="transfTerceiros.jsp" />
                        <c:url var="depositos" value="depositos.jsp" />
                        <c:url var="encerrar" value="encerramento.jsp" />
                        <c:url var="sacar" value="saques.jsp" />
                        <c:url var="extCompleto" value="Portal?action=extratos" >
                            <param name="extrato" value="" />
                        </c:url>
                        <c:url var="extQuinzeDias" value="Portal?action=extratos" >
                            <param name="extrato" value="15" />
                        </c:url>
                        <c:url var="extTrintaDias" value="Portal?action=extratos" >
                            <param name="extrato" value="30" />
                        </c:url>
                        <a href="${allContas}" >Todas Contas</a> |
                        <a href="${extCompleto}"  >Extrato Completo</a> |
                        <a href="${extQuinzeDias}" >Extrato Últimos 15 dias</a> | 
                        <a href="${extTrintaDias}" >Extrato Últimos 30 dias</a> |
                        <a href="#" >Transferências para minha conta</a> |
                        <a href="${transfTerc}" >Transferências para Terceiros</a> |
                        <a href="${depositos}" >Depósito</a> | 
                        <c:if test="${conta.tipoConta == 'J'}" > 
                            <a href="sacar" >Saque</a> | 
                        </c:if> 
                        <a href="encerrar" >Encerramento Conta-Corrente</a>
                    </div>
            </div>
        </div>
    </body>
</html>
