package com.example.betmatchplay.Models;

public class Puja {
    public String titulo, subtitulo, imageURL;

    public Puja(String titulo, String subtitulo, String imageURL) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.imageURL=imageURL;
    }

    public Puja(){}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getImageURL() { return imageURL; }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
