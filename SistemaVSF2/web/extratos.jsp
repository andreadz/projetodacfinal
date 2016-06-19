<%-- 
    Document   : extratos
    Created on : Jun 13, 2016, 2:50:43 PM
    Author     : Andre
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                        <table class="table-striped table-bordered">
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
                                <c:if test="${transacoes.size() > 0}" >
                                    <c:forEach var="transac" items="${transacoes}">
                                        <tr>                                        
                                            <td style="padding: 10px;"><fmt:formatDate value="${transac.dataTransacao}" pattern="dd/MM/yyyy" /></td>
                                            <c:choose>
                                                <c:when test="${transac.tipoTransacao == 1}">
                                                    <td style="padding: 10px;">Depósito</td>
                                                </c:when>
                                                <c:when test="${transac.tipoTransacao == 2}">
                                                    <td style="padding: 10px;">Transferência para minha conta</td>
                                                </c:when>
                                                <c:when test="${transac.tipoTransacao == 3}">
                                                    <td style="padding: 10px;">Transferência para terceiros</td>
                                                </c:when>
                                                <c:when test="${transac.tipoTransacao == 4}">
                                                    <td style="padding: 10px;">Saque</td>
                                                </c:when>
                                            </c:choose>                                        
                                            <td style="padding: 10px;"><fmt:formatNumber value="${transac.valor}" type="currency" /></td>
                                            <td style="padding: 10px;">${transac.idConta1}</td>
                                            <td style="padding: 10px;">${transac.idConta2}</td>
                                            <td style="padding: 10px;"><fmt:formatNumber value="${transac.saldoConta}" type="currency" /></td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                    <c:if test="${transacoes.size() <= 0}">
                                        Nenhuma transacão realizada.
                                    </c:if>
                            </tbody>
                        </table>
                    </div>
                    <div>
                        <c:url var="allContas" value="Portal?action=todasContas" />
                        <c:url var="transfers" value="transferencias.jsp" />
                        <c:url var="transfTerc" value="transfTerceiros.jsp" />
                        <c:url var="depositos" value="depositos.jsp" />
                        <c:url var="encerrar" value="encerramento.jsp" />
                        <c:url var="sacar" value="saques.jsp" />
                        <c:url var="extCompleto" value="Portal?action=extratos" >
                            <c:param name="extrato" value="" />
                        </c:url>
                        <c:url var="extQuinzeDias" value="Portal?action=extratos" >
                            <c:param name="extrato" value="15" />
                        </c:url>
                        <c:url var="extTrintaDias" value="Portal?action=extratos" >
                            <c:param name="extrato" value="30" />
                        </c:url>
                        <a href="${allContas}" >Todas Contas</a> |
                        <a href="${extCompleto}"  >Extrato Completo</a> |
                        <a href="${extQuinzeDias}" >Extrato Últimos 15 dias</a> | 
                        <a href="${extTrintaDias}" >Extrato Últimos 30 dias</a> |
                        <c:if test="${contas.size() > 1}">
                            <a href="${transfers}" >Transferências para minha conta</a> |
                        </c:if>  
                        <a href="${transfTerc}" >Transferências para Terceiros</a> |
                        <a href="${depositos}" >Depósito</a> | 
                        <c:if test="${conta.tipoConta == 'J'}" > 
                            <a href="${sacar}" >Saque</a> | 
                        </c:if> 
                        <a href="${encerrar}" >Encerramento Conta-Corrente</a>
                    </div>
            </div>
        </div>
    </body>
</html>
