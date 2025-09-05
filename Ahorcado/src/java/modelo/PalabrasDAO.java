package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PalabrasDAO {

    private Connection connection;

    public PalabrasDAO(Connection connection) {
        this.connection = connection;
    }

    // Obtener todas las palabras
    public List<Palabras> obtenerTodasLasPalabras() throws SQLException {
        List<Palabras> palabras = new ArrayList<>();
        String sql = "call sp_ListarPalabra();";

        try (PreparedStatement stmt = connection.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Palabras palabra = new Palabras(
                        rs.getInt("codigoPalabra"),
                        rs.getString("palabra"),
                        rs.getString("pista")
                );
                palabras.add(palabra);
            }
        }
        return palabras;
    }
}