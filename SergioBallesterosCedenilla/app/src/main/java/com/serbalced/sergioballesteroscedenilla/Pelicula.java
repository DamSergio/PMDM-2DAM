package com.serbalced.sergioballesteroscedenilla;

import android.content.Context;
import android.graphics.Bitmap;

public class Pelicula {
    private int codigo; //entero que identifica la película
    private String nombre; //nombre de la película
    private String director; //nombre del director
    private int año; //año de lanzamiento
    private int imagen; //referencia a un dibujable
    private Bitmap bmp; //referencia a un bitmap
    private float rating; //estrellas

    public Pelicula(int codigo, String nombre, String director, int año, int imagen, Bitmap bmp, float rating) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.director = director;
        this.año = año;
        this.imagen = imagen;
        this.bmp = bmp;
        this.rating = rating;
    }

    public Pelicula(int codigo, String nombre, String director, int año, int imagen, float rating) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.director = director;
        this.año = año;
        this.imagen = imagen;
        this.bmp = null;
        this.rating = rating;
    }

    private void setPortada() {
        this.imagen = R.drawable.sin_portada;
    }

    public Pelicula(int codigo, String nombre, String director, int año, Bitmap bmp, float rating) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.director = director;
        this.año = año;
        this.imagen = 0;
        this.bmp = bmp;
        this.rating = rating;
    }

    public Pelicula() {
        this.codigo = 0;
        this.nombre = "";
        this.director = "";
        this.año = 0;
        this.imagen = 0;
        this.bmp = null;
        this.rating = 0;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
