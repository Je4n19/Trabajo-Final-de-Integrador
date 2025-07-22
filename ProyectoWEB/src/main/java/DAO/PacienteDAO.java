package DAO;

import Conexion.Conexion;
import Logica.Obstetra;
import Logica.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public List<Paciente> listarPacientes() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM viewListarPacientes";

        try (Connection cn = Conexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setIdPaciente(rs.getInt("idPaciente"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                p.setEdad(rs.getInt("edad"));
                p.setEstado(rs.getString("estado").charAt(0));
                Obstetra o = new Obstetra();
                o.setNombreObstetra(rs.getString("nombreObstetra"));

                p.setObstetra(o);

                lista.add(p);
            }

        } catch (Exception e) {
            System.err.println("Error al listar pacientes desde la vista: " + e.getMessage());
        }

        return lista;
    }

    public void registrarPaciente(Paciente paciente) {
        String sql = "{CALL paRegistrarPaciente(?, ?, ?, ?)}";

        try (Connection cn = Conexion.getConexion(); CallableStatement cs = cn.prepareCall(sql)) {

            cs.setString(1, paciente.getNombre());
            cs.setString(2, paciente.getApellido());
            cs.setInt(3, paciente.getEdad());
            cs.setInt(4, paciente.getObstetra().getIdObstetra());

            cs.execute();

        } catch (Exception e) {
            System.err.println("Error al registrar paciente: " + e.getMessage());
        }
    }

    public Paciente leerPaciente(int idPaciente) {
        Paciente p = null;
        String sql = "SELECT p.idPaciente, p.nombre, p.apellido, p.edad, p.estado, "
                + "o.idObstetra, o.nombreObstetra FROM Paciente p "
                + "JOIN Obstetra o ON p.idObstetra = o.idObstetra WHERE p.idPaciente = ?";
        try (Connection cn = Conexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Paciente();
                p.setIdPaciente(rs.getInt("idPaciente"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                p.setEdad(rs.getInt("edad"));
                p.setEstado(rs.getString("estado").charAt(0));

                Obstetra o = new Obstetra();
                o.setIdObstetra(rs.getInt("idObstetra"));
                o.setNombreObstetra(rs.getString("nombreObstetra"));
                p.setObstetra(o);
            }

        } catch (Exception e) {
            System.err.println("Error al leer paciente: " + e.getMessage());
        }
        return p;
    }

    public void actualizarPaciente(Paciente paciente) {
        String sql = "UPDATE Paciente SET nombre = ?, apellido = ?, edad = ?, idObstetra = ?, estado = ? WHERE idPaciente = ?";
        try (Connection cn = Conexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getApellido());
            ps.setInt(3, paciente.getEdad());
            ps.setInt(4, paciente.getObstetra().getIdObstetra());
            ps.setString(5, String.valueOf(paciente.getEstado()));
            ps.setInt(6, paciente.getIdPaciente());

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al actualizar paciente: " + e.getMessage());
        }
    }

    public void eliminarPaciente(int idPaciente) {
        String sql = "UPDATE Paciente SET estado = '2' WHERE idPaciente = ?";
        try (Connection cn = Conexion.getConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idPaciente);
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al eliminar paciente: " + e.getMessage());
        }
    }
}
