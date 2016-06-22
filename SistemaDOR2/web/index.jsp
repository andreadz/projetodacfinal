<%-- 
    Document   : index
    Created on : 16/06/2016, 14:50:33
    Author     : sdgdsgd
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link href="bootstrap-3.3.6-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="bootstrap-3.3.6-dist/js/bootstrap.min.js" type="text/javascript"></script>
        <title>DOR</title>
    </head>
    <body>        
        <div class="container theme-showcase" role="main" style="padding-top: 100px;">
            <c:if test="">
                <div class="alert alert-danger" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only bg-danger">Error:</span>
                    ${msg}
                </div>
            </c:if>
            <c:if test="">
                <div class="alert alert-success" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only bg-success">Sucesso</span>
                    ${msg}
                </div>
            </c:if>

            <div class="jumbotron" style="padding-left: 250px;">
                <h2 class="form-signin-heading">DOR - Devedores Originalmente Regulares</h2>
                    <div class="container">
                        <form action="ProcessaLoginLogout?action=acessar" method="POST" class="form-signin">
                            
                            <table>
                                <tbody>
                                    <tr>
                                        <td >
                                                      
                                            <input type="email" name="email" maxlength="50" required="required" class="form-control" placeholder="Email" />
                                        </td>
                                        </tr>
                                        <tr>
                                        <td >
                                            
                                            <input type="password" name="senha" maxlength="15" required="required"  class="form-control" placeholder="Senha"/>
                                        </td>                                        
                                        </tr>
                                        <tr><td>&nbsp; &nbsp;</td></tr>
                                        <tr><td style="padding-left: 10px;">
                                            <input type="submit" class="btn btn-group-sm btn-primary" value="Acessar" />
                                        </td>
                                    
                                </tbody>
                            </table>
                            
                        </form>
                    </div>
            </div>
            ${msg}
        </div>
    </body>
</html>