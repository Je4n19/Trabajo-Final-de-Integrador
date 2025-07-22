package Logica;

import java.io.Serializable;

public class Paciente implements Serializable {
    private int idPaciente;
    private String nombre;
    private String apellido;
    private int edad;
    private Obstetra obstetra; 
    private char estado;

    public Paciente() {
    }

    public Paciente(int idPaciente, String nombre, String apellido, int edad, Obstetra obstetra, char estado) {
        this.idPaciente = idPaciente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.obstetra = obstetra;
        this.estado = estado;
    }


    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Obstetra getObstetra() {
        return obstetra;
    }

    public void setObstetra(Obstetra obstetra) {
        this.obstetra = obstetra;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
}
