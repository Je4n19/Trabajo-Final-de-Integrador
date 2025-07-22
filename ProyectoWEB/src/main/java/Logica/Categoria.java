
package Logica;

public class Categoria {
     private int idCategoria;
    private String nombreCategoria;
    private String Descripcion;
    private char estado;

    public Categoria(int idCategoria, String nombreCategoria, String Descripcion, char estado) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.Descripcion = Descripcion;
        this.estado = estado;
    }

    public Categoria() {
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }


}
