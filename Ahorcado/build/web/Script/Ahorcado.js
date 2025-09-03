// Declaracion de Variables
let tiempoRestante = 300; //tiempo en seg
let intervalo = null;
let juegoIniciado = false;

// Arreglo de objetos que incluye la palabra y su respectiva pista
const palabras = [
    {palabra: "PROGRAMAR", pista: "Acción de crear instrucciones para una computadora."},
    {palabra: "ESTUDIAR", pista: "Ejercitar el entendimiento para alcanzar o comprender algo."},
    {palabra: "AHORCADO", pista: "Nombre del juego que estás jugando ahora."},
    {palabra: "COMPUTADORA", pista: "Máquina que procesa información."},
    {palabra: "PROFESORES", pista: "Persona que ejerce o enseña una ciencia o arte."}
];

// Variables para el estado del juego
let palabraSecreta = "";
let pistaActual = "";
let progreso = [];
let errores = 0;
const erroresMax = 6;

// Mostrar cronómetro
function actualizarCronometro() {
    //Convierte los minutos a segundos
    const minutos = Math.floor(tiempoRestante / 60); 
    const segundos = tiempoRestante % 60;
    //padStart: Asegura que siempre tenga 2 digitos
    //Actualiza el contenido del elemento
    document.querySelector(".cronometro").textContent =
            `${String(minutos).padStart(2, "0")}:${String(segundos).padStart(2, "0")}`;
}

// Iniciar juego
function iniciarJuego() {
    //Evita reiniciar el juego si ya esta en curso
    if (!juegoIniciado) {
        juegoIniciado = true;

        // Si aún no hay palabra seleccionada, selecciona una al azar
        if (!palabraSecreta) {
            const seleccion = palabras[Math.floor(Math.random() * palabras.length)];
            //Asigna la palabra
            palabraSecreta = seleccion.palabra;
            pistaActual = seleccion.pista;
            //imagen una pendiente
            palabraSecretaImagen = seleccion.imagen;
            
            //Muestra la pista de la palabra
            document.querySelector(".pista-box").textContent = pistaActual;

            //Inicializa el arreglo de progeso con _ usando la longitud de la palabra
            progreso = Array(palabraSecreta.length).fill("_");
            //Metodo mostrarProgeso()
            mostrarProgreso();
            
            //Activa todos los botones de letras y reinicia su estilo css
            document.querySelectorAll(".letra").forEach(btn => {
                btn.disabled = false;
                btn.style.backgroundColor = "";
                btn.style.color = "";
            });
            
            //Muestra los corazones de vida
            document.querySelectorAll(".corazon").forEach(c => c.style.visibility = "visible");
            document.getElementById("imagen-ganador").style.display = "none";
        }

        // Reinicia el cronómetro si estaba pausado
        intervalo = setInterval(() => {
            if (tiempoRestante > 0) {
                tiempoRestante--;
                actualizarCronometro();
            } else {
                //Cuando se acaba el tiempo, detiene el juego y muestra la alerta
                clearInterval(intervalo);
                alert("¡Se acabó el tiempo!, la palabra oculta es: " + palabraSecreta);
                reiniciarJuego();
            }
        }, 1000);
    }
}


// Reiniciar juego
//Resetea todas las variables a su estado inicial y para el tiempo
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

    // Reiniciar botones y corazones
    document.querySelectorAll(".letra").forEach(btn => {
        btn.disabled = false;
        btn.style.backgroundColor = "";
        btn.style.color = "";
    });

    document.querySelectorAll(".corazon").forEach(c => {
        c.style.visibility = "visible";
    });

    actualizarCronometro();
}

// Pausar
function pausarJuego() {
    //Detiene el cronometro y cambia su estado a no iniciado
    clearInterval(intervalo);
    juegoIniciado = false;
}

// Mostrar progreso
function mostrarProgreso() {
    //Muestra las letras adivinadas y los guiones 
    document.querySelector(".espacios").textContent = progreso.join(" ");
}

// Verificar letra
function verificarLetra(letra, boton) {
    //Verifica si el juego esta activo antes de procesar la letra
    if (!juegoIniciado) {
        alert("Primero debes iniciar el juego con el botón INICIO.");
        return;
    }
    
    boton.disabled = true; // Bloquear la letra una vez usada
    
    //Recorre la palabra secreta y actualiza el progreso
    let acierto = false;
    for (let i = 0; i < palabraSecreta.length; i++) {
        if (palabraSecreta[i] === letra) {
            progreso[i] = letra;
            acierto = true;
        }
    }

    mostrarProgreso(); //Actualiza visualmente el progreso
    
    //Cambia el color en base al resultado 
    if (acierto) {
        boton.style.backgroundColor = "green";
        boton.style.color = "white";
    } else {
        boton.style.backgroundColor = "red";
        boton.style.color = "white";
        errores++; //si no es correcta aunmenta 1 error

        // Ocultar un corazón
        const corazones = document.querySelectorAll(".corazon");
        if (errores <= erroresMax) {
            corazones[errores - 1].style.visibility = "hidden"; // oculta el corazón correspondiente
        }
        
        //Si se exceden los errores, se termina el juego y muestra la palabra secreta
        if (errores >= erroresMax) {
            clearInterval(intervalo);
            document.querySelector(".espacios").textContent = palabraSecreta.split("").join(" ");
            alert("¡Perdiste!, la palabra oculta es: " + palabraSecreta);
        }
    }

    // Verificar si ganó
    //Si ya no hay _ el jugador gano
    if (!progreso.includes("_")) {
        clearInterval(intervalo);
        alert("¡Ganaste! La palabra oculta es: " + palabraSecreta);
    }
}


// Eventos
document.addEventListener("DOMContentLoaded", () => { //DOMContentLoaded ocurre cuando el navegador ya cargo todo
    //Selecciona todas las letras y les asigna el evento onclick para verficar la letra
    document.querySelectorAll(".letra").forEach(btn => {
        btn.addEventListener("click", (e) => {
            const letra = e.target.textContent;
            verificarLetra(letra, btn);
        });
    });
    //Inicializa el cronometro en la pantalla
    actualizarCronometro();
});
