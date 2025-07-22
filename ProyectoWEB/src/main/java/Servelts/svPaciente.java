package Servelts;

import DAO.ObstetraDAO;
import DAO.PacienteDAO;
import Logica.Obstetra;
import Logica.Paciente;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "svPaciente", urlPatterns = {"/svPaciente"})
public class svPaciente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        try {
            if (accion != null) {
                switch (accion) {
                    case "cerrar":
                        cerrarSesion(request, response);
                        break;
                    case "listarPacientes":
                        listarPacientes(request, response);
                        break;
                    case "registrar":
                        registrarPaciente(request, response);
                        break;
                    case "leerPaciente":
                        presentarPaciente(request, response);
                        break;
                    case "actualizarPaciente":
                        actualizarPaciente(request, response);
                        break;

                    case "eliminarPaciente":
                        eliminarPaciente(request, response);
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
        request.getSession().invalidate();
        response.sendRedirect("login.jsp");
    }

    private void cargarObstetrasActivos(HttpServletRequest request) {
        ObstetraDAO dao = new ObstetraDAO();
        try {
            List<Obstetra> obstetras = dao.listarObstetrasActivos();
            request.setAttribute("obstetras", obstetras);
        } catch (Exception e) {
            request.setAttribute("msje", "Error al cargar obstetras: " + e.getMessage());
        }
    }

    private void listarPacientes(HttpServletRequest request, HttpServletResponse response) {
        PacienteDAO dao = new PacienteDAO();
        try {
            List<Paciente> pacientes = dao.listarPacientes();
            request.setAttribute("pacientes", pacientes);
            cargarObstetrasActivos(request);
        } catch (Exception e) {
            request.setAttribute("msje", "Error al listar pacientes: " + e.getMessage());
        }
        try {
            request.getRequestDispatcher("/vistaPacientes/pacientes.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("msje", "No se pudo redirigir: " + ex.getMessage());
        }
    }

    private void registrarPaciente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PacienteDAO dao = new PacienteDAO();
        String nombre = request.getParameter("txtNombre");
        String apellido = request.getParameter("txtApellido");
        String edadStr = request.getParameter("txtEdad");
        String idObstetraStr = request.getParameter("cboObstetra");

        if (nombre != null && apellido != null && edadStr != null && idObstetraStr != null
                && !nombre.trim().isEmpty() && !apellido.trim().isEmpty()
                && !edadStr.trim().isEmpty() && !idObstetraStr.trim().isEmpty()) {

            try {
                int edad = Integer.parseInt(edadStr);
                int idObstetra = Integer.parseInt(idObstetraStr);
                Paciente paciente = new Paciente();
                paciente.setNombre(nombre);
                paciente.setApellido(apellido);
                paciente.setEdad(edad);
                paciente.setEstado('1');

                Obstetra obstetra = new Obstetra();
                obstetra.setIdObstetra(idObstetra);
                paciente.setObstetra(obstetra);

                dao.registrarPaciente(paciente);
                response.sendRedirect("svPaciente?accion=listarPacientes");

            } catch (Exception e) {
                request.setAttribute("msje", "Error al registrar paciente: " + e.getMessage());
                cargarObstetrasActivos(request);
                request.getRequestDispatcher("/vistaPacientes/pacientes.jsp").forward(request, response);
            }

        } else {
            request.setAttribute("msje", "Todos los campos son obligatorios.");
            cargarObstetrasActivos(request);
            request.getRequestDispatcher("/vistaPaciente/pacientes.jsp").forward(request, response);
        }
    }

    private void presentarPaciente(HttpServletRequest request, HttpServletResponse response) {
        PacienteDAO dao = new PacienteDAO();
        if (request.getParameter("cod") != null) {
            try {
                int idPaciente = Integer.parseInt(request.getParameter("cod"));
                Paciente paciente = dao.leerPaciente(idPaciente);
                request.setAttribute("pacienteEditar", paciente);
            } catch (Exception e) {
                request.setAttribute("msje", "Error al leer paciente: " + e.getMessage());
            }
        }
        listarPacientes(request, response);
    }

    private void actualizarPaciente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PacienteDAO dao = new PacienteDAO();
        Paciente paciente = new Paciente();
        try {
            paciente.setIdPaciente(Integer.parseInt(request.getParameter("hPaciente")));
            paciente.setNombre(request.getParameter("txtNombre"));
            paciente.setApellido(request.getParameter("txtApellido"));
            paciente.setEdad(Integer.parseInt(request.getParameter("txtEdad")));

            Obstetra obstetra = new Obstetra();
            obstetra.setIdObstetra(Integer.parseInt(request.getParameter("cboObstetra")));
            paciente.setObstetra(obstetra);

            paciente.setEstado(request.getParameter("chkEstado") != null ? '1' : '0');

            dao.actualizarPaciente(paciente);
            response.sendRedirect("svPaciente?accion=listarPacientes");

        } catch (Exception e) {
            request.setAttribute("msje", "Error al actualizar paciente: " + e.getMessage());
            request.setAttribute("pacienteEditar", paciente);
            cargarObstetrasActivos(request);
            request.getRequestDispatcher("/vistaPacientes/pacientes.jsp").forward(request, response);
        }
    }

    private void eliminarPaciente(HttpServletRequest request, HttpServletResponse response) {
        PacienteDAO dao = new PacienteDAO();
        if (request.getParameter("cod") != null) {
            try {
                int id = Integer.parseInt(request.getParameter("cod"));
                dao.eliminarPaciente(id);
                response.sendRedirect("svPaciente?accion=listarPacientes");
            } catch (Exception e) {
                request.setAttribute("msje", "Error al eliminar paciente: " + e.getMessage());
                listarPacientes(request, response);
            }
        }
    }
}
