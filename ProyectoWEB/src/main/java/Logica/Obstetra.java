package Logica;

public class Obstetra {
    private int idObstetra;
    private String nombreObstetra;
    private char estado;

    public Obstetra() {
    }

 
    public Obstetra(int idObstetra, String nombreObstetra, char estado) {
        this.idObstetra = idObstetra;
        this.nombreObstetra = nombreObstetra;
        this.estado = estado;
    }

    public int getIdObstetra() {
        return idObstetra;
    }

    public void setIdObstetra(int idObstetra) {
        this.idObstetra = idObstetra;
    }

    public String getNombreObstetra() {
        return nombreObstetra;
    }

    public void setNombreObstetra(String nombreObstetra) {
        this.nombreObstetra = nombreObstetra;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
}

