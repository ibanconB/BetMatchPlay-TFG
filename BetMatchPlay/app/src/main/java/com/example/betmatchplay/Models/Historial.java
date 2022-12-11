package com.example.betmatchplay.Models;

public class Historial {
    public String nombrePuja,fechaHora,correoUsuario,precioComprado;

    public Historial(String nombrePuja, String fechaHora, String correoUsuario, String precioComprado) {
        this.nombrePuja = nombrePuja;
        this.fechaHora = fechaHora;
        this.correoUsuario = correoUsuario;
        this.precioComprado = precioComprado;
    }

    public Historial() {
    }

    public String getNombrePuja() {
        return nombrePuja;
    }

    public void setNombrePuja(String nombrePuja) {
        this.nombrePuja = nombrePuja;
    }

    public String getfechaHora() {
        return fechaHora;
    }

    public void setfechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getPrecioComprado() {
        return precioComprado;
    }

    public void setPrecioComprado(String precioComprado) {
        this.precioComprado = precioComprado;
    }
}
