<%@page import="Logica.Obstetra"%>
<%@page import="java.util.List"%>
<%@page import="Logica.Usuario"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    List<Integer> permisos = (List<Integer>) session.getAttribute("permisos");
    if (usuario != null && permisos != null) {
%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Categorias</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
    </head>
    <body>
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


                <main class="ms-auto ps-3 pe-3 pt-5" style="margin-left: 250px; width: calc(100% - 250px);">
                    <div class="container-fluid mt-5 pt-3">
                        <div class="row justify-content-center">
                            <div class="col-12 col-lg-11 col-xl-10">
                                <div class="card shadow">
                                    <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
                                        <h5 class="mb-0"><i class="bi bi-person-vcard-fill me-2"></i>Lista de Obstetras</h5>
                                        <a href="#" class="btn btn-light text-dark fw-bold d-flex align-items-center shadow-sm border"
                                           data-bs-toggle="modal" data-bs-target="#modalRegistrarObstetra">
                                            <i class="bi bi-person-plus me-2 fs-5"></i>
                                            <span class="d-none d-sm-inline">Nuevo Obstetra</span>
                                        </a>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table table-hover table-striped align-middle text-center">
                                                <thead class="table-dark">
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Nombre</th>
                                                        <th>Estado</th>
                                                        <th>Acciones</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        List<Obstetra> listaObstetras = (List<Obstetra>) request.getAttribute("obstetras");
                                                        if (listaObstetras != null) {
                                                            for (Obstetra o : listaObstetras) {
                                                    %>
                                                    <tr>
                                                        <td><%= o.getIdObstetra()%></td>
                                                        <td><%= o.getNombreObstetra()%></td>
                                                        <td>
                                                            <%
                                                                char estado = o.getEstado();
                                                                String color = estado == '1' ? "success" : "secondary";
                                                                String texto = estado == '1' ? "Activo" : "Inactivo";
                                                            %>
                                                            <span class="badge bg-<%= color%>"><%= texto%></span>
                                                        </td>
                                                        <td class="text-center">
                                                            <div class="d-flex flex-sm-row flex-column justify-content-center align-items-center gap-2">
                                                                <a href="svObstetras?accion=leerObstetra&cod=<%= o.getIdObstetra()%>"
                                                                   class="btn btn-sm btn-outline-primary"
                                                                   title="Editar">
                                                                    <i class="bi bi-pencil-square"></i>
                                                                </a>
                                                                <a href="svObstetras?accion=eliminarObstetra&cod=<%= o.getIdObstetra()%>"
                                                                   class="btn btn-sm btn-outline-danger"
                                                                   title="Eliminar"
                                                                   onclick="return confirm('¿Estás seguro de eliminar este obstetra?');">
                                                                    <i class="bi bi-trash3-fill"></i>
                                                                </a>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <% }
                                                    } else { %>
                                                    <tr>
                                                        <td colspan="4" class="text-center text-muted">No hay obstetras para mostrar</td>
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

        <div class="modal fade" id="modalRegistrarObstetra" tabindex="-1" aria-labelledby="modalRegistrarObstetraLabel" aria-hidden="true">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalRegistrarObstetraLabel">
                            <i class="bi bi-pencil-square me-2"></i>Registrar Nuevo Obstetra
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                    </div>
                    <div class="modal-body">
                        <form action="svObstetras?accion=registrar" method="POST">
                            <div class="mb-3">
                                <label class="form-label">Nombre del Obstetra:</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-person-vcard"></i></span>
                                    <input type="text" class="form-control" name="txtNombreObstetra" placeholder="Ej: Dr. Juan Pérez" autocomplete="off" required>
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
            Obstetra obstetraEditar = (Obstetra) request.getAttribute("obstetraEditar");
        %>
        <% if (obstetraEditar != null) {%>
        <div class="modal fade" id="modalEditar" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <form action="svObstetras?accion=actualizarObstetra" method="post">
                        <div class="modal-header">
                            <h5 class="modal-title">
                                <i class="bi bi-pencil-square me-2"></i>Editar Obstetra
                            </h5>
                            <a href="svObstetras?accion=listarObstetras" class="btn-close" aria-label="Cerrar"></a>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="hObstetra" id="hObstetra" value="<%= obstetraEditar.getIdObstetra()%>">
                            <div class="mb-3">
                                <label class="form-label">Nombre del Obstetra:</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-person-vcard"></i></span>
                                    <input type="text" name="txtNombreObstetra" id="txtNombreObstetra" value="<%= obstetraEditar.getNombreObstetra()%>" class="form-control" placeholder="Ej: Dra. Ana Torres" required>
                                </div>
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" class="form-check-input" id="chkEstado" name="chkEstado"
                                       <%= obstetraEditar.getEstado() == '1' ? "checked" : ""%>>
                                <label class="form-check-label" for="chkEstado">Obstetra Activo</label>
                            </div>
                            <div class="d-flex justify-content-center align-items-center mt-4 mx-3 mb-0">
                                <button type="submit" class="btn btn-dark">
                                    <i class="bi bi-save-fill"></i> Actualizar
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <% } %>

        <% if (obstetraEditar != null) { %>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var modalEditar = new bootstrap.Modal(document.getElementById('modalEditar'));
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