<%-- 
    Document   : editarUsuario
    Created on : 16/06/2016, 16:18:46
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
                <h2 class="form-signin-heading">DOR</h2>
                <div>
                    <form action="PortalAdmin?action=editarUsuario" method="POST" class="form-group-sm" >
                        
                            <table >
                                <label>Edição de usuário</label>
                                <tbody c>
                                    <tr>                                        
                                        <td>
                                            <label>Nome Completo</label>
                                        </td> 
                                    </tr>
                                    <tr>                                        
                                        <td >
                                            <input type="text" class="form-control" maxlength="50" size="50" name="nome" required="required" value="${usuario.nome}" />
                                        </td>
                                    </tr>                                    
                                    <tr>                                        
                                        <td>
                                            <label>Email</label>
                                            <input type="text" class="form-control" maxlenght="50" name="email" required="required" value="${usuario.email}" />
                                        </td>
                                        <td style="margin-left: 10px ">
                                            
                                            <label>Perfil</label>
                                            <select name="perfilUsuario" class="form-control">
                                                <c:if test="${usuario.perfil == 1}" >
                                                    <option value="1" selected="selected">Administrador</option>
                                                    <option value="2">Usuário</option>
                                                </c:if>
                                                <c:if test="${usuario.perfil == 2}" >
                                                    <option value="1">Administrador</option>
                                                    <option value="2" selected="selected">Usuário</option>
                                                </c:if>
                                            </select>                                            
                                        </td>
                                    </tr>    
                                    <tr><td>&nbsp; &nbsp;</td></tr>
                                    <tr>
                                        <td>
                                            <a href="portalAdmin.jsp" class="btn btn-default">Cancelar</a>
                                            <input type="submit" class="btn  btn-primary" value="Enviar" />
                                        </td>
                                    </tr>
                                    <input type="hidden" name="idUsuario" value="${usuario.id}" />
                                </tbody>
                            </table>
                    </form>
                    ${msg}
                
            </div>
        </div>
        </div>
    
</body>
</html>
