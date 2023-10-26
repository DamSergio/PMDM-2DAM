package com.serbalced.examensorpresaequipos;

public class Equipo {
    private String nombre;
    private int img;
    private int puntos;

    public Equipo(String nombre, int img, int puntos) {
        this.nombre = nombre;
        this.img = img;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
