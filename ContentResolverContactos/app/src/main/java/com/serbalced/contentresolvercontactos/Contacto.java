package com.serbalced.contentresolvercontactos;

import android.graphics.Bitmap;

public class Contacto {
    public String nombre;
    public int tiene_telefono;
    public long id;

    public Contacto(String nombre, int tiene_telefono, long id) {
        this.nombre = nombre;
        this.tiene_telefono = tiene_telefono;
        this.id = id;
    }
}
