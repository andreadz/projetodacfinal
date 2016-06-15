<%-- 
    Document   : verificaExistencia
    Created on : 21/05/2016, 18:48:45
    Author     : André
--%>

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
                    <form action="Verificacoes?action=verificaExistencia" method="POST">
                        <table>                
                            <tr>                    
                                <td>
                                    <input type="radio" name="tipoPessoa" value="J"  />
                                </td>
                                <td>
                                    CNPJ
                                </td>
                                <td>                   
                                    <input id="cnpj" type="text" name="cnpj" />           
                                </td>
                                <td>
                                    CPF
                                </td>
                                <td>
                                    <input type="radio" name="tipoPessoa" value="F"  />
                                </td>
                                <td>
                                    <input id="cpf" type="text" name="cpf" />     
                                </td>
                                <td>
                                    <input type="submit" value="Verificar"  />
                                </td>
                            </tr>
                        </table>
                        ${msg}                             
                    </form>
            </div>
        </div>
    </body>  
</html>
