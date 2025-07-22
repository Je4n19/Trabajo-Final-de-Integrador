
package Logica;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int idUsuario;
    private String nombreUsuario;
    private String contra;
    private char estado;
    private Cargo idCargo;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombreUsuario, String contra, char estado, Cargo idCargo) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contra = contra;
        this.estado = estado;
        this.idCargo = idCargo;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public Cargo getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Cargo idCargo) {
        this.idCargo = idCargo;
    }
    
}
