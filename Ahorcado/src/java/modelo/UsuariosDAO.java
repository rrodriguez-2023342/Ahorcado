package modelo;

import Config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuariosDAO {
    // Crear un objeto para manejar la conexión a la base de datos
    Conexion cn = new Conexion();

    // Variables que se usarán para conectarse y ejecutar consultas en la base de datos
    Connection con;           // Representa la conexión con la base de datos
    PreparedStatement ps;     // Permite preparar consultas SQL con parámetros
    ResultSet rs;             // Guarda los resultados que devuelve la consulta

    // Método para validar un usuario (comprobar si existe en la base de datos)
    public Usuarios validar(String correoUsuario, String contraseñaUsuario) {
        // Crear un objeto de tipo Usuarios donde guardaremos la información del usuario encontrado
        Usuarios usuarios = new Usuarios();

        // Consulta SQL que llama a un procedimiento almacenado que verifica el usuario y contraseña
        String sql = "call sp_BuscarUsuarioLog(?, ?);"; // ? son parámetros que se reemplazan con datos reales

        try {
            // Conectar a la base de datos
            con = cn.Conexion();

            // Preparar la consulta SQL
            ps = con.prepareStatement(sql);

            // Asignar los valores de los parámetros (?) con los datos recibidos
            ps.setString(1, correoUsuario);      // Primer ? será el correo
            ps.setString(2, contraseñaUsuario);  // Segundo ? será la contraseña

            // Ejecutar la consulta y guardar el resultado en "rs"
            rs = ps.executeQuery();

            // Recorrer los resultados (aunque normalmente será solo un usuario)
            while (rs.next()) {
                // Guardar la información del usuario en el objeto "usuarios"
                usuarios.setCodigoUsuario(rs.getInt("codigoUsuario"));
                usuarios.setContraseña(rs.getString("contraseña"));
                usuarios.setCorreoUsuario(rs.getString("correoUsuario"));
            }
        } catch (Exception e) {
            // Si ocurre un error (como que no encuentre usuario), se muestra un mensaje y la traza del error
            System.out.println("El usuario o contraseña son incorrectos");
            e.printStackTrace();
        }

        // Retornar el objeto "usuarios", que estará vacío si no se encontró ningún usuario
        return usuarios;
    }

    // Método para registrar un nuevo usuario en la base de datos
    public boolean registrar(String correo, String contraseña) {
        // Consulta SQL que llama a un procedimiento almacenado para agregar un usuario
        String sql = "call sp_AgregarUsuario(?, ?);";

        try {
            // Conectar a la base de datos
            con = cn.Conexion();

            // Preparar la consulta SQL
            ps = con.prepareStatement(sql);

            // Asignar los valores de los parámetros con los datos ingresados
            ps.setString(1, correo);
            ps.setString(2, contraseña);

            // Ejecutar la actualización (INSERT) y guardar el número de filas afectadas
            int filas = ps.executeUpdate();

            // Retornar true si se insertó al menos una fila, false si no
            return filas > 0;
        } catch (Exception e) {
            // Si ocurre algún error, mostrar la traza y retornar false
            e.printStackTrace();
            return false;
        }
    }

}
