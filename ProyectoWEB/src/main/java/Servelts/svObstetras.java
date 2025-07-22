package Servelts;

import DAO.ObstetraDAO;
import Logica.Obstetra;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "svObstetras", urlPatterns = {"/svObstetras"})
public class svObstetras extends HttpServlet {

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
                    case "listarObstetras":
                        listarObstetras(request, response);
                        break;
                    case "registrar":
                        registrarObstetra(request, response);
                        break;
                    case "leerObstetra":
                        presentarObstetra(request, response);
                        break;
                    case "actualizarObstetra":
                        actualizarObstetra(request, response);
                        break;
                    case "eliminarObstetra":
                        eliminarObstetra(request, response);
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

    private void listarObstetras(HttpServletRequest request, HttpServletResponse response) {
        ObstetraDAO dao = new ObstetraDAO();
        List<Obstetra> obstetras = null;
        try {
            obstetras = dao.listarObstetras();
            request.setAttribute("obstetras", obstetras);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo listar los obstetras: " + e.getMessage());
        }

        try {
            this.getServletConfig().getServletContext()
                    .getRequestDispatcher("/vistaObstetras/obstetras.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la petición: " + e.getMessage());
        }
    }

    private void registrarObstetra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObstetraDAO dao = new ObstetraDAO();
        String nombre = request.getParameter("txtNombreObstetra");

        if (nombre != null && !nombre.trim().isEmpty()) {
            Obstetra obs = new Obstetra();
            obs.setNombreObstetra(nombre);
            try {
                dao.registrarObstetra(obs);
                response.sendRedirect("svObstetras?accion=listarObstetras");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo registrar el obstetra: " + e.getMessage());
                request.setAttribute("obstetra", obs);
                request.getRequestDispatcher("/vistaObstetras/obstetras.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("msje", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("/vistaObstetras/obstetras.jsp").forward(request, response);
        }
    }

    private void presentarObstetra(HttpServletRequest request, HttpServletResponse response) {
        ObstetraDAO dao = new ObstetraDAO();
        Obstetra obstetra;
        if (request.getParameter("cod") != null) {
            obstetra = new Obstetra();
            obstetra.setIdObstetra(Integer.parseInt(request.getParameter("cod")));
            try {
                obstetra = dao.leerObstetra(obstetra);
                if (obstetra != null) {
                    request.setAttribute("obstetraEditar", obstetra);
                } else {
                    request.setAttribute("msje", "No se encontró el obstetra");
                }
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos: " + e.getMessage());
            }
        } else {
            request.setAttribute("msje", "No se tiene el parámetro necesario");
        }

        try {
            List<Obstetra> obstetras = dao.listarObstetras();
            request.setAttribute("obstetras", obstetras);
            this.getServletConfig().getServletContext()
                    .getRequestDispatcher("/vistaObstetras/obstetras.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msje", "No se pudo realizar la operación: " + e.getMessage());
        }
    }

    private void actualizarObstetra(HttpServletRequest request, HttpServletResponse response) {
        ObstetraDAO dao = new ObstetraDAO();
        Obstetra obs = new Obstetra();

        if (request.getParameter("hObstetra") != null && request.getParameter("txtNombreObstetra") != null) {
            obs.setIdObstetra(Integer.parseInt(request.getParameter("hObstetra")));
            obs.setNombreObstetra(request.getParameter("txtNombreObstetra"));

            if (request.getParameter("chkEstado") != null) {
                obs.setEstado('1');
            } else {
                obs.setEstado('0');
            }

            try {
                dao.actualizarObstetra(obs);
                response.sendRedirect("svObstetras?accion=listarObstetras");
                return;
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo actualizar el obstetra: " + e.getMessage());
                request.setAttribute("obstetraEditar", obs);
                listarObstetras(request, response);
                return;
            }
        }
    }

    private void eliminarObstetra(HttpServletRequest request, HttpServletResponse response) {
        ObstetraDAO dao = new ObstetraDAO();

        if (request.getParameter("cod") != null) {
            int idObstetra = Integer.parseInt(request.getParameter("cod"));
            try {
                dao.eliminarObstetra(idObstetra); 
                response.sendRedirect("svObstetras?accion=listarObstetras");
            } catch (Exception e) {
                request.setAttribute("msje", "No se pudo acceder a la base de datos: " + e.getMessage());
                try {
                    request.getRequestDispatcher("/vistaObstetras/obstetras.jsp").forward(request, response);
                } catch (Exception ex) {
                    System.err.println("Error al redirigir: " + ex.getMessage());
                }
            }
        } else {
            request.setAttribute("msje", "No se encontró el obstetra");
            try {
                request.getRequestDispatcher("/vistaObstetras/obstetras.jsp").forward(request, response);
            } catch (Exception ex) {
                System.err.println("Error al redirigir: " + ex.getMessage());
            }
        }
    }

}
