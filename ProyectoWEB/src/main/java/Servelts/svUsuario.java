package Servelts;

import DAO.CargoDAO;
import DAO.PermisoDAO;
import DAO.UsuarioDAO;
import Logica.Cargo;
import Logica.Usuario;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "svUsuario", urlPatterns = {"/svUsuario"})
public class svUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "verificar":
                        verificar(request, response);
                        break;
                    case "cerrar":
                        cerrarSesion(request, response);
                        break;
                    case "listarUsuarios":
                        listarUsuarios(request, response);
                        break;
                    case "registrar":
                        registrarUsuario(request, response);
                        break;
                    case "leerUsuario":
                        presentarUsuario(request, response);
                        break;
                    case "actualizarUsuario":
                        actualizarUsuario(request, response);
                        break;
                    case "eliminarUsuario":
                        eliminarUsuario(request, response);
                        break;
                    default:
                        response.sendRedirect("login.jsp");
                }
            } else {
                response.sendRedirect("login.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void verificar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession sesion;
        UsuarioDAO usuDAO = new UsuarioDAO();
        Usuario usuario = this.obtenerUsuario(request);
        usuario = usuDAO.validarLogin(usuario);
        if (usuario != null && usuario.getIdCargo() != null) {
            sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);
            PermisoDAO permisoDAO = new PermisoDAO();
            List<Integer> permisos = permisoDAO.obtenerPermisosCargo(usuario.getIdCargo().getIdCargo());
            sesion.setAttribute("permisos", permisos);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        } else {
            request.setAttribute("msje", "Credenciales Incorrectas");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession sesion = request.getSession();
        sesion.invalidate();
        response.sendRedirect("login.jsp");
    }

    private Usuario obtenerUsuario(HttpServletRequest request) {
        Usuario user = new Usuario();
        user.setNombreUsuario(request.getParameter("txtUsuario"));
        user.setContra(request.getParameter("txtContra"));
        return user;
    }

   

    private void cargarCargosActivos(HttpServletRequest request) {
        CargoDAO daoCargo = new CargoDAO();
        List<Cargo> cargos = null;
        try {
            cargos = daoCargo.listarCargosActivos();
            request.setAttribute("cargos", cargos);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo mostrar los cargos activos: " + e.getMessage());
        }
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO usuDAO = new UsuarioDAO();
        List<Usuario> usuarios = null;
        try {
            usuarios = usuDAO.listarUsuarios();
            request.setAttribute("usuarios", usuarios);
            cargarCargosActivos(request);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los usuarios" + e.getMessage());
        } finally {
            usuDAO = null;
        }
        try {
            this.getServletConfig().getServletContext()
                    .getRequestDispatcher("/vistaUsuario/usuarios.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se pudo realizar la petición" + ex.getMessage());
        }
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDAO daoUsuario = new UsuarioDAO();
        String nombre = request.getParameter("txtNombre");
        String clave = request.getParameter("txtClave");
        String idCargoStr = request.getParameter("cboCargo");
        if (nombre != null && !nombre.trim().isEmpty()
                && clave != null && !clave.trim().isEmpty()
                && idCargoStr != null && !idCargoStr.trim().isEmpty()) {
            Usuario usu = new Usuario();
            usu.setNombreUsuario(nombre);
            usu.setContra(clave);
            Cargo cargo = new Cargo();
            try {
                cargo.setIdCargo(Integer.parseInt(idCargoStr));
            } catch (NumberFormatException e) {
                request.setAttribute("msje", "Seleccione un cargo válido.");
                cargarCargosActivos(request);
                request.getRequestDispatcher("/vistaUsuario/usuarios.jsp").forward(request, response);
                return;
            }
            usu.setIdCargo(cargo);
            try {
                daoUsuario.registrarUsuarios(usu);
                response.sendRedirect("svUsuario?accion=listarUsuarios");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo registrar el usuario: " + e.getMessage());
                request.setAttribute("usuario", usu);
                cargarCargosActivos(request);
                request.getRequestDispatcher("/vistaUsuario/usuarios.jsp").forward(request, response);
            }

        } else {
            request.setAttribute("msje", "Todos los campos son obligatorios.");
            cargarCargosActivos(request);
            request.getRequestDispatcher("/vistaUsuario/usuarios.jsp").forward(request, response);
        }
    }

    private void presentarUsuario(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO daoUsuario = new UsuarioDAO();
        Usuario usuario;

        if (request.getParameter("cod") != null) {
            usuario = new Usuario();
            usuario.setIdUsuario(Integer.parseInt(request.getParameter("cod")));
            try {
                usuario = daoUsuario.leerUsuario(usuario);
                if (usuario != null) {
                    request.setAttribute("usuarioEditar", usuario);
                } else {
                    request.setAttribute("msje", "No se encontró el usuario");
                }
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos" + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se tiene el parametro necesario");
        }

        try {
            List<Usuario> usuarios = daoUsuario.listarUsuarios();
            request.setAttribute("usuarios", usuarios);
            this.cargarCargosActivos(request);
            this.getServletConfig().getServletContext()
                    .getRequestDispatcher("/vistaUsuario/usuarios.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operación" + e.getMessage());
        }
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO DAOusu;
        Usuario usus = null;
        Cargo cargo;
        if (request.getParameter("hUsuario") != null
                && request.getParameter("txtNombre") != null
                && request.getParameter("txtContra") != null
                && request.getParameter("cboCargo") != null) {
            usus = new Usuario();
            usus.setIdUsuario(Integer.parseInt(request.getParameter("hUsuario")));
            usus.setNombreUsuario(request.getParameter("txtNombre"));
            usus.setContra(request.getParameter("txtContra"));
            cargo = new Cargo();
            cargo.setIdCargo(Integer.parseInt(request.getParameter("cboCargo")));
            usus.setIdCargo(cargo);
            if (request.getParameter("chkEstado") != null) {
                usus.setEstado('1');
            } else {
                usus.setEstado('0');
            }
            DAOusu = new UsuarioDAO();
            try {
                DAOusu.actualizarUsuario(usus);
                response.sendRedirect("svUsuario?accion=listarUsuarios");
                return;
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo actualizar el usuario: " + e.getMessage());
                request.setAttribute("usuarioEditar", usus);
                listarUsuarios(request, response);
                return;
            }
        }
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) {
        UsuarioDAO daoUsuario = new UsuarioDAO();
        Usuario usuario = new Usuario();

        if (request.getParameter("cod") != null) {
            usuario.setIdUsuario(Integer.parseInt(request.getParameter("cod")));
            try {
                daoUsuario.eliminarUsuario(usuario);
                response.sendRedirect("svUsuario?accion=listarUsuarios");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos: " + e.getMessage());
                try {
                    request.getRequestDispatcher("/vistaUsuario/usuarios.jsp").forward(request, response);
                } catch (Exception ex) {
                    System.err.println("Error al redirigir: " + ex.getMessage());
                }
            }
        } else {
            request.setAttribute("msje", "No se encontró el usuario");
            try {
                request.getRequestDispatcher("/vistaUsuario/usuarios.jsp").forward(request, response);
            } catch (Exception ex) {
                System.err.println("Error al redirigir: " + ex.getMessage());
            }
        }
    }

}
