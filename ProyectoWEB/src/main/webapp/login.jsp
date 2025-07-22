<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
        <title>Login</title>
    </head>
    <body class="bg-dark d-flex justify-content-center align-items-center vh-100">
        <div class="bg-white p-5 rounded-3 text-secondary shadow" style="width: 25rem ">
            <form action="svUsuario?accion=verificar" method="POST">
                <div class="d-flex justify-content-center text-dark">
                    <i class="bi bi-person-circle" style="font-size: 100px;"></i>
                </div>
                <div class="text-center fs-4 fw-bold text-dark">ACCESO AL SISTEMA</div>
                <div class="text-center text-muted mb-3" style="font-size: 0.9rem;">Pacientes y Obstetras</div>
                <div class="input-group mt-3 justify-content-center">
                    <div class="input-group-text">
                        <i class="bi bi-person fs-4"></i>
                    </div>
                    <div  class="form-floating">
                        <input type="text" class="form-control bg-light" id="Usuario" name="txtUsuario" placeholder="Nombre" required>
                        <label for="Usuario">Usuario*</label>
                    </div>
                </div>
                <div class="input-group mt-3 justify-content-center">
                    <div class="input-group-text">
                        <i class="bi bi-lock fs-4"></i>
                    </div>
                    <div class="form-floating">
                        <input type="password" class="form-control bg-light" id="contra" name="txtContra" placeholder="Contraseña" required>
                        <label for="contra">Contraseña*</label>
                    </div>
                </div>
                <div class="text-center">
                    <input class="btn  btn-dark text-white w-100 mt-4 fw-bold shadow-sm" type="submit" name="verificar" value="INGRESAR">
                </div><br>
                <div class="text-center">
                    <p>- Verificación de Credenciales -</p>
                    <div class="alert bg-dark text-white text-center py-2" role="alert" style="max-width: 300px; margin: 10px auto;">
                        <i class="bi bi-info-lg me-2"></i>
                        Mensaje: 
                        <%= request.getAttribute("msje") != null
                                ? request.getAttribute("msje") : "Inicie Sesión"%>
                    </div>
                </div>
            </form>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js" integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO" crossorigin="anonymous"></script>
    </body>
</html>

