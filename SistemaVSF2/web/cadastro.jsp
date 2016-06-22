<%-- 
    Document   : cadastro
    Created on : 21/04/2016, 17:46:47
    Author     : André
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <link href="bootstrap-3.3.6-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="bootstrap-3.3.6-dist/js/bootstrap.min.js" type="text/javascript"></script>
        <title>Cadastro</title>
    </head>
    <body>
        <div class="container " role="main" style="padding-top: 100px;">
            <div class="jumbotron" style="padding-left: 250px;">
                <h2>Banco VSF - Virtude do Sistema Financeiro</h2>
                
                    <form action="Cadastros?action=cadcliente" method="POST">
                      
                            <table >
                                <label >Cadastro de cliente</label>
                                <tbody>
                                    <tr>                                        
                                        <td >
                                            <label>Nome Completo</label>
                                        </td> 
                                        <td >
                                            <label>RG</label>
                                        </td> 
                                    </tr>
                                    <tr>                                        
                                        <td >
                                            <input type="text" class="form-control" maxlength="50" size="50" name="nome" required="required" />
                                        </td>
                                        <td >                                        
                                            <input type="text" class="form-control" maxlength="9" name="rg" required="required"/>
                                        </td>
                                    </tr>
                                    <tr>

                                        <td colspan="2">
                                            <label>Endereço</label>
                                            <input type="text" class="form-control" maxlenght="40" name="endereco" required="required"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label>Número</label>
                                            <input type="text" class="form-control" maxlenght="5" name="numero" required="required"/>
                                        </td>
                                        <td>
                                            <label>Complemento</label>
                                            <input type="text" class="form-control" maxlenght="15" name="complemento" required="required"/>
                                        </td>
                                        <td>
                                            <label>CEP</label>
                                            <input type="text" class="form-control" maxlenght="8" name="cep" required="required" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label>Telefone</label>
                                            <input type="text" class="form-control" maxlenght="11" name="telefone" required="required" />
                                        </td>
                                        <td>
                                            <label>Email</label>
                                            <input type="text" class="form-control" maxlenght="25" name="email" required="required" />
                                        </td>
                                    </tr>
                                    <tr>
                                <div class="row">
                                    <c:if test="${tipoPessoa == 'F' }">
                                        <div class="col-lg-4">
                                            <div class="input-group">
                                                <span class="input-group-addon">
                                                    <input type="radio" name="tipoConta" value="F"  checked="true" />
                                                </span>
                                                <input id="cpf" type="text" name="cpf" class="form-control" value="${cpf}"  readonly="true" /> 
                                                <span class="input-group-addon">
                                                    CPF - Conta Física
                                                </span>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${tipoPessoa == 'J' }">
                                        <div class="col-lg-5">
                                            <div class="input-group">
                                                <span class="input-group-addon">
                                                    <input type="radio" name="tipoConta" value="J" checked="true"  />
                                                </span>
                                                <input id="cnpj" type="text" class="form-control" name="cnpj" class="form-control" value="${cnpj}"  readonly="true"  />
                                                <span class="input-group-addon">
                                                    CNPJ - Conta Jurídica
                                                </span>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>  
                                </tr>
                                <tr>
                                    <td colspan="3">
                                        <label> Insira sua renda mensal, de acordo com ela será definido o seu Cheque Especial(limite)</label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label>Renda</label>
                                        <input type="text"  class="form-control"maxlength="18" name="renda" required="required"/>
                                    </td>
                                    <td>
                                        <label>Senha</label>
                                        <input type="password" class="form-control" maxlength="10" name="senha" required="required"/>
                                    </td>
                                    <td>
                                        <label>Confirma Senha</label>
                                        <input type="password" class="form-control" maxlength="10" name="confirmaSenha" required="required"/>
                                    </td>
                                    <td>
                                    </td>
                                </tr>
                                <td>&nbsp; &nbsp;</td>
                                <tr>
                                    <td>
                                        <input type="reset" class="btn btn-sm btn-default" value="Cancelar" />
                                        <input type="submit" class="btn btn-sm btn-primary" value="Enviar" />
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <div>
                                </form>
                            </div>
                        
                
            </div>
    </body>
</html>
