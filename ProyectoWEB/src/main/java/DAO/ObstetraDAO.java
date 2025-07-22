package DAO;

import Conexion.Conexion;
import Logica.Obstetra;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ObstetraDAO {

    public List<Obstetra> listarObstetras() {
        List<Obstetra> listaObstetras = new ArrayList<>();
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "SELECT * FROM viewListarObstetras";
                try (PreparedStatement ps = cn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Obstetra obstetra = new Obstetra();
                        obstetra.setIdObstetra(rs.getInt("idObstetra"));
                        obstetra.setNombreObstetra(rs.getString("nombreObstetra"));
                        obstetra.setEstado(rs.getString("estado").charAt(0));
                        listaObstetras.add(obstetra);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar los obstetras: " + e.getMessage());
        }
        return listaObstetras;
    }

    public void registrarObstetra(Obstetra obstetra) {
        String sql = "INSERT INTO Obstetra (nombreObstetra) VALUES (?)";

        try (Connection cn = Conexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, obstetra.getNombreObstetra());
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al registrar obstetra: " + e.getMessage());
        }
    }

    public Obstetra leerObstetra(Obstetra obstetra) {
        Obstetra ob = null;
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "SELECT idObstetra, nombreObstetra, estado FROM Obstetra WHERE idObstetra = ?";
                try (PreparedStatement ps = cn.prepareStatement(query)) {
                    ps.setInt(1, obstetra.getIdObstetra());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            ob = new Obstetra();
                            ob.setIdObstetra(rs.getInt("idObstetra"));
                            ob.setNombreObstetra(rs.getString("nombreObstetra"));
                            ob.setEstado(rs.getString("estado").charAt(0));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al leer el obstetra: " + e.getMessage());
        }
        return ob;
    }

    public void eliminarObstetra(int idObstetra) {
        String sql = "UPDATE Obstetra SET estado = '2' WHERE idObstetra = ?";
        try (Connection cn = Conexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idObstetra);
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al eliminar obstetra: " + e.getMessage());
        }
    }

    public void actualizarObstetra(Obstetra obstetra) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "UPDATE Obstetra SET nombreObstetra = ?, estado = ? WHERE idObstetra = ?";
                try (PreparedStatement ps = cn.prepareStatement(query)) {
                    ps.setString(1, obstetra.getNombreObstetra());
                    ps.setString(2, String.valueOf(obstetra.getEstado()));
                    ps.setInt(3, obstetra.getIdObstetra());
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar obstetra: " + e.getMessage());
        }
    }

    public List<Obstetra> listarObstetrasActivos() {
        List<Obstetra> lista = new ArrayList<>();
        String query = "SELECT * FROM viewListarObstetrasActivos";

        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                try (PreparedStatement ps = cn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        Obstetra o = new Obstetra();
                        o.setIdObstetra(rs.getInt("idObstetra"));
                        o.setNombreObstetra(rs.getString("nombreObstetra"));
                        o.setEstado(rs.getString("estado").charAt(0));
                        lista.add(o);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar los obstetras activos: " + e.getMessage());
        }
        return lista;
    }
}
