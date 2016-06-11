<%-- 
    Document   : cadastroconta
    Created on : Jun 11, 2016, 4:14:23 PM
    Author     : Andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="bootstrap-3.3.6-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="bootstrap-3.3.6-dist/js/bootstrap.min.js" type="text/javascript"></script>
        <title>Cadastro</title>
    </head>
    <body>
        <div class="container theme-showcase" role="main" style="padding-top: 100px;">
            <div class="" style="padding-left: 250px;">
                <h2>Banco VSF - Virtude do Sistema Financeiro</h2>
                <div>
                    <form action="Cadastros?action=cadconta" method="POST">
                        <div class="panel panel-default">
                            <table class="panel-heading">
                                <labe class="panel-title">Abertura de conta corrente</labe>
                                <tbody class="panel-body">
                                    <tr>                                        
                                        <td class="col-md-2">
                                            <select name="agencia" class="focus">
                                                <option value="7859">Agência Agrárias - </option>
                                                <option value="1569">Agência Santos Andrade - Centro</option>
                                                <option value="4411">Agência Reitoria - Centro</option>
                                                <option value="0616">Agência SEPT - Jardim das Américas</option>
                                                <option value="2316">Agência Politécnico - Jardim das Américas</option>
                                            </select>
                                        </td> 
                                    </tr>                                    
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
                </div>
                </body>
</html>
