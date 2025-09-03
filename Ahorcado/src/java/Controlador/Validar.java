/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuarios;
import modelo.UsuariosDAO;

/**
 *
 * @author Roberto
 */
@WebServlet(name = "Validar", urlPatterns = {"/Validar"})
public class Validar extends HttpServlet {

    //Instancias de las clases
    UsuariosDAO usuariosDAO = new UsuariosDAO();
    Usuarios usuarios = new Usuarios();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Validar</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Validar at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        // Obtiene el valor del parámetro "accion" enviado desde un formulario HTML.
        // Esto nos dice qué acción quiere hacer el usuario: ingresar, registrar, etc.
        String accion = request.getParameter("accion");

        // Si el usuario hizo clic en un botón que dice "Ingresar"
        if ("Ingresar".equalsIgnoreCase(accion)) {
            // Obtiene el correo y la contraseña ingresados por el usuario en el formulario
            String email = request.getParameter("txtCorreo");
            String pass = request.getParameter("txtPassword");

            // Llama al método "validar" del DAO de usuarios para verificar si el usuario existe y la contraseña es correcta
            usuarios = usuariosDAO.validar(email, pass);

            // Si el correo del usuario existe (es decir, encontró un usuario válido)
            if (usuarios.getCorreoUsuario() != null) {
                // Crea una sesión para el usuario (permite mantenerlo "logueado")
                HttpSession sesion = request.getSession();
                // Guarda el objeto usuario en la sesión para usarlo después
                sesion.setAttribute("usuario", usuarios);
                // Redirige al usuario al menú del juego Ahorcado
                request.getRequestDispatcher("Controlador?menu=Ahorcado").forward(request, response);
            } else {
                // Si el usuario no existe o la contraseña es incorrecta, muestra un mensaje de error
                request.setAttribute("errorLogin", "Correo o contraseña incorrectos");
                // Redirige de vuelta al formulario de inicio de sesión
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        // Si el usuario hizo clic en un botón que dice "Registrar"
        } else if ("Registrar".equalsIgnoreCase(accion)) {
            // Obtiene los datos ingresados en el formulario de registro
            String emailR = request.getParameter("txtUsuarioR");
            String passR = request.getParameter("txtPasswordR");
            String confirmar = request.getParameter("confirmar");

            // Verifica si las contraseñas ingresadas coinciden
            if (!passR.equals(confirmar)) {
                // Si no coinciden, muestra un mensaje de error
                request.setAttribute("errorRegistro", "Las contraseñas no coinciden");
                // Redirige de vuelta al formulario de registro
                request.getRequestDispatcher("index.jsp").forward(request, response);
                // Sale del método para que no siga ejecutando el código
                return;
            }

            // Llama al método "registrar" del DAO para guardar el nuevo usuario en la base de datos
            boolean registrado = usuariosDAO.registrar(emailR, passR);

            // Si se registró correctamente
            if (registrado) {
                // Muestra un mensaje de éxito
                request.setAttribute("mensajeExito", "Usuario registrado con éxito, ahora puedes iniciar sesión");
            } else {
                // Si hubo un error al registrar, muestra un mensaje de error
                request.setAttribute("errorRegistro", "Error al registrar el usuario");
            }
            // Redirige de vuelta al formulario principal
            request.getRequestDispatcher("index.jsp").forward(request, response);

        // Si no se seleccionó ninguna acción válida
        } else {
            // Redirige de vuelta al formulario principal por defecto
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
