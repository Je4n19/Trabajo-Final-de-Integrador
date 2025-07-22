package Servelts;

import DAO.CargoDAO;
import DAO.PermisoDAO;
import Logica.Cargo;
import Logica.Permiso;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "svCargo", urlPatterns = {"/svCargo"})
public class svCargo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "cerrar":
                        cerrarSesion(request, response);
                        break;
                    case "listarCargos":
                        listarCargos(request, response);
                        break;
                    case "leerPermiso":
                        presentarPermiso(request, response);
                        break;
                    case "actualizarPermiso":
                        actualizarPermiso(request, response);
                        break;
                    case "leerCargo":
                        presentarCargo(request, response);
                        break;
                    case "actualizarCargo":
                        actualizarCargo(request, response);
                        break;
                    case "eliminarCargo":
                        eliminarCargo(request, response);
                        break;
                    case "listarCargosEliminados":
                        listarCargosEliminados(request, response);
                        break;
                    case "registrarCargo":
                        registrarCargo(request, response);
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

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession sesion = request.getSession();
        sesion.invalidate();
        response.sendRedirect("login.jsp");
    }

    private void listarCargos(HttpServletRequest request, HttpServletResponse response) {
        CargoDAO daoCargo = new CargoDAO();
        List<Cargo> cargos = null;
        try {
            cargos = daoCargo.listarCargos();
            request.setAttribute("cargos", cargos);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los cargos" + e.getMessage());
        } finally {
            daoCargo = null;
        }
        try {
            this.getServletConfig().getServletContext()
                    .getRequestDispatcher("/vistaCargo/cargos.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la petición" + e.getMessage());
        }

    }

    private void listarCargosEliminados(HttpServletRequest request, HttpServletResponse response) {
        CargoDAO daoCargo = new CargoDAO();
        List<Cargo> cargos = null;
        try {
            cargos = daoCargo.listarCargosEliminados();
            request.setAttribute("cargosEliminados", cargos);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los cargos" + e.getMessage());
        } finally {
            daoCargo = null;
        }
        listarCargos(request, response);
    }

    private void presentarCargo(HttpServletRequest request, HttpServletResponse response) {
        CargoDAO daoCargo = new CargoDAO();
        if (request.getParameter("cod") != null) {
            Cargo cargo = new Cargo();
            cargo.setIdCargo(Integer.parseInt(request.getParameter("cod")));
            try {
                cargo = daoCargo.leerCargoID(cargo);
                if (cargo != null) {
                    request.setAttribute("cargoEditarCargo", cargo);
                    List<Cargo> cargos = daoCargo.listarCargos();
                    request.setAttribute("cargos", cargos);
                    request.getRequestDispatcher("/vistaCargo/cargos.jsp").forward(request, response);
                    return;
                } else {
                    request.setAttribute("msje", "No se encontró al cargo");
                }
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos: " + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se tiene el parámetro necesario");
        }
        listarCargos(request, response);
    }

    private void actualizarCargo(HttpServletRequest request, HttpServletResponse response) {
        CargoDAO daoCargo = new CargoDAO();
        if (request.getParameter("hCargo") != null
                && request.getParameter("txtNombreCargo") != null) {
            Cargo cargo = new Cargo();
            cargo.setIdCargo(Integer.parseInt(request.getParameter("hCargo")));
            cargo.setNombreCargo(request.getParameter("txtNombreCargo"));
            if (request.getParameter("chkEstado") != null) {
                cargo.setEstado('1');
            } else {
                cargo.setEstado('0');
            }
            try {
                daoCargo.actualizarCargo(cargo);
                response.sendRedirect("svCargo?accion=listarCargos");
                return;
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo actualizar el usuario: " + e.getMessage());
                request.setAttribute("cargoEditarCargo", cargo);
                listarCargos(request, response);
                return;
            }
        }
    }

    private void eliminarCargo(HttpServletRequest request, HttpServletResponse response) {
        CargoDAO daoCargo = new CargoDAO();
        Cargo cargo = new Cargo();
        if (request.getParameter("cod") != null) {
            cargo.setIdCargo(Integer.parseInt(request.getParameter("cod")));
            try {
                daoCargo.eliminarCargo(cargo);
                response.sendRedirect("svCargo?accion=listarCargos");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos: " + e.getMessage());
                try {
                    request.getRequestDispatcher("vistaCargo/cargos.jsp").forward(request, response);
                } catch (Exception ex) {
                    System.err.println("Error al redirigir: " + ex.getMessage());
                }
            }
        } else {
            request.setAttribute("msje", "No se encontró el cargo");
            try {
                request.getRequestDispatcher("vistaCargo/cargos.jsp").forward(request, response);
            } catch (Exception ex) {
                System.err.println("Error al redirigir: " + ex.getMessage());
            }
        }
    }

    private void presentarPermiso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PermisoDAO daoPermiso = new PermisoDAO();
        try {
            String cod = request.getParameter("cod");
            if (cod != null) {
                int idCargo = Integer.parseInt(cod);
                Cargo cargo = daoPermiso.leerPermisosPorCargo(idCargo);
                if (cargo != null) {
                    request.setAttribute("cargoEditar", cargo);
                    Set<String> nombresPermisos = new HashSet<>();
                    if (cargo.getPermisos() != null) {
                        for (Permiso p : cargo.getPermisos()) {
                            nombresPermisos.add(p.getNombrePermiso());
                        }
                    }
                    request.setAttribute("nombresPermisos", nombresPermisos);
                } else {
                    request.setAttribute("msje", "No se encontró el cargo");
                }
            } else {
                request.setAttribute("msje", "No se tiene el parámetro necesario");
            }
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo acceder a la base de datos: " + e.getMessage());
        }
        listarCargos(request, response);
    }

    private void actualizarPermiso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idCargo = Integer.parseInt(request.getParameter("hCargo"));
            String[] permisos = request.getParameterValues("permisos");
            List<Integer> permisosSeleccionados = new ArrayList<>();
            if (permisos != null) {
                for (String permisoId : permisos) {
                    permisosSeleccionados.add(Integer.parseInt(permisoId));
                }
            }
            PermisoDAO daoPermiso = new PermisoDAO();
            daoPermiso.actualizarPermisoCargo(idCargo, permisosSeleccionados);
            request.setAttribute("msje", "Permisos actualizados correctamente.");
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo actualizar los permisos: " + e.getMessage());
        }
        listarCargos(request, response);
    }

    private void registrarCargo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CargoDAO daoCargo = new CargoDAO();
        String nombreCargo = request.getParameter("txtNombreCargo");
        String[] permisosSeleccionados = request.getParameterValues("permisos");

        if (nombreCargo != null && !nombreCargo.trim().isEmpty()) {
            Cargo cargo = new Cargo();
            cargo.setNombreCargo(nombreCargo);
            List<Permiso> listaPermisos = new ArrayList<>();
            if (permisosSeleccionados != null) {
                for (String idPermisoStr : permisosSeleccionados) {
                    try {
                        int idPermiso = Integer.parseInt(idPermisoStr);
                        Permiso permiso = new Permiso();
                        permiso.setIdPermiso(idPermiso);
                        listaPermisos.add(permiso);
                    } catch (NumberFormatException e) {
                        request.setAttribute("msje", "Uno o más permisos seleccionados no son válidos.");
                        request.getRequestDispatcher("/vistaCargo/cargos.jsp").forward(request, response);
                        return;
                    }
                }
            }
            cargo.setPermisos(listaPermisos);
            try {
                daoCargo.registrarCargo(cargo);
                response.sendRedirect("svCargo?accion=listarCargos");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo registrar el cargo: " + e.getMessage());
                request.setAttribute("cargo", cargo);
                request.getRequestDispatcher("/vistaCargo/cargos.jsp").forward(request, response);
            }

        } else {
            request.setAttribute("msje", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("/vistaCargo/cargos.jsp").forward(request, response);
        }
    }

}
