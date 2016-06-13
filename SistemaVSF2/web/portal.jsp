<%-- 
    Document   : portal
    Created on : Jun 13, 2016, 12:21:26 AM
    Author     : Andre
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:out value="${cliente.nome}"  /> </br>
        <c:out value="${conta.numAgencia}"  /> </br>
        <c:out value="${conta.numConta}"  />
    </body>
</html>
