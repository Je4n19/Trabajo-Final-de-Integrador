<%@page import="java.util.List"%>
<%@page import="Logica.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    List<Integer> permisos = (List<Integer>) session.getAttribute("permisos");
    if (usuario != null && permisos != null) {
%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Principal</title>
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
                                            <i class="bi bi-hospital-fill me-2"></i>Sistema de Hospital
                                        </h5>
                                    </div>
                                    <div class="card-body text-center">
                                        <img src="<%= request.getContextPath()%>/img/Hospital.jpg" 
                                             alt="Imagen del Hospital" 
                                             class="img-fluid rounded shadow-sm w-100" 
                                             style="max-height: 600px;">

                                        <h4 class="mt-4 text-secondary">Bienvenido al sistema de gestión hospitalaria</h4>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" crossorigin="anonymous"></script>
    </body>
</html>
<%
    } else {
        response.sendRedirect("login.jsp");
    }
%>