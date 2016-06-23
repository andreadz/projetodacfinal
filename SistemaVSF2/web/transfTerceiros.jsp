<%-- 
    Document   : transfTerceiros
    Created on : Jun 14, 2016, 3:03:17 PM
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
                                            <a href="ProcessaLoginLogout?action=logout" >Logout</a> <br/>
                                        </td>                                        
                                    </tr>                                    
                                </tbody>
                            </table>
                        </form>
                    </div>  
                    <div>
                        <form method="POST" action="Portal?action=confirmaTransferencia">

                            Agência/Conta <input type="text" class="form-control" style="width:20%"  value="${conta.numAgencia}/${conta.numConta}" readonly="true" />&nbsp;
                            Agência <input type="text" class="form-control" style="width:20%"  name="agenciaDestino" required="required" />
                          Conta Destino  <input type="text" name="contaDestino"  class="form-control" style="width:20%" required="required" />
                            </br>
                            Valor &nbsp;<input type="text" class="form-control" style="width:20%" name="valor" required="required" /></br>
                            <!--Token: <input type="text" name="token" /></br> -->
                            <input type="reset" class="btn btn-sm btn-default" value="Cancelar" /> &nbsp;
                            <input type="submit" class="btn btn-sm btn-warning" value="Transferir" />
                        </form>
                        </br></br>
                        <c:if test="${contaRecebeTransf != null}">
                            <form method="POST" action="Portal?action=transferirTerceiros">
                                Transferência de: <input type="text" style="width:20%" value="${conta.numAgencia}/${conta.numConta}" readonly="true" class="form-control" /> </br></br>
                                Para: &nbsp;<input type="text" class="form-control"  style="width:20%" name="agenciaContaDestino"  value="${contaRecebeTransf.numAgencia}/${contaRecebeTransf.numConta}" readonly="true" /> &nbsp;
                                Nome: &nbsp;<input type="text" class="form-control" style="width:20%"   value="${clienteRecebeTransf.nome}" readonly="true" /> 

                                <c:if test="${clienteRecebeTransf.cnpj != null}">
                                    CNPJ &nbsp;<input type="text"  value="${clienteRecebeTransf.cnpj}" style="width:20%"  readonly="true" /></br> </br>
                                </c:if>
                                <c:if test="${clienteRecebeTransf.cpf != null}">
                                    CPF: &nbsp;<input type="text" class="form-control" style="width:20%"  value="${clienteRecebeTransf.cpf}" readonly="true" /></br> </br>
                                </c:if>

                                Valor de : &nbsp;<input type="text"  class="form-control" style="width:20%"  name="valorTransferencia" value="${valorTransferencia}" readonly="true" /></br>
                                <a href="transfTerceiros.jsp" class="btn btn-sm btn-default" >Cancelar </a> &nbsp;
                                <input type="submit" class="btn btn-sm btn-warning" value="Confirmar" />
                            </form>
                        </c:if>
                    </div>
                    <div>
                        <c:url var="cadastrarConta" value="cadastroNovaConta.jsp" />
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
                        <a href="portal.jsp">Portal</a> |
                        <a href="${cadastrarConta}" >Cadastrar Nova Conta</a> |
                        <a href="${allContas}" >Todas Contas</a> |
                        <a href="${extCompleto}"  >Extrato Completo</a> |
                        <a href="${extQuinzeDias}" >Extrato Últimos 15 dias</a> | 
                        <a href="${extTrintaDias}" >Extrato Últimos 30 dias</a>
                        <c:if test="${contas.size() > 1}">
                            <a href="${transfers}" >Transferências para minha conta</a> |
                        </c:if> 
                        <a href="#" >Transferências para Terceiros</a> |
                        <a href="${depositos}" >Depósito</a> | 
                        <c:if test="${conta.tipoConta == 'J'}" > 
                            <a href="${sacar}" >Saque</a> | 
                        </c:if> 
                        <a href="${encerrar}" >Encerramento Conta-Corrente</a>
                    </div>
                    ${msg} </br>
                    ${mensagemDOR}
            </div>
        </div>
    </body>
</html>
