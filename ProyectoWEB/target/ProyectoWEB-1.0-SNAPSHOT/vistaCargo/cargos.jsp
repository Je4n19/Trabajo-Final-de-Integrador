<%@page import="java.util.Set"%>
<%@page import="Logica.Cargo"%>
<%@page import="java.util.List"%>
<%@page import="Logica.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<meta charset="UTF-8">
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    List<Integer> permisos = (List<Integer>) session.getAttribute("permisos");
    if (usuario != null && permisos != null) {
%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Perfiles</title>
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

                <main class="ms-auto pt-5 mt-5 px-3" style="margin-left: 250px; width: calc(100% - 250px);">
                    <div class="container-fluid">
                        <div class="row justify-content-center">
                            <div class="col-12 col-lg-11 col-xl-10">
                                <div class="card shadow-lg">
                                    <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
                                        <h5 class="mb-0">
                                            <i class="bi bi-person-lines-fill me-2"></i>Lista de Cargos
                                        </h5>
                                        <a href="#"
                                           class="btn btn-light text-dark fw-bold d-flex align-items-center shadow-sm border"
                                           data-bs-toggle="modal"
                                           data-bs-target="#modalRegistrarCargo">
                                            <i class="bi bi-suitcase-lg me-2 fs-5"></i>
                                            <span class="d-none d-sm-inline">Nuevo Cargo</span>
                                        </a>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table table-hover table-striped align-middle text-center">
                                                <thead class="table-dark">
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Cargo</th>
                                                        <th>Estado</th>
                                                        <th>Acciones</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        List<Cargo> listaCargos = (List<Cargo>) request.getAttribute("cargos");
                                                        if (listaCargos != null) {
                                                            for (Cargo c : listaCargos) {
                                                    %>
                                                    <tr>
                                                        <td><%= c.getIdCargo()%></td>
                                                        <td><%= c.getNombreCargo()%></td>
                                                        <td>
                                                            <%
                                                                char estado = c.getEstado();
                                                                String color = estado == '1' ? "success" : "secondary";
                                                                String texto = estado == '1' ? "Activo" : "Inactivo";
                                                            %>
                                                            <span class="badge bg-<%= color%>"><%= texto%></span>
                                                        </td>
                                                        <td class="text-center">
                                                            <div class="d-flex flex-sm-row flex-column justify-content-center align-items-center gap-2">
                                                                <a href="svCargo?accion=leerCargo&cod=<%= c.getIdCargo()%>"
                                                                   class="btn btn-sm btn-outline-primary"
                                                                   title="Editar">
                                                                    <i class="bi bi-pencil-square"></i>
                                                                </a>
                                                                <a href="svCargo?accion=leerPermiso&cod=<%= c.getIdCargo()%>"
                                                                   class="btn btn-sm btn-outline-success"
                                                                   title="EditarPermiso">
                                                                    <i class="bi bi-archive"></i>
                                                                </a>
                                                                <a href="svCargo?accion=eliminarCargo&cod=<%= c.getIdCargo()%>"
                                                                   class="btn btn-sm btn-outline-danger"
                                                                   title="Eliminar"
                                                                   onclick="return confirm('¿Estás seguro de eliminar este usuario?');">
                                                                    <i class="bi bi-trash3-fill"></i>
                                                                </a>
                                                            </div>
                                                        </td>

                                                    </tr>
                                                    <% }
                                                    } else { %>
                                                    <tr>
                                                        <td colspan="4" class="text-center text-muted">No hay cargos para mostrar</td>
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

        <div class="modal fade" id="modalRegistrarCargo" tabindex="-1" aria-labelledby="modalRegistrarCargoLabel" aria-hidden="true">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalRegistrarCargoLabel">
                            <i class="bi bi-pencil-square me-2"></i>Registrar Nuevo Cargo
                        </h5>
                        <a href="svCargo?accion=listarCargos" class="btn-close" aria-label="Cerrar"></a>
                    </div>
                    <div class="modal-body">
                        <form action="svCargo?accion=registrarCargo" method="POST">
                            <div class="mb-3">
                                <label class="form-label">Nombre del Cargo</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-suitcase-lg"></i></span>
                                    <input type="text" class="form-control" name="txtNombreCargo" placeholder="Ejem: Administrador" autocomplete="off" required>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Escoja los permisos:</label>
                                <div class="row row-cols-1 row-cols-md-2 g-3">
                                    <div class="col">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" id="verUsuarios" name="permisos" value="1">
                                            <label class="form-check-label" for="verUsuarios">
                                                <i class="bi bi-people-fill me-1"></i> Ver Usuarios
                                            </label>
                                        </div>
                                    </div>

                                    <div class="col">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" id="verCargos" name="permisos" value="2">
                                            <label class="form-check-label" for="verCargos">
                                                <i class="bi bi-person-badge-fill me-1"></i> Ver Perfiles
                                            </label>
                                        </div>
                                    </div>

                                    <div class="col">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" id="verObstetras" name="permisos" value="3">
                                            <label class="form-check-label" for="verObstetras">
                                                <i class="bi bi-heart-pulse-fill me-1"></i> Ver Obstetras
                                            </label>
                                        </div>
                                    </div>

                                    <div class="col">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" id="verPacientes" name="permisos" value="4">
                                            <label class="form-check-label" for="verPacientes">
                                                <i class="bi bi-person-vcard-fill me-1"></i> Ver Pacientes
                                            </label>
                                        </div>
                                    </div>
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
            Cargo cargoEditar = (Cargo) request.getAttribute("cargoEditar");
        %>
        <div class="modal fade" id="modalEditarPermiso" tabindex="-1" aria-hidden="true">
            <% if (cargoEditar != null) {%>
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <form action="svCargo?accion=actualizarPermiso" method="post">
                        <div class="modal-header">
                            <h5 class="modal-title">
                                <i class="bi bi-pencil-square me-2"></i>Editar Permisos
                                <br>Cargo: <%= cargoEditar.getNombreCargo()%>
                            </h5>
                            <a href="svCargo?accion=listarCargos" class="btn-close" aria-label="Cerrar"></a>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="hCargo" id="hCargo" value="<%= cargoEditar.getIdCargo()%>">
                            <%
                                Set<String> nombresPermisos = (Set<String>) request.getAttribute("nombresPermisos");
                                char estadoCargo = cargoEditar.getEstado();
                            %>
                            <div class="mb-3">
                                <label class="form-label fw-bold d-block ms-5">Escoja los permisos:</label>
                                <div class="ms-5">
                                    <div class="form-check mb-2">
                                        <input class="form-check-input" type="checkbox" id="verUsuarios" name="permisos" value="1"
                                               <%= (nombresPermisos != null && nombresPermisos.contains("verUsuarios")) ? "checked" : ""%>>
                                        <label class="form-check-label" for="verUsuarios">
                                            <i class="bi bi-people-fill me-1"></i> Ver Usuarios
                                        </label>
                                    </div>

                                    <div class="form-check mb-2">
                                        <input class="form-check-input" type="checkbox" id="verCargos" name="permisos" value="2"
                                               <%= (nombresPermisos != null && nombresPermisos.contains("verCargos")) ? "checked" : ""%>>
                                        <label class="form-check-label" for="verCargos">
                                            <i class="bi bi-person-badge-fill me-1"></i> Ver Perfiles
                                        </label>
                                    </div>

                                    <div class="form-check mb-2">
                                        <input class="form-check-input" type="checkbox" id="verObstetras" name="permisos" value="3"
                                               <%= (nombresPermisos != null && nombresPermisos.contains("verObstetras")) ? "checked" : ""%>>
                                        <label class="form-check-label" for="verObstetras">
                                            <i class="bi bi-heart-pulse-fill me-1"></i> Ver Obstetras
                                        </label>
                                    </div>

                                    <div class="form-check mb-2">
                                        <input class="form-check-input" type="checkbox" id="verPacientes" name="permisos" value="4"
                                               <%= (nombresPermisos != null && nombresPermisos.contains("verPacientes")) ? "checked" : ""%>>
                                        <label class="form-check-label" for="verPacientes">
                                            <i class="bi bi-person-vcard-fill me-1"></i> Ver Pacientes
                                        </label>
                                    </div>
                                </div>

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
            <% } %>
        </div>
        <% if (cargoEditar != null) { %>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var modalEditar = new bootstrap.Modal(document.getElementById('modalEditarPermiso'));
                modalEditar.show();
            });
        </script>
        <% } %>

        <%
            Cargo cargoEditarCargo = (Cargo) request.getAttribute("cargoEditarCargo");
        %>
        <div class="modal fade" id="modalEditarCargo" tabindex="-1" aria-hidden="true">
            <% if (cargoEditarCargo != null) {%>
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <form action="svCargo?accion=actualizarCargo" method="post">
                        <div class="modal-header">
                            <h5 class="modal-title">
                                <i class="bi bi-pencil-square me-2"></i>Editar Cargo
                            </h5>
                            <a href="svCargo?accion=listarCargos" class="btn-close" aria-label="Cerrar"></a>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="hCargo" id="hCargo" value="<%= cargoEditarCargo.getIdCargo()%>">
                            <div class="mb-3">
                                <div class="mb-3">
                                    <label class="form-label">Nombre Usuario</label>
                                    <div class="input-group">
                                        <span class="input-group-text"><i class="bi bi-person-fill"></i></span>
                                        <input type="text" name="txtNombreCargo" id="txtNombreCargo" value="<%= cargoEditarCargo.getNombreCargo()%>" class="form-control" placeholder="Ejem: Pablo" required>
                                    </div>
                                </div>
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" class="form-check-input" id="chkEstado" name="chkEstado"
                                       <%= cargoEditarCargo.getEstado() == '1' ? "checked" : ""%>>
                                <label class="form-check-label" for="chkEstado">Cargo Activo</label>
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
            <% } %>
        </div>
        <% if (cargoEditarCargo != null) { %>
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var modalEditarCargo = new bootstrap.Modal(document.getElementById('modalEditarCargo'));
                modalEditarCargo.show();
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
