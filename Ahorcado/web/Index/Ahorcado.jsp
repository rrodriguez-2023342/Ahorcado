<%-- 
    Document   : Ahorcado
    Created on : 2 sept 2025, 08:22:21
    Author     : Roberto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="${pageContext.request.contextPath}/Image/logo.png"/>
        <link href="https://fonts.googleapis.com/css2?family=Cinzel:wght@400;700&family=Press+Start+2P&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Styles/Ahorcado.css"/>
        <title>Ahorcado</title>
    </head>
    <body>
        <div class="container">
            <header class="top">
                <h1 class="titulo">AHORCADO</h1>
                <div class="status">
                    <div class="cronometro">05:00</div>
                    <div class="vidas">
                        <img src="${pageContext.request.contextPath}/Image/corazon.PNG" class="corazon" alt="vida">
                        <img src="${pageContext.request.contextPath}/Image/corazon.PNG" class="corazon" alt="vida">
                        <img src="${pageContext.request.contextPath}/Image/corazon.PNG" class="corazon" alt="vida">
                        <img src="${pageContext.request.contextPath}/Image/corazon.PNG" class="corazon" alt="vida">
                        <img src="${pageContext.request.contextPath}/Image/corazon.PNG" class="corazon" alt="vida">
                        <img src="${pageContext.request.contextPath}/Image/corazon.PNG" class="corazon" alt="vida">
                    </div>
                </div>
            </header>

            <section class="area-juego">
                <div class="left">
                    <div class="caja-letras">
                        <div class="espacios">__ __ __ __ __ __ __ __ __</div>
                    </div>

                    <div class="alfabeto">
                        <div class="alfa-fila">
                            <button class="letra">A</button>
                            <button class="letra">B</button>
                            <button class="letra">C</button>
                            <button class="letra">D</button>
                            <button class="letra">E</button>
                            <button class="letra">F</button>
                            <button class="letra">G</button>
                            <button class="letra">H</button>
                            <button class="letra">I</button>
                            <button class="letra">J</button>
                            <button class="letra">K</button>
                            <button class="letra">L</button>
                            <button class="letra">M</button>
                        </div>
                        <div class="alfa-fila">
                            <button class="letra">N</button>
                            <button class="letra">Ñ</button>
                            <button class="letra">O</button>
                            <button class="letra">P</button>
                            <button class="letra">Q</button>
                            <button class="letra">R</button>
                            <button class="letra">S</button>
                            <button class="letra">T</button>
                            <button class="letra">U</button>
                            <button class="letra">V</button>
                            <button class="letra">W</button>
                        </div>
                        <div class="alfa-fila">
                            <button class="letra">X</button>
                            <button class="letra">Y</button>
                            <button class="letra">Z</button>
                        </div>
                    </div>

                    <div class="contenedor-pista">
                        <label for="pista">Pista:</label>
                        <div id="pista" class="pista-box">Aquí va la pista o descripción del elemento a adivinar.</div>
                    </div>
                </div>

                <aside class="right">
                    <div class="dibujo" aria-hidden="true">
                        <div id="imagen-ganador" class="imagen-ganador" style="display:none;">
                            <img src="" alt="Imagen del objeto">
                        </div>  
                    </div>

                    <div class="controls">
                        <button class="btn-inicio" onclick="iniciarJuego()">INICIO</button>
                        <button class="btn-reiniciar" onclick="reiniciarJuego()">REINICIAR</button>
                        <button class="btn-pausa" onclick="pausarJuego()">PAUSA</button>
                        <button class="btn-salir"><a href="Controlador?menu=Index">SALIR</a></button>
                    </div>
                </aside>
            </section>
        </div>
        <script src="${pageContext.request.contextPath}/Script/Ahorcado.js"></script>
    </body>
</html>
