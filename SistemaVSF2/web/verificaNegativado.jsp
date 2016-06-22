<%-- 
    Document   : verificaNegativado
    Created on : 21/05/2016, 01:27:56
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
        <title>Banco VSF</title>
    </head>
    <body>
        <div class="container theme-showcase" role="main" style="padding-top: 100px;">
            <div class="jumbotron" style="padding-left: 250px;">
                <form action="Verificacoes?action=verificaNegativado" method="POST">
                    <h3>Banco VSF - Verifica Negativação</h1>
                    <table>                
                        <tr>                    
                            <td>
                                <input type="radio"   name="tipoPessoa" value="J"  />
                            </td>
                            <td>
                                CNPJ
                            </td>
                            <td>                   
                                <input type="text" class="form-control"  name="cnpj" />           
                            </td>
                            <td>
                                CPF
                            </td>
                            <td>
                                <input type="radio" name="tipoPessoa" value="F"  />
                            </td>
                            <td>
                                <input type="text" class="form-control"  name="cpf" />     
                            </td>
                            <td>
                                <input type="submit" value="Verificar"  class="btn btn-info"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div></div>
    </body>
</html>
