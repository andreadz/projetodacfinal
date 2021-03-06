<%-- 
    Document   : index
    Created on : 15/04/2016, 19:11:55
    Author     : André
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <c:if test="${msg.equals('Login e/ou senha incorretos.') || msg.equals('Conta inativa.')}">
                <div class="alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only">Error:</span>
                    ${msg}
                </div>
            </c:if>
            <c:if test="${msg.equals('Conta encerrada com sucesso!')}">
                <div class="alert alert-success" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only">Sucesso</span>
                    ${msg}
                </div>
            </c:if>
            <c:if test="${msg.equals('Conta cadastrada com sucesso!')}">
                <div class="alert alert-success" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only">Sucesso</span>
                    ${msg}
                </div>
            </c:if>

            <div class="jumbotron" style="padding-left: 250px;">
                <h3>Banco VSF - Virtude do Sistema Financeiro</h3>
                    <div>
                        <form action="ProcessaLoginLogout?action=acessar" method="POST">
                            <table>
                                <tbody>
                                    <tr>
                                        <td style="padding: 10px;">
                                            <label>Agência</label>            
                                            <input type="text" class="form-control"  name="agencia" maxlength="4" size="4"  />
                                        </td>
                                        <td style="padding: 10px;">
                                            <label>Conta</label>
                                            <input type="text" class="form-control"  name="numConta" maxlength="6" size="6" /> 
                                        </td>
                                        <td style="padding: 10px;">
                                            <label>Senha</label>
                                            <input type="password" class="form-control" name="senha" maxlength="10" />
                                        </td>
                                        <td style="padding-left: 10px;">
                                            <input type="submit" class="btn btn-group-sm btn-primary" value="Acessar" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="padding-top:0px;">
                                            <a href="verificaExistencia.jsp" class="btn btn-lg btn-link">Cadastre-se aqui</a>
                                        </td>
                                        <td colspan="3"></td>                                        
                                    </tr>
                                </tbody>
                            </table>
                            <%-- ${msg} </br> --%>
                            <c:if test="${conta != null}">
                                Dados conta: </br>
                                Agência: <c:out value="${conta.numAgencia}"/> </br>
                                Conta: <c:out value="${conta.numConta}"/> 
                            </c:if>
                        </form>
                    </div>
            </div>
        </div>
    </body>
</html>
