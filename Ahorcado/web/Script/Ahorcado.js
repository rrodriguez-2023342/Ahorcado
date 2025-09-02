let tiempoRestante = 300;
let intervalo = null;
let juegoIniciado = false;

const palabras = [
    {palabra: "PROGRAMAR", pista: "Acción de crear instrucciones para una computadora."},
    {palabra: "ESTUDIAR", pista: "Ejercitar el entendimiento para alcanzar o comprender algo."},
    {palabra: "AHORCADO", pista: "Nombre del juego que estás jugando ahora."},
    {palabra: "COMPUTADORA", pista: "Máquina que procesa información."},
    {palabra: "PROFESORES", pista: "Persona que ejerce o enseña una ciencia o arte."}
];

let palabraSecreta = "";
let pistaActual = "";
let progreso = [];
let errores = 0;
const erroresMax = 6;

// Mostrar cronómetro
function actualizarCronometro() {
    const minutos = Math.floor(tiempoRestante / 60);
    const segundos = tiempoRestante % 60;
    document.querySelector(".cronometro").textContent =
            `${String(minutos).padStart(2, "0")}:${String(segundos).padStart(2, "0")}`;
}

// Iniciar juego
function iniciarJuego() {
    if (juegoIniciado)
        return;
    juegoIniciado = true;

    // Seleccionar palabra random con pista
    const seleccion = palabras[Math.floor(Math.random() * palabras.length)];
    palabraSecreta = seleccion.palabra;
    pistaActual = seleccion.pista;

    document.querySelector(".pista-box").textContent = pistaActual;

    // Inicializar progreso
    progreso = Array(palabraSecreta.length).fill("_");
    errores = 0;
    mostrarProgreso();

    // Reiniciar estado de letras
    document.querySelectorAll(".letra").forEach(btn => {
        btn.disabled = false;
        btn.style.backgroundColor = "";
    });

    // Iniciar cronómetro
    intervalo = setInterval(() => {
        if (tiempoRestante > 0) {
            tiempoRestante--;
            actualizarCronometro();
        } else {
            clearInterval(intervalo);
            alert("¡Se acabo el tiempo!, la palabra oculta es: " + palabraSecreta);
            reiniciarJuego();
        }
    }, 1000);
}

// Reiniciar juego
function reiniciarJuego() {
    clearInterval(intervalo);
    tiempoRestante = 300;
    juegoIniciado = false;
    palabraSecreta = "";
    pistaActual = "";
    progreso = [];
    errores = 0;

    document.querySelector(".espacios").textContent = "__ __ __ __ __ __ __ __ __";
    document.querySelector(".pista-box").textContent = "Aquí va la pista o descripción del elemento a adivinar.";

    // Reiniciar botones
    document.querySelectorAll(".letra").forEach(btn => {
        btn.disabled = false;
        btn.style.backgroundColor = "";
        btn.style.color = "";
    });

    actualizarCronometro();
}

// Pausar
function pausarJuego() {
    clearInterval(intervalo);
    juegoIniciado = false;
}

// Mostrar progreso
function mostrarProgreso() {
    document.querySelector(".espacios").textContent = progreso.join(" ");
}

// Verificar letra
function verificarLetra(letra, boton) {
    if (!juegoIniciado) {
        alert("Primero debes iniciar el juego con el botón INICIO.");
        return;
    }

    boton.disabled = true; // Bloquear la letra una vez usada

    let acierto = false;
    for (let i = 0; i < palabraSecreta.length; i++) {
        if (palabraSecreta[i] === letra) {
            progreso[i] = letra;
            acierto = true;
        }
    }

    mostrarProgreso();

    if (acierto) {
        boton.style.backgroundColor = "green";
        boton.style.color = "white";
    } else {
        boton.style.backgroundColor = "red";
        boton.style.color = "white";
        errores++;

        if (errores >= erroresMax) {
            clearInterval(intervalo);
            document.querySelector(".espacios").textContent = palabraSecreta.split("").join(" ");
            alert("¡Perdiste!, la palabra oculta es: " + palabraSecreta);
        }
    }

    // Verificar si ganó
    if (!progreso.includes("_")) {
        clearInterval(intervalo);
        alert("¡Ganaste! La palabra oculta es: " + palabraSecreta);
    }
}

// Eventos
document.addEventListener("DOMContentLoaded", () => {

    document.querySelectorAll(".letra").forEach(btn => {
        btn.addEventListener("click", (e) => {
            const letra = e.target.textContent;
            verificarLetra(letra, btn);
        });
    });

    actualizarCronometro();
});
