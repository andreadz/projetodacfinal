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
        <div class="container theme-showcase" role="main" style="padding-top: 100px;">
            <div class="" style="padding-left: 250px;">
                <h2>DOR</h2>
                <div>
                    <form action="PortalAdmin?action=editarUsuario" method="POST">
                        <div class="panel panel-default">
                            <table class="panel-heading">
                                <labe class="panel-title">Edição de usuário</labe>
                                <tbody class="panel-body">
                                    <tr>                                        
                                        <td class="col-md-2">
                                            <label>Nome Completo</label>
                                        </td> 
                                    </tr>
                                    <tr>                                        
                                        <td class="col-md-2">
                                            <input type="text" maxlength="50" size="50" name="nome" required="required" value="${usuario.nome}" />
                                        </td>
                                    </tr>                                    
                                    <tr>                                        
                                        <td>
                                            <label>Email</label>
                                            <input type="text" maxlenght="50" name="email" required="required" value="${usuario.email}" />
                                        </td>
                                        <td>
                                            <label>Perfil</label>
                                            <select name="perfilUsuario">
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
                                    <tr>
                                        <td>
                                            <input type="reset" class="btn btn-sm btn-default" value="Cancelar" />
                                            <input type="submit" class="btn btn-sm btn-primary" value="Enviar" />
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
    </div>
</body>
</html>
