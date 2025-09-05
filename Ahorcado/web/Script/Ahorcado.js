// Declaración de variables
let tiempoRestante = 300; // Tiempo en segundos
let intervalo = null; // Guardará el setInterval del cronómetro
let juegoIniciado = false; // Indica si el juego está activo
let palabras = []; // Array que se llenará desde la base de datos

let palabraSecreta = ""; // Palabra que el jugador debe adivinar
let pistaActual = ""; // Pista de la palabra actual
let palabraSecretaImagen = ""; // Imagen asociada a la palabra
let progreso = []; // Letras acertadas y guiones
let errores = 0; // Contador de errores
const erroresMax = 6; // Máximo de errores permitidos

// Cargar palabras desde el servlet
async function cargarPalabras() {
    try {
        const response = await fetch("MostrarPalabras?action=obtenerPalabras"); // Llamar al servlet
        if (!response.ok)
            throw new Error("Error al obtener las palabras"); // Si falla, lanzar error

        const data = await response.json(); // Convertir la respuesta en JSON

        // Guardar palabras en mayúsculas y agregar ruta de imagen
        palabras = data.map(p => ({
                palabra: p.palabra.toUpperCase(),
                pista: p.pista,
                imagen: "Image/" + p.palabra.toLowerCase() + ".png"
            }));
    } catch (error) {
        console.error(error); // Mostrar error en consola
        alert("No se pudieron cargar las palabras del servidor"); // Alerta al usuario
    }
}

// Actualizar cronómetro en pantalla
function actualizarCronometro() {
    const minutos = Math.floor(tiempoRestante / 60);
    const segundos = tiempoRestante % 60;
    document.querySelector(".cronometro").textContent =
            `${String(minutos).padStart(2, "0")}:${String(segundos).padStart(2, "0")}`;
}

// Iniciar juego
function iniciarJuego() {
    if (!juegoIniciado) { // Solo si el juego no ha iniciado
        juegoIniciado = true;

        if (!palabraSecreta) { // Si no hay palabra seleccionada
            if (palabras.length === 0) {
                alert("No hay palabras cargadas");
                return;
            }

            // Seleccionar palabra aleatoria
            const seleccion = palabras[Math.floor(Math.random() * palabras.length)];
            palabraSecreta = seleccion.palabra;
            pistaActual = seleccion.pista;
            palabraSecretaImagen = seleccion.imagen;

            document.querySelector(".pista-box").textContent = pistaActual; // Mostrar pista

            progreso = Array(palabraSecreta.length).fill("_"); // Inicializar guiones
            mostrarProgreso();

            // Habilitar botones de letras
            document.querySelectorAll(".letra").forEach(btn => {
                btn.disabled = false;
                btn.style.backgroundColor = "";
                btn.style.color = "";
            });

            // Mostrar corazones (vidas)
            document.querySelectorAll(".corazon").forEach(c => c.style.visibility = "visible");

            // Ocultar imagen de ganador
            document.querySelector("#imagen-ganador img").src = "";
            document.getElementById("imagen-ganador").style.display = "none";
        }

        // Iniciar cronómetro
        intervalo = setInterval(() => {
            if (tiempoRestante > 0) {
                tiempoRestante--;
                actualizarCronometro();
            } else {
                clearInterval(intervalo); // Detener cronómetro
                alert("¡Se acabó el tiempo!, la palabra oculta es: " + palabraSecreta);
                reiniciarJuego(); // Reiniciar juego
            }
        }, 1000); // Ejecutar cada segundo
    }
}

// Reiniciar juego
function reiniciarJuego() {
    clearInterval(intervalo); // Detener cronómetro
    tiempoRestante = 300;
    juegoIniciado = false;
    palabraSecreta = "";
    pistaActual = "";
    palabraSecretaImagen = "";
    progreso = [];
    errores = 0;

    // Resetear pantalla
    document.querySelector(".espacios").textContent = "__ __ __ __ __ __ __ __ __";
    document.querySelector(".pista-box").textContent = "Aquí va la pista o descripción del elemento a adivinar.";

    // Resetear botones de letras
    document.querySelectorAll(".letra").forEach(btn => {
        btn.disabled = false;
        btn.style.backgroundColor = "";
        btn.style.color = "";
    });

    // Resetear corazones
    document.querySelectorAll(".corazon").forEach(c => c.style.visibility = "visible");

    // Ocultar imagen de ganador
    document.querySelector("#imagen-ganador img").src = "";
    document.getElementById("imagen-ganador").style.display = "none";

    actualizarCronometro(); // Actualizar cronómetro
}

// Pausar juego
function pausarJuego() {
    clearInterval(intervalo); // Detener cronómetro
    juegoIniciado = false; // Cambiar estado
}

// Mostrar progreso de palabra
function mostrarProgreso() {
    document.querySelector(".espacios").textContent = progreso.join(" "); // Letras y guiones
}

// Verificar letra seleccionada
function verificarLetra(letra, boton) {
    if (!juegoIniciado) { // Si el juego no ha iniciado
        alert("Primero debes iniciar el juego con el botón INICIO.");
        return;
    }

    boton.disabled = true; // Desactivar botón
    let acierto = false;

    // Revisar si la letra está en la palabra secreta
    for (let i = 0; i < palabraSecreta.length; i++) {
        if (palabraSecreta[i] === letra) {
            progreso[i] = letra; // Actualizar progreso
            acierto = true;
        }
    }

    mostrarProgreso();

    if (acierto) { // Si acertó
        boton.style.backgroundColor = "green";
        boton.style.color = "white";
    } else { // Si falló
        boton.style.backgroundColor = "red";
        boton.style.color = "white";
        errores++; // Aumentar errores

        const corazones = document.querySelectorAll(".corazon");
        if (errores <= erroresMax) {
            corazones[errores - 1].style.visibility = "hidden"; // Ocultar corazón
        }

        if (errores >= erroresMax) { // Perder juego
            clearInterval(intervalo);
            document.querySelector(".espacios").textContent = palabraSecreta.split("").join(" ");
            alert("¡Perdiste!, la palabra oculta es: " + palabraSecreta);
        }
    }

    if (!progreso.includes("_")) { // Ganar juego
        clearInterval(intervalo);
        alert("¡Ganaste! La palabra oculta es: " + palabraSecreta);

        // Mostrar imagen de ganador
        const imgElement = document.querySelector("#imagen-ganador img");
        imgElement.src = palabraSecretaImagen;
        document.getElementById("imagen-ganador").style.display = "block";
    }
}

// Eventos al cargar la página
document.addEventListener("DOMContentLoaded", async () => {
    await cargarPalabras(); // Cargar palabras desde BD

    // Asignar evento a cada botón de letra
    document.querySelectorAll(".letra").forEach(btn => {
        btn.addEventListener("click", (e) => {
            const letra = e.target.textContent;
            verificarLetra(letra, btn);
        });
    });

    actualizarCronometro(); // Mostrar cronómetro al inicio
});
