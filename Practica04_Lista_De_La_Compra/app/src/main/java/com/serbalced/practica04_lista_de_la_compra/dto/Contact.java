package com.serbalced.practica04_lista_de_la_compra.dto;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Contact {
    public long id;
    public String name;
    public ArrayList<String> numbers;
    public Bitmap image;

    public Contact() {
        this.id = -1;
        this.name = "";
        this.numbers = new ArrayList<>();
        this.image = null;
    }
}
