/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Config.Conexion;
import modelo.Palabras;
import modelo.PalabrasDAO;

/**
 *
 * @author Roberto
 */
@WebServlet(name = "MostrarPalabras", urlPatterns = {"/MostrarPalabras"})
public class MostrarPalabras extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // Este método se ejecuta automáticamente cuando el navegador hace una solicitud GET (como abrir una página o hacer clic en un enlace)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtiene el valor del parámetro "action" enviado desde la URL o el formulario
        // Esto sirve para saber qué acción quiere realizar el usuario
        String action = request.getParameter("action");

        // Verifica si la acción recibida es "obtenerPalabras"
        if ("obtenerPalabras".equals(action)) {
            // Llama al método obtenerPalabras() para manejar la solicitud y devolver palabras para el juego
            mostrarPalabras(request, response);
        } else {
            // Si la acción no es "obtenerPalabras", simplemente redirige al usuario a la página principal del juego
            request.getRequestDispatcher("/ahorcado.jsp").forward(request, response);
        }
    }

    // Método privado que se encarga de obtener todas las palabras de la base de datos y enviarlas como JSON al navegador
    private void mostrarPalabras(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Crear un objeto para conectarse a la base de datos
            Conexion conn = new Conexion();
            Connection connection = conn.Conexion(); // Abrir la conexión

            // Crear un objeto DAO para manejar las operaciones relacionadas con la tabla Palabras
            PalabrasDAO dao = new PalabrasDAO(connection);

            // Obtener todas las palabras de la base de datos
            List<Palabras> palabras = dao.obtenerTodasLasPalabras();

            // Construir manualmente un JSON que se enviará al navegador
            StringBuilder json = new StringBuilder();
            json.append("["); // Abrir el array JSON

            // Recorrer todas las palabras obtenidas de la base de datos
            for (int i = 0; i < palabras.size(); i++) {
                Palabras palabra = palabras.get(i); // Tomar la palabra actual
                json.append("{"); // Abrir objeto JSON para esta palabra

                // Agregar las propiedades de la palabra en formato JSON
                json.append("\"codigoPalabra\":").append(palabra.getCodigoPalabra()).append(",");
                json.append("\"palabra\":\"").append(palabra.getPalabra()).append("\",");
                json.append("\"pista\":\"").append(palabra.getPista()).append("\"");

                json.append("}"); // Cerrar objeto JSON de esta palabra

                // Si no es la última palabra, agregar una coma para separar los objetos JSON
                if (i < palabras.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]"); // Cerrar el array JSON

            // Configurar la respuesta HTTP como JSON y con codificación UTF-8
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Escribir el JSON generado en la respuesta para que el navegador lo reciba
            response.getWriter().write(json.toString());

        } catch (SQLException e) {
            // Si ocurre un error con la base de datos, devolver un código 500 y un mensaje de error en JSON
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error al obtener las palabras\"}");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servelet para el ahorcado";
    }// </editor-fold>

}
