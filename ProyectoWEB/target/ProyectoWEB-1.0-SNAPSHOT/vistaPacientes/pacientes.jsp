<%@page import="Logica.Obstetra"%>
<%@page import="Logica.Paciente"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="Logica.Categoria"%>
<%@page import="Logica.Producto"%>
<%@page import="java.util.List"%>
<%@page import="Logica.Usuario"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    List<Integer> permisos = (List<Integer>) session.getAttribute("permisos");
    if (usuario != null && permisos != null) {
%>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Productos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
    </head>
    <body>
        <%
            List<Categoria> listaCategorias = (List<Categoria>) request.getAttribute("categorias");
        %>
        <div class="container-fluid">
            <div class="row">
                <div class="position-fixed top-0 start-0 bg-dark d-flex flex-column vh-100" style="width: 250px; z-index: 1040;">
                    <div class="text-white py-3 px-2 text-center border-bottom border-white">
                        <a href="index.jsp" class="text-decoration-none text-white">
                            <header class="d-flex flex-column align-items-center">
                                <i class="bi bi-hospital-fill fs-1 mb-1"></i>
                                <h1 class="h5 fw-bold mb-1 d-none d-sm-block">Sistema de Hospital</h1>
                                <small class="text-white-50 d-none d-sm-block">Gestión de obstetras y pacientes</small>
                            </header>
                        </a>
                    </div>
                     <nav class="nav flex-column flex-grow-1 px-2 pb-5">
                        <% if (permisos.contains(1)) { %>
                        <a class="btn btn-dark w-100 text-start mt-3" href="svUsuario?accion=listarUsuarios">
                            <i class="bi bi-people-fill"></i><span class="d-none d-sm-inline ms-2">Usuarios</span>
                        </a>
                        <% } %>

                        <% if (permisos.contains(2)) { %>
                        <a class="btn btn-dark w-100 text-start mt-3" href="svCargo?accion=listarCargos">
                            <i class="bi bi-person-badge-fill"></i><span class="d-none d-sm-inline ms-2">Perfiles</span>
                        </a>
                        <% } %>

                        <% if (permisos.contains(3)) { %>
                        <a class="btn btn-dark w-100 text-start mt-3" href="svObstetras?accion=listarObstetras">
                            <i class="bi bi-heart-pulse-fill"></i><span class="d-none d-sm-inline ms-2">Obstetras</span>
                        </a>
                        <% } %>

                        <% if (permisos.contains(4)) { %>
                        <a class="btn btn-dark w-100 text-start mt-3" href="svPaciente?accion=listarPacientes">
                            <i class="bi bi-person-vcard-fill"></i><span class="d-none d-sm-inline ms-2">Pacientes</span>
                        </a>
                        <% }%>
                    </nav>
                </div>

                <nav class="navbar navbar-expand-lg bg-body-tertiary p-3 fixed-top shadow-sm" style="left: 250px; width: calc(100% - 250px); z-index: 1030;">
                    <div class="container-fluid d-flex justify-content-between align-items-center">

                        <div class="d-flex align-items-center text-dark">
                            <i class="bi bi-person-arms-up fs-3 me-2"></i>
                            <div class="d-flex flex-column">
                                <span class="fw-bold text-uppercase small">Usuario: <%= usuario.getNombreUsuario()%></span>
                                <small class="text-muted text-uppercase small">Cargo: <%= usuario.getIdCargo().getNombreCargo()%></small>
                            </div>
                        </div>

                        <a class="btn btn-outline-dark d-flex align-items-center" href="svUsuario?accion=cerrar">
                            <i class="bi bi-box-arrow-right fs-5"></i>
                            <span class="ms-2 fw-semibold">Salir</span>
                        </a>

                    </div>
                </nav>


                <main class="ms-auto pt-5 mt-5 px-3" style="margin-left: 250px; width: calc(100% - 250px);">
                    <div class="container-fluid">
                        <div class="row justify-content-center">
                            <div class="col-12 col-lg-11 col-xl-10">
                                <div class="card shadow">
                                    <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
                                        <h5 class="mb-0"><i class="bi bi-people-fill me-2"></i>Listado de Pacientes</h5>
                                        <a href="#" class="btn btn-light text-dark fw-bold d-flex align-items-center shadow-sm border"
                                           data-bs-toggle="modal" data-bs-target="#modalRegistrarPaciente">
                                            <i class="bi bi-person-plus me-2 fs-5"></i>
                                            <span class="d-none d-sm-inline">Nuevo Paciente</span>
                                        </a>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table table-hover table-striped align-middle text-center">
                                                <thead class="table-dark">
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Nombre</th>
                                                        <th>Apellido</th>
                                                        <th>Edad</th>
                                                        <th>Obstetra(s)</th>
                                                        <th>Estado</th>
                                                        <th>Acciones</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        List<Paciente> listaPacientes = (List<Paciente>) request.getAttribute("pacientes");
                                                        if (listaPacientes != null) {
                                                            for (Paciente p : listaPacientes) {
                                                                List<Obstetra> obstetras = (List<Obstetra>) request.getAttribute("obstetrasPaciente" + p.getIdPaciente());
                                                    %>
                                                    <tr>
                                                        <td><%= p.getIdPaciente()%></td>
                                                        <td><%= p.getNombre()%></td>
                                                        <td><%= p.getApellido()%></td>
                                                        <td><%= p.getEdad()%></td>
                                                        <td>
                                                            <%= p.getObstetra().getNombreObstetra()%>
                                                        </td>
                                                        <td>
                                                            <%
                                                                char estado = p.getEstado();
                                                                String color = estado == '1' ? "success" : "secondary";
                                                                String texto = estado == '1' ? "Activo" : "Inactivo";
                                                            %>
                                                            <span class="badge bg-<%= color%>"><%= texto%></span>
                                                        </td>
                                                        <td class="text-center">
                                                            <div class="d-flex flex-sm-row flex-column justify-content-center align-items-center gap-2">
                                                                <a href="svPaciente?accion=leerPaciente&cod=<%= p.getIdPaciente()%>"
                                                                   class="btn btn-sm btn-outline-primary"
                                                                   title="Editar">
                                                                    <i class="bi bi-pencil-square"></i>
                                                                </a>
                                                                <a href="svPaciente?accion=eliminarPaciente&cod=<%= p.getIdPaciente()%>"
                                                                   class="btn btn-sm btn-outline-danger"
                                                                   title="Eliminar"
                                                                   onclick="return confirm('¿Estás seguro de eliminar este paciente?');">
                                                                    <i class="bi bi-trash3-fill"></i>
                                                                </a>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <%  }
                                                    } else { %>
                                                    <tr>
                                                        <td colspan="7" class="text-center text-muted">No hay pacientes para mostrar</td>
                                                    </tr>
                                                    <% } %>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>


        <div class="modal fade" id="modalRegistrarPaciente" tabindex="-1" aria-labelledby="modalRegistrarPacienteLabel" aria-hidden="true">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalRegistrarPacienteLabel">
                            <i class="bi bi-pencil-square me-2"></i>Registrar Nuevo Paciente
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                    </div>
                    <div class="modal-body">
                        <form action="svPaciente?accion=registrar" method="post" accept-charset="UTF-8">
                            <div class="mb-3">
                                <label class="form-label">Nombre</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-person-fill"></i></span>
                                    <input type="text" class="form-control" name="txtNombre" placeholder="Ej: María" autocomplete="off" required>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Apellido</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-person-lines-fill"></i></span>
                                    <input type="text" class="form-control" name="txtApellido" placeholder="Ej: López" autocomplete="off" required>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Edad</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-cake"></i></span>
                                    <input type="number" class="form-control" name="txtEdad" placeholder="Ej: 28" min="0" required>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Obstetra</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-person-vcard"></i></span>
                                    <select class="form-select" name="cboObstetra" required>
                                        <option value="">Seleccione un Obstetra</option>
                                        <%
                                            List<Obstetra> listaObstetras = (List<Obstetra>) request.getAttribute("obstetras");
                                            if (listaObstetras != null && !listaObstetras.isEmpty()) {
                                                for (Obstetra o : listaObstetras) {
                                        %>
                                        <option value="<%= o.getIdObstetra()%>"><%= o.getNombreObstetra()%></option>
                                        <%
                                            }
                                        } else {
                                        %>
                                        <option selected>No hay obstetras disponibles</option>
                                        <%
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="d-flex justify-content-center align-items-center mt-4">
                                <button type="submit" class="btn btn-dark">
                                    <i class="bi bi-save-fill"></i> Registrar
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <%
            Paciente pacienteEditar = (Paciente) request.getAttribute("pacienteEditar");
            List<Obstetra> listaObstetras2= (List<Obstetra>) request.getAttribute("obstetras");
        %>
        <% if (pacienteEditar != null) {%>
        <div class="modal fade" id="modalEditarPaciente" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <form action="svPaciente?accion=actualizarPaciente" method="post">
                        <div class="modal-header">
                            <h5 class="modal-title">
                                <i class="bi bi-pencil-square me-2"></i>Editar Paciente
                            </h5>
                            <a href="svPaciente?accion=listarPacientes" class="btn-close" aria-label="Cerrar"></a>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="hPaciente" value="<%= pacienteEditar.getIdPaciente()%>">
                            <div class="mb-3">
                                <label class="form-label">Nombre</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-person-fill"></i></span>
                                    <input type="text" name="txtNombre" value="<%= pacienteEditar.getNombre()%>" class="form-control" placeholder="Ej: Ana" required>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Apellido</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-person-lines-fill"></i></span>
                                    <input type="text" name="txtApellido" value="<%= pacienteEditar.getApellido()%>" class="form-control" placeholder="Ej: Ramírez" required>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Edad</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-cake"></i></span>
                                    <input type="number" name="txtEdad" value="<%= pacienteEditar.getEdad()%>" class="form-control" required>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Obstetra</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-person-vcard"></i></span>
                                    <select name="cboObstetra" class="form-select" required>
                                        <option value="">Seleccione un Obstetra</option>
                                        <%
                                            if (listaObstetras2 != null) {
                                                for (Obstetra o : listaObstetras2) {
                                                    boolean seleccionado = pacienteEditar.getObstetra().getIdObstetra() == o.getIdObstetra();
                                        %>
                                        <option value="<%= o.getIdObstetra()%>" <%= seleccionado ? "selected" : ""%>>
                                            <%= o.getNombreObstetra()%>
                                        </option>
                                        <%
                                            }
                                        } else {
                                        %>
                                        <option selected>No hay obstetras disponibles</option>
                                        <% }%>
                                    </select>
                                </div>
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" class="form-check-input" id="chkEstado" name="chkEstado"
                                       <%= pacienteEditar.getEstado() == '1' ? "checked" : ""%>>
                                <label class="form-check-label" for="chkEstado">Paciente Activo</label>
                            </div>
                            <div class="d-flex justify-content-center align-items-center mt-4">
                                <button type="submit" class="btn btn-dark">
                                    <i class="bi bi-save-fill"></i> Actualizar
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var modalEditar = new bootstrap.Modal(document.getElementById('modalEditarPaciente'));
                modalEditar.show();
            });
        </script>
        <% } %>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" crossorigin="anonymous"></script>
    </body>
</html>
<%
    } else {
        response.sendRedirect("login.jsp");
    }
%>