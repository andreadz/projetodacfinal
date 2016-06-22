<%-- 
    Document   : novoUsuario
    Created on : 16/06/2016, 18:07:46
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
       <div class="container " role="main" style="padding-top: 100px;">
            <div class="jumbotron" style="padding-left: 250px;">
                <h2>DOR</h2>
                
                    <form action="PortalAdmin?action=cadUsuario" method="POST">
                       
                            <table >
                                <label>Cadastro de usuário</label>
                                <tbody >
                                    <tr>                                        
                                        <td >
                                            <label>Nome Completo</label>
                                        </td> 
                                    </tr>
                                    <tr>                                        
                                        <td >
                                            <input type="text" class="form-control" maxlength="50" size="50" name="nome" required="required" />
                                        </td>
                                    </tr>                                    
                                    <tr>                                        
                                        <td>
                                            <label>Email</label>
                                            <input type="text" class="form-control" maxlenght="50" name="email" required="required" />
                                        </td>
                                        <td>
                                            <label>Perfil</label>
                                            <select name="perfilUsuario" class="form-control">
                                                <option value="1">Administrador</option>
                                                <option value="2">Usuário</option>
                                            </select>
                                        </td>
                                    </tr>                                    
                                    <tr>                                    
                                        <td>
                                            <label>Senha</label>
                                            <input type="password" class="form-control" maxlength="10" name="senha" required="required"/>
                                        </td>
                                        <td>
                                            <label>Confirma Senha</label>
                                            <input type="password" class="form-control" maxlength="10" name="confirmaSenha" required="required"/>
                                        </td>
                                    </tr>
                                    <tr><td>&nbsp; &nbsp;</td></tr>
                                    <tr>
                                        <td>
                                            <a href="portalAdmin.jsp" class="btn btn-default">Cancelar</a>
                                            <input type="submit" class="btn btn-sm btn-primary" value="Enviar" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                    </form>
                    ${msg}
                
           
        </div>
    </div>
</body>
</html>
