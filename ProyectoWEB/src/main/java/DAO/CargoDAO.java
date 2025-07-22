package DAO;

import Conexion.Conexion;
import Logica.Cargo;
import Logica.Permiso;
import Logica.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CargoDAO {

    public List<Cargo> listarCargos() {
        List<Cargo> listarCargos = new ArrayList<>();

        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "SELECT * FROM viewListarCargos";
                try (PreparedStatement ps = cn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        Cargo cargo = new Cargo();

                        cargo.setIdCargo(rs.getInt("IDCARGO"));
                        cargo.setNombreCargo(rs.getString("NOMBRECARGO"));
                        cargo.setEstado(rs.getString("ESTADO").charAt(0));

                        listarCargos.add(cargo);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar los cargos: " + e.getMessage());
        }

        return listarCargos;
    }

    public void registrarCargo(Cargo car) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "{CALL paRegistrarCargo (?, ?)}";
                try (CallableStatement cs = cn.prepareCall(query)) {
                    cs.setString(1, car.getNombreCargo());
                    cs.registerOutParameter(2, java.sql.Types.INTEGER);
                    cs.executeUpdate();
                    int idCargo = cs.getInt(2);

                    List<Permiso> permisos = car.getPermisos();
                    if (permisos != null && !permisos.isEmpty()) {
                        String sqlPermiso = "{CALL paRegistrarCargoPermiso (?, ?)}";
                        try (CallableStatement ps = cn.prepareCall(sqlPermiso)) {
                            for (Permiso permiso : permisos) {
                                ps.setInt(1, idCargo);
                                ps.setInt(2, permiso.getIdPermiso());
                                ps.addBatch();
                            }
                            ps.executeBatch();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al registrar usuario: " + e);
        }
    }

    public List<Cargo> listarCargosActivos() {
        List<Cargo> listarCargosActivos = new ArrayList<>();
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "SELECT * FROM viewListarCargosActivos";
                try (PreparedStatement ps = cn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Cargo cargo = new Cargo();

                        cargo.setIdCargo(rs.getInt("IDCARGO"));
                        cargo.setNombreCargo(rs.getString("NOMBRECARGO"));
                        cargo.setEstado(rs.getString("ESTADO").charAt(0));

                        listarCargosActivos.add(cargo);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar los cargos: " + e.getMessage());
        }
        return listarCargosActivos;
    }

    public List<Cargo> listarCargosEliminados() {
        List<Cargo> listarCargosElminados = new ArrayList<>();

        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "SELECT * FROM viewListarCargosEliminados";
                try (PreparedStatement ps = cn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        Cargo cargo = new Cargo();
                        cargo.setIdCargo(rs.getInt("IDCARGO"));
                        cargo.setNombreCargo(rs.getString("NOMBRECARGO"));
                        cargo.setEstado(rs.getString("ESTADO").charAt(0));
                        listarCargosElminados.add(cargo);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar los cargos: " + e.getMessage());
        }

        return listarCargosElminados;
    }

    public Cargo leerCargoID(Cargo car) {
        Cargo cargo = null;
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "{CALL paLeerCargoID (?)}";
                try (CallableStatement cs = cn.prepareCall(query)) {
                    cs.setInt(1, car.getIdCargo());
                    try (ResultSet rs = cs.executeQuery()) {
                        if (rs.next()) {
                            cargo = new Cargo();
                            cargo.setIdCargo(rs.getInt("IDCARGO"));
                            cargo.setNombreCargo(rs.getString("NOMBRECARGO"));
                            cargo.setEstado(rs.getString("ESTADO").charAt(0));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al leer el cargo: " + e.getMessage());
        }
        return cargo;
    }

    public void actualizarCargo(Cargo cargo) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "{CALL paActualizarCargo (?, ?, ?)}";
                try (CallableStatement cs = cn.prepareCall(query)) {
                    cs.setInt(1, cargo.getIdCargo());
                    cs.setString(2, cargo.getNombreCargo());
                    cs.setString(3, String.valueOf(cargo.getEstado()));
                    cs.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar el cargo: " + e.getMessage());
        }
    }

    public void eliminarCargo(Cargo cargo) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "{CALL paEliminarCargo (?)}";
                try (CallableStatement cs = cn.prepareCall(query)) {
                    cs.setInt(1, cargo.getIdCargo());
                    cs.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar cargo: " + e.getMessage());
        }
    }

}
