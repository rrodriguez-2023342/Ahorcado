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

        <section>
            <div class="container">
                <div class="segundo_contenedor">
                    <div class="login">
                        <div class="form">
                            <div class="text-center">
                                <h6><span>Log in </span> <span>Sign up</span></h6>
                                <input type="checkbox" class="checkbox" id="reg-log">
                                <label for="reg-log"></label>

                                <div class="card-3d-wrap">
                                    <div class="card-3d-wrapper">

                                        <!-- FORMULARIO DE LOGIN -->
                                        <div class="card-front">
                                            <div class="center-wrap">
                                                <form action="Validar" method="Post">
                                                    <h4 class="heading">Inicio de Sesión</h4>
                                                    <div class="form-group">
                                                        <input type="text" name="txtCorreo" id="txtCorreo" class="form-style" placeholder="Email" autocomplete="off" required>
                                                        <i class="input-icon material-icons"></i>
                                                    </div>
                                                    <div class="form-group">
                                                        <input type="password" name="txtPassword" id="txtPassword" class="form-style" placeholder="Contraseña" autocomplete="off" required>
                                                        <i class="input-icon material-icons"></i>
                                                    </div>
                                                    <center>
                                                        <input type="submit" class="btnIniciar" name="accion" value="Ingresar" placeholder="Entrar">
                                                    </center>
                                                </form>
                                            </div>
                                        </div>

                                        <!-- FORMULARIO DE REGISTRO -->
                                        <div class="card-back">
                                            <div class="center-wrap">
                                                <form action="Validar" method="post">
                                                    <h4 class="heading">Registrarse</h4>

                                                    <div class="form-group">
                                                        <input type="text" name="txtUsuarioR" id="usuario_registro" class="form-style" placeholder="Email" required>
                                                    </div>

                                                    <div class="form-group">
                                                        <input type="password" name="txtPasswordR" id="password_registro" class="form-style" placeholder="Contraseña" required>
                                                    </div>

                                                    <div class="form-group">
                                                        <input type="password" name="confirmar" id="confirmar_registro" class="form-style" placeholder="Confirmar contraseña" required>
                                                    </div>

                                                    <c:if test="${not empty errorRegistro}">
                                                        <div class="alert-error-small">${errorRegistro}</div>
                                                    </c:if>
                                                    <c:if test="${not empty mensajeExito}">
                                                        <div class="alert-success-small">${mensajeExito}</div>
                                                    </c:if>

                                                    <center>
                                                        <button type="submit" class="btnRegistrar" name="accion" value="Registrar">Registrarme</button>
                                                    </center>
                                                </form>
                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
</body>
</html>
