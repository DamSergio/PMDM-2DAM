package com.serbalced.tarea6elreproductordemedios.dto;

import android.media.MediaPlayer;

public class Reproductor {
    public String nombre;
    public String desc;
    public int tipo;
    public String uri;
    public String imagen;
    public MediaPlayer mp;

    public Reproductor(String nombre, String desc, int tipo, String uri, String imagen, MediaPlayer mp) {
        this.nombre = nombre;
        this.desc = desc;
        this.tipo = tipo;
        this.uri = uri;
        this.imagen = imagen;
        this.mp = mp;
    }

    public Reproductor() {
        this.nombre = "";
        this.desc = "";
        this.tipo = 0;
        this.imagen = "";
        this.mp = null;
    }
}
