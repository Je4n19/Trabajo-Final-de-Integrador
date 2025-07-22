package DAO;

import Conexion.Conexion;
import Logica.Categoria;
import Logica.Producto;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listarProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "SELECT * FROM viewListarProductos";
                try (PreparedStatement ps = cn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Producto producto = new Producto();
                        producto.setIdProducto(rs.getInt("IDPRODUCTO"));
                        producto.setNombreProducto(rs.getString("NOMBREPRODUCTO"));
                        producto.setDescripcion(rs.getString("DESCRIPCION"));
                        producto.setPrecio(rs.getDouble("PRECIO"));
                        producto.setStock(rs.getInt("STOCK"));
                        producto.setEstado(rs.getString("ESTADO").charAt(0));
                        Categoria categoria = new Categoria();
                        categoria.setNombreCategoria(rs.getString("NOMBRECATEGORIA"));
                        producto.setIdCategoria(categoria);
                        listaProductos.add(producto);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar los productos: " + e.getMessage());
        }
        return listaProductos;
    }

    public void registrarProducto(Producto producto) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "{CALL paRegistrarProducto (?, ?, ?, ?, ?)}";
                try (CallableStatement cs = cn.prepareCall(query)) {
                    cs.setString(1, producto.getNombreProducto());   
                    cs.setString(2, producto.getDescripcion());      
                    cs.setDouble(3, producto.getPrecio());
                    cs.setInt(4, producto.getStock());
                    cs.setInt(5, producto.getIdCategoria().getIdCategoria());
                    cs.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al registrar producto: " + e);
        }
    }

    public Producto leerProducto(Producto produc) {
        Producto producto = null;
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "{CALL paLeerProductoID (?)}";
                try (PreparedStatement ps = cn.prepareStatement(query)) {
                    ps.setInt(1, produc.getIdProducto());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            producto = new Producto();
                            producto.setIdProducto(rs.getInt("IDPRODUCTO"));
                            producto.setNombreProducto(rs.getString("NOMBREPRODUCTO"));
                            producto.setDescripcion(rs.getString("DESCRIPCION"));
                            producto.setPrecio(rs.getDouble("PRECIO"));
                            producto.setStock(rs.getInt("STOCK"));
                            Categoria categoria = new Categoria();
                            categoria.setNombreCategoria(rs.getString("NOMBRECATEGORIA"));
                            producto.setIdCategoria(categoria);
                            producto.setEstado(rs.getString("ESTADO").charAt(0));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al leer al la categoria: " + e.getMessage());
        }
        return producto;
    }

    public void actualizarProducto(Producto producto) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "{CALL paActualizarProducto (?, ?, ?, ?, ?, ?)}";
                try (CallableStatement cs = cn.prepareCall(query)) {
                    cs.setInt(1, producto.getIdProducto());
                    cs.setString(1, producto.getNombreProducto());
                    cs.setString(2, producto.getDescripcion());
                    cs.setDouble(4, producto.getPrecio());
                    cs.setInt(5, producto.getStock());
                    cs.setInt(6, producto.getIdCategoria().getIdCategoria());
                    cs.setString(7, String.valueOf(producto.getEstado()));
                    cs.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar el producto: " + e.getMessage());
        }
    }

    public void eliminarProducto(Producto producto) {
        try (Connection cn = Conexion.getConexion()) {
            if (cn != null) {
                String query = "{CALL paEliminarProducto (?)}";
                try (CallableStatement cs = cn.prepareCall(query)) {
                    cs.setInt(1, producto.getIdProducto());
                    cs.executeUpdate();
                } catch (Exception ex) {
                    System.out.println("Error al eliminar el producto:" + ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

}
