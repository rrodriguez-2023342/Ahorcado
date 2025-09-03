package modelo;

public class Usuarios {
   private int codigoUsuario;
   private String contraseña;
   private String correoUsuario;

    public Usuarios() {
    }

    public Usuarios(int codigoUsuario, String contraseña, String correoUsuario) {
        this.codigoUsuario = codigoUsuario;
        this.contraseña = contraseña;
        this.correoUsuario = correoUsuario;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }
   
   
}
