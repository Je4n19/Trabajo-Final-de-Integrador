
package Logica;

import java.util.ArrayList;
import java.util.List;

public class Cargo {
    private int idCargo;
    private String NombreCargo;
    private char estado;
    private List<Permiso> permisos;
    
    public Cargo(){
     permisos = new ArrayList<>();
    }
    

    public Cargo(int idCargo, String NombreCargo, char estado) {
        this.idCargo = idCargo;
        this.NombreCargo = NombreCargo;
        this.estado = estado;
    }

    public Cargo(int idCargo, String NombreCargo) {
        this.idCargo = idCargo;
        this.NombreCargo = NombreCargo;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombreCargo() {
        return NombreCargo;
    }

    public void setNombreCargo(String NombreCargo) {
        this.NombreCargo = NombreCargo;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }
    
}
