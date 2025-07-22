package DAO;

import Conexion.Conexion;
import Logica.Cargo;
import Logica.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

   public Usuario validarLogin(Usuario usu) {
    Usuario user = null;
    try (Connection cn = Conexion.getConexion()) {
        if (cn != null) {
            String query = "EXEC paValidarLogin ?, ?;";
            try (PreparedStatement ps = cn.prepareStatement(query)) {
                ps.setString(1, usu.getNombreUsuario());
                ps.setString(2, usu.getContra());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int idUsuario = rs.getInt("IDUSUARIO");
                        String nombreCargo = rs.getString("NOMBRECARGO");
                        int idCargo = rs.getInt("IDCARGO");
                        if (idUsuario != 0 && idCargo != 0 && nombreCargo != null) {
                            user = new Usuario();
                            user.setIdUsuario(idUsuario);
                            user.setNombreUsuario(usu.getNombreUsuario());
                            Cargo cargo = new Cargo();
                            cargo.setIdCargo(idCargo);
                            cargo.setNombreCargo(nombreCargo);
                            user.setIdCargo(cargo);
                            user.setEstado('1');
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al validar login: " + e);
        }
        return user;
    }


    public List<Usuario> listarUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "SELECT * FROM viewListarUsuario";
                try (PreparedStatement ps = cn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(rs.getInt("IDUSUARIO"));
                        usuario.setNombreUsuario(rs.getString("NOMBREUSUARIO"));
                        usuario.setEstado(rs.getString("ESTADO").charAt(0)); 
                        Cargo cargo = new Cargo();
                        cargo.setNombreCargo(rs.getString("NOMBRECARGO"));
                        usuario.setIdCargo(cargo);
                        listaUsuarios.add(usuario);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar los usuarios: " + e.getMessage());
        }

        return listaUsuarios;
    }

    public void registrarUsuarios(Usuario usu) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "EXEC paRegistrarUsuario ?, ?, ?;";
                try (PreparedStatement ps = cn.prepareStatement(query)) {
                    ps.setString(1, usu.getNombreUsuario());
                    ps.setString(2, usu.getContra());
                    ps.setInt(3, usu.getIdCargo().getIdCargo());
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al registrar usuario: " + e);
        }
    }

    public Usuario leerUsuario(Usuario usu) {
        Usuario usuario = null;
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "EXEC paLeerUsuarioID ?;";
                try (PreparedStatement ps = cn.prepareStatement(query)) {
                    ps.setInt(1, usu.getIdUsuario());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            usuario = new Usuario();
                            usuario.setIdUsuario(rs.getInt("IDUSUARIO"));
                            usuario.setNombreUsuario(rs.getString("NOMBREUSUARIO"));
                            usuario.setContra(rs.getString("CLAVE"));
                            usuario.setEstado(rs.getString("ESTADO").charAt(0)); 
                            Cargo cargo = new Cargo();
                            cargo.setNombreCargo(rs.getString("NOMBRECARGO"));
                            usuario.setIdCargo(cargo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al leer al usuario: " + e.getMessage());
        }

        return usuario;
    }

    public void actualizarUsuario(Usuario usuario) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "EXEC paActualizarUsuario ?, ?, ?, ?, ?;";
                try (PreparedStatement ps = cn.prepareStatement(query)) {
                    ps.setInt(1, usuario.getIdUsuario());
                    ps.setString(2, usuario.getNombreUsuario());
                    ps.setString(3, usuario.getContra());
                    ps.setString(4, String.valueOf(usuario.getEstado())); 
                    ps.setInt(5, usuario.getIdCargo().getIdCargo());
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar el usuario: " + e.getMessage());
        }
    }
    
    public void eliminarUsuario(Usuario usuario){
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "EXEC paEliminarUsuario ?;";
                try (PreparedStatement ps = cn.prepareStatement(query)) {
                    ps.setInt(1, usuario.getIdUsuario());
                    ps.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("Error al eliminar el usuario:"+ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

}
