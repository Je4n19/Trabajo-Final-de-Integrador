
package Logica;

public class Permiso {
    private int idPermiso;
    private String nombrePermiso;
    private char estado;

    public Permiso() {
    }

    public Permiso(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
    }

    public Permiso(int idPermiso, String nombrePermiso, char estado) {
        this.idPermiso = idPermiso;
        this.nombrePermiso = nombrePermiso;
        this.estado = estado;
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getNombrePermiso() {
        return nombrePermiso;
    }

    public void setNombrePermiso(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
    
}
