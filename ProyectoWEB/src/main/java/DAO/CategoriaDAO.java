package DAO;

import Conexion.Conexion;
import Logica.Categoria;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> listarCategorias() {
        List<Categoria> listarCategorias = new ArrayList<>();
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "SELECT * FROM viewListarCategorias";
                try (PreparedStatement ps = cn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Categoria categoria = new Categoria();
                        categoria.setIdCategoria(rs.getInt("IDCATEGORIA"));
                        categoria.setNombreCategoria(rs.getString("NOMBRECATEGORIA"));
                        categoria.setDescripcion(rs.getString("DESCRIPCION"));
                        categoria.setEstado(rs.getString("ESTADO").charAt(0));
                        listarCategorias.add(categoria);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar los cargos: " + e.getMessage());
        }
        return listarCategorias;
    }

    public void registrarCategoria(Categoria categoria) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "{CALL paRegistrarCategoria (?, ?)}";
                try (CallableStatement ca = cn.prepareCall(query)) {
                    ca.setString(1, categoria.getNombreCategoria());
                    ca.setString(2, categoria.getDescripcion());
                    ca.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al registrar la categoria: " + e);
        }
    }

    public Categoria leerCategoria(Categoria cate) {
        Categoria categoria = null;
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "{CALL paLeerCategoriaID (?)}";
                try (CallableStatement cs = cn.prepareCall(query)) {
                    cs.setInt(1, cate.getIdCategoria());
                    try (ResultSet rs = cs.executeQuery()) {
                        if (rs.next()) {
                            categoria = new Categoria();
                            categoria.setIdCategoria(rs.getInt("IDCATEGORIA"));
                            categoria.setNombreCategoria(rs.getString("NOMBRECATEGORIA"));
                            categoria.setDescripcion(rs.getString("DESCRIPCION"));
                            categoria.setEstado(rs.getString("ESTADO").charAt(0));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al leer al usuario: " + e.getMessage());
        }
        return categoria;
    }

    public void actualizarCategoria(Categoria categoria) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "{CALL paActualizarCategoria (?, ?, ?, ?)}";
                try (CallableStatement cs = cn.prepareCall(query)) {
                    cs.setInt(1, categoria.getIdCategoria());
                    cs.setString(2, categoria.getNombreCategoria());
                    cs.setString(3, categoria.getDescripcion());
                    cs.setString(4, String.valueOf(categoria.getEstado()));
                    cs.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar la categoria: " + e.getMessage());
        }
    }

    public void eliminarCategoria(Categoria categoria) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "{CALL paEliminarCategoria (?)}";
                try (CallableStatement cs = cn.prepareCall(query)) {
                    cs.setInt(1, categoria.getIdCategoria());
                    cs.executeUpdate();
                } catch(Exception ex){
                    System.out.println("Error al eliminar el usuario: "+ ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al conecctarse a la BD" + e.getMessage());
        }
    }

    public List<Categoria> listarCategoriasActivas() {
         List<Categoria> listarCategoriasActivas = new ArrayList<>();
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "SELECT * FROM viewListarCategoriasActivas";
                try (PreparedStatement ps = cn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Categoria categoria = new Categoria();
                        categoria.setIdCategoria(rs.getInt("IDCATEGORIA"));
                        categoria.setNombreCategoria(rs.getString("NOMBRECATEGORIA"));
                        categoria.setDescripcion(rs.getString("DESCRIPCION"));
                        categoria.setEstado(rs.getString("ESTADO").charAt(0));
                        listarCategoriasActivas.add(categoria);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar las categorias: " + e.getMessage());
        }
        return listarCategoriasActivas;
    }
}
