//VARIABLES
let tiempoRestante = 300; // Tiempo que dura el juego (5 minutos)
let intervalo = null; // Aquí se guardará el "reloj" que cuenta los segundos
let juegoIniciado = false; // Sirve para saber si el juego ya empezó o no
let palabras = []; // Aquí se van a guardar las palabras que vienen del servidor

let palabraSecreta = ""; // La palabra que el jugador debe adivinar
let pistaActual = ""; // Una pequeña pista para ayudar al jugador
let palabraSecretaImagen = ""; // Imagen relacionada con la palabra
let progreso = []; // Aquí se guardan las letras acertadas y los guiones
let errores = 0; // Contador de fallos del jugador
const erroresMax = 6; // Número máximo de fallos permitidos

// Imágenes que muestran el dibujo del ahorcado en cada fallo
const imagenesAhorcado = [
    "Image/ahorcado1.png", // Sin errores
    "Image/ahorcado2.png", // 1 error
    "Image/ahorcado3.png", // 2 errores
    "Image/ahorcado4.png", // 3 errores
    "Image/ahorcado5.png", // 4 errores
    "Image/ahorcado6.png", // 5 errores
    "Image/ahorcado7.png"  // 6 errores = juego perdido
];

// Esta funcion trae las palabras desde el servidor
async function cargarPalabras() {
    try {
        const response = await fetch("MostrarPalabras?action=obtenerPalabras");
        if (!response.ok)
            throw new Error("Error al obtener las palabras");

        const data = await response.json();

        // Guarda las palabras en MAYÚSCULAS y prepara la imagen
        palabras = data.map(p => {
            const ruta = "Image/" + p.palabra.toLowerCase() + ".png";
            return {
                palabra: p.palabra.toUpperCase(),
                pista: p.pista,
                imagen: ruta // Si no existe la personalizada, se pondrá una por defecto
            };
        });
    } catch (error) {
        console.error(error);
        alert("No se pudieron cargar las palabras del servidor");
    }
}


// Esta funcion actualiza el cronometro en la pantalla
function actualizarCronometro() {
    const minutos = Math.floor(tiempoRestante / 60);
    const segundos = tiempoRestante % 60;
    document.querySelector(".cronometro").textContent =
            `${String(minutos).padStart(2, "0")}:${String(segundos).padStart(2, "0")}`;
}

// Muestra la imagen del ahorcado dependiendo de los fallos
function actualizarAhorcado() {
    const imgElement = document.querySelector("#imagen-ganador img");
    imgElement.src = imagenesAhorcado[errores];
    document.getElementById("imagen-ganador").style.display = "block";
}

// Empieza el juego
function iniciarJuego() {
    if (!juegoIniciado) { // Solo si no se ha iniciado antes
        juegoIniciado = true;

        if (!palabraSecreta) { // Si aún no hay palabra seleccionada
            if (palabras.length === 0) {
                alert("No hay palabras cargadas");
                return;
            }

            // Elige una palabra al azar
            const seleccion = palabras[Math.floor(Math.random() * palabras.length)];
            palabraSecreta = seleccion.palabra;
            pistaActual = seleccion.pista;
            palabraSecretaImagen = seleccion.imagen;

            // Muestra la pista en pantalla
            document.querySelector(".pista-box").textContent = pistaActual;

            // Crea guiones bajos por cada letra de la palabra
            progreso = Array(palabraSecreta.length).fill("_");
            mostrarProgreso();

            // Activa todos los botones de letras
            document.querySelectorAll(".letra").forEach(btn => {
                btn.disabled = false;
                btn.style.backgroundColor = "";
                btn.style.color = "";
            });

            // Muestra todos los corazones (vidas)
            document.querySelectorAll(".corazon").forEach(c => c.style.visibility = "visible");

            // Reinicia la imagen del ahorcado
            errores = 0;
            actualizarAhorcado();
        }

        // Inicia el reloj que descuenta los segundos
        intervalo = setInterval(() => {
            if (tiempoRestante > 0) {
                tiempoRestante--;
                actualizarCronometro();
            } else {
                clearInterval(intervalo);
                alert("¡Se acabó el tiempo!, la palabra oculta es: " + palabraSecreta);
                reiniciarJuego();
            }
        }, 1000);
    }
}

