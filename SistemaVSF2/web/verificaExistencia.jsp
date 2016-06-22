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
                <h3>Banco VSF - Verifica Existência</h3>
                    <form action="Verificacoes?action=verificaExistencia" method="POST">                        
                        <div class="row">
                            <div class="col-lg-4">
                                <div class="input-group">
                                    <span class="input-group-addon">
                                        <input type="radio" name="tipoPessoa" value="F"  />
                                    </span>
                                    <input id="cpf" type="text" name="cpf" class="form-control"  maxlength="11" /> 
                                    <span class="input-group-addon">
                                        CPF
                                    </span>
                                </div>
                            </div>
                            <div class="col-lg-5">
                                <div class="input-group">
                                    <span class="input-group-addon">
                                        <input type="radio" name="tipoPessoa" value="J"  />
                                    </span>
                                    <input id="cnpj" type="text" name="cnpj" class="form-control" maxlength="14"  />
                                    <span class="input-group-addon">
                                        CNPJ
                                    </span>
                                </div>
                            </div>
                            <input type="submit" value="Verificar" class="btn btn-info" />
                        </div>                                               
                    </form>
            </div>
        </div>
    </body>  
</html>
