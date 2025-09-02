<%-- 
    Document   : index
    Created on : 2 sept 2025, 08:15:40
    Author     : Roberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="${pageContext.request.contextPath}/Image/logo.png"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Styles/index.css"/>
        <title>Inicio</title>
    </head>
    <body>
        <div class="contenedor">
            <h1>AHORCADO</h1>
            <button class="boton">
                <a href="Controlador?menu=Ahorcado">Jugar</a>
            </button> 
        </div>
    </body>
</html>