// Reinicia el juego y vuelve todo a como estaba al principio
function reiniciarJuego() {
    clearInterval(intervalo);
    tiempoRestante = 300;
    juegoIniciado = false;
    palabraSecreta = "";
    pistaActual = "";
    palabraSecretaImagen = "";
    progreso = [];
    errores = 0;

    document.querySelector(".espacios").textContent = "__ __ __ __ __ __ __ __ __";
    document.querySelector(".pista-box").textContent = "Aquí va la pista o descripción del elemento a adivinar.";

    // Vuelve a activar todos los botones
    document.querySelectorAll(".letra").forEach(btn => {
        btn.disabled = false;
        btn.style.backgroundColor = "";
        btn.style.color = "";
    });

    // Muestra de nuevo los corazones
    document.querySelectorAll(".corazon").forEach(c => c.style.visibility = "visible");

    // Vuelve a poner la primera imagen del ahorcado
    const imgElement = document.querySelector("#imagen-ganador img");
    imgElement.src = imagenesAhorcado[0];
    document.getElementById("imagen-ganador").style.display = "block";

    actualizarCronometro();
}

// Pausa el juego
function pausarJuego() {
    clearInterval(intervalo);
    juegoIniciado = false;
}

// Muestra los guiones y letras acertadas
function mostrarProgreso() {
    document.querySelector(".espacios").textContent = progreso.join(" ");
}

// Comprueba si la letra elegida es correcta
function verificarLetra(letra, boton) {
    if (!juegoIniciado) {
        alert("Primero debes iniciar el juego con el botón INICIO.");
        return;
    }

    boton.disabled = true;
    let acierto = false;

    // Revisa si la letra aparece en la palabra
    for (let i = 0; i < palabraSecreta.length; i++) {
        if (palabraSecreta[i] === letra) {
            progreso[i] = letra;
            acierto = true;
        }
    }

    mostrarProgreso();

    if (acierto) {
        // Si la letra esta, pinta el botón en verde
        boton.style.backgroundColor = "green";
        boton.style.color = "white";
    } else {
        // Si la letra NO está, pinta el botón en rojo y aumenta el error
        boton.style.backgroundColor = "red";
        boton.style.color = "white";
        errores++;

        // Oculta un corazon
        const corazones = document.querySelectorAll(".corazon");
        if (errores <= erroresMax) {
            corazones[errores - 1].style.visibility = "hidden";
        }

        actualizarAhorcado();

        // Si ya llego al maximo de errores, pierde
        if (errores >= erroresMax) {
            clearInterval(intervalo);
            document.querySelector(".espacios").textContent = palabraSecreta.split("").join(" ");
            alert("¡Perdiste!, la palabra oculta es: " + palabraSecreta);

            // Muestra la imagen de la palabra, o la de defecto si no existe
            const imgElement = document.querySelector("#imagen-ganador img");
            imgElement.onerror = () => {
                imgElement.src = "Image/pordefecto.png";
            };
            imgElement.src = palabraSecretaImagen;
            document.getElementById("imagen-ganador").style.display = "block";
        }
    }

    // Si ya no quedan guiones, significa que gano
    if (!progreso.includes("_")) {
        clearInterval(intervalo);
        alert("¡Ganaste! La palabra oculta es: " + palabraSecreta);

        const imgElement = document.querySelector("#imagen-ganador img");
        imgElement.onerror = () => {
            imgElement.src = "Image/pordefecto.png";
        };
        imgElement.src = palabraSecretaImagen;
        document.getElementById("imagen-ganador").style.display = "block";
    }
}

//AL CARGAR LA PAGINA
document.addEventListener("DOMContentLoaded", async () => {
    // Carga las palabras desde el servidor
    await cargarPalabras();

    // Hace que cada botón de letra funcione
    document.querySelectorAll(".letra").forEach(btn => {
        btn.addEventListener("click", (e) => {
            const letra = e.target.textContent;
            verificarLetra(letra, btn);
        });
    });

    // Muestra el cronómetro desde el inicio
    actualizarCronometro();

    // Pone el ahorcado vacío al iniciar
    actualizarAhorcado();
});
