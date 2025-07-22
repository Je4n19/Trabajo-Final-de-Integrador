package DAO;

import Conexion.Conexion;
import Logica.Cargo;
import Logica.Permiso;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PermisoDAO {

    public List<Permiso> listarPermisos() {
        List<Permiso> listaPermisos = new ArrayList<>();
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "SELECT * FROM viewListarPermiso";
                try (PreparedStatement ps = cn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Permiso permiso = new Permiso();
                        permiso.setIdPermiso(rs.getInt("IDPERMISO"));
                        permiso.setNombrePermiso(rs.getString("NOMBREPERMISO"));
                        listaPermisos.add(permiso);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar los permisos: " + e.getMessage());
        }
        return listaPermisos;
    }

    public void registrarPermisoCargo(int idCargo, List<Integer> permisos) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "{CALL paRegistrarCargoPermiso (?,  ?)}";
                for (Integer idPermiso : permisos) {
                    try (CallableStatement cs = cn.prepareCall(query)) {
                        cs.setInt(1, idCargo);
                        cs.setInt(2, idPermiso);
                        cs.executeUpdate();
                    } catch (Exception e) {
                        System.err.println("Error al registrar usuario: " + e);
                    }
                }
            } else {
                System.err.println("No se pudo establecer conexion con la base de datos");
            }
        } catch (Exception e) {
            System.err.println("Error a conectar la base de datos " + e.getMessage());
        }
    }

    public Cargo leerPermisosPorCargo(int idCargo) {
        Cargo cargo = null;
        String pa = "{CALL paLeerPermisosPorCargo(?)}";

        try (Connection cn = Conexion.getConexion(); CallableStatement cs = cn.prepareCall(pa)) {

            cs.setInt(1, idCargo);

            try (ResultSet rs = cs.executeQuery()) {
                cargo = new Cargo();
                cargo.setIdCargo(idCargo);
                List<Permiso> permisos = new ArrayList<>();

                while (rs.next()) {
                    if (cargo.getNombreCargo() == null) {
                        cargo.setNombreCargo(rs.getString("NOMBRECARGO"));
                    }
                    String nombrePermiso = rs.getString("NOMBREPERMISO");
                    if (nombrePermiso != null) {
                        permisos.add(new Permiso(nombrePermiso));
                    }
                }
                cargo.setPermisos(permisos);
            }

        } catch (Exception e) {
            System.err.println("Error al leer permisos por cargo: " + e.getMessage());
        }

        return cargo;
    }

    public List<Integer> obtenerPermisosCargo(int idCargo) {
        List<Integer> permisos = new ArrayList<>();
        String sql = "{CALL paLeerIdsPermisosPorCargo(?)}";
        try (Connection cn = Conexion.getConexion(); CallableStatement cs = cn.prepareCall(sql)) {
            cs.setInt(1, idCargo);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    int idPermiso = rs.getInt("IDPERMISO");
                    permisos.add(idPermiso);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener IDs de permisos por idCargo: " + e.getMessage());
        }

        return permisos;
    }

    public void actualizarPermisoCargo(int idCargo, List<Integer> permisosSeleccionados) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String paBorrar = "{CALL paQuitarPermisosPorCargo(?)}";
                try (CallableStatement csBorrar = cn.prepareCall(paBorrar)) {
                    csBorrar.setInt(1, idCargo);
                    csBorrar.executeUpdate();
                }
                String paInsertar = "{CALL paRegistrarCargoPermiso(?, ?)}";
                for (int idPermiso : permisosSeleccionados) {
                    try (CallableStatement csInsert = cn.prepareCall(paInsertar)) {
                        csInsert.setInt(1, idCargo);
                        csInsert.setInt(2, idPermiso);
                        csInsert.executeUpdate();
                    }
                }
            } else {
                System.err.println("No se pudo establecer conexión con la base de datos");
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar permisos del cargo: " + e.getMessage());
        }
    }

    public void actualizarPermisoCargoEliminado(int idCargo, List<Integer> permisosSeleccionados) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String paBorrar = "{CALL paQuitarPermisosPorCargo(?)}";
                try (CallableStatement csBorrar = cn.prepareCall(paBorrar)) {
                    csBorrar.setInt(1, idCargo);
                    csBorrar.executeUpdate();
                }
                String paInsertar = "{CALL paRegistrarPermisoCargoEliminado(?, ?)}";
                for (int idPermiso : permisosSeleccionados) {
                    try (CallableStatement csInsert = cn.prepareCall(paInsertar)) {
                        csInsert.setInt(1, idCargo);
                        csInsert.setInt(2, idPermiso);
                        csInsert.executeUpdate();
                    }
                }
            } else {
                System.err.println("No se pudo establecer conexión con la base de datos");
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar permisos del cargo: " + e.getMessage());
        }
    }
}
