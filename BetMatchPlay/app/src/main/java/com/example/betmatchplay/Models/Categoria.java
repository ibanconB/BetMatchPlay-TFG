package com.example.betmatchplay.Models;

public class Categoria {
    public String nombreCategoria, imageURL;

    public Categoria(String nombreCategoria, String imageURL) {
        this.nombreCategoria = nombreCategoria;
        this.imageURL = imageURL;
    }

    public Categoria(){}

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getURLimage() {
        return imageURL;
    }

    public void setURLimage(String URLimage) {
        this.imageURL = imageURL;
    }
}
