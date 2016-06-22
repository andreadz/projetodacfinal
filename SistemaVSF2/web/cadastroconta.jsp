<%-- 
    Document   : cadastroconta
    Created on : Jun 11, 2016, 4:14:23 PM
    Author     : Andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
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
               
                        <form action="Cadastros?action=cadconta" method="POST">
                        
                            <table >
                                <label>Abertura de conta corrente</label>
                                <tbody>
                                    <tr>                                        
                                        <td >
                                            <select name="agencia" class="focus" class="form-control">
                                                <option value="7859">Agência Agrárias - Cabral</option>
                                                <option value="7055">Agência Santos Andrade - Centro</option>
                                                <option value="7089">Agência Reitoria - Centro</option>
                                                <option value="6599">Agência SEPT - Jardim das Américas</option>
                                                <option value="6523">Agência Politécnico - Jardim das Américas</option>
                                            </select>
                                        </td> 
                                    </tr>     
                                    <tr>
                                        ${msg}
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
                        
               
                </body>
</html>
