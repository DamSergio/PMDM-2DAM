package com.serbalced.practica04_lista_de_la_compra.dto;

import android.graphics.Bitmap;

public class Product {
    public int iD;
    public String name;
    public double price;
    public Bitmap image;
    public String description;
    public String amount;

    public Product() {
        this.iD = 0;
        this.name = "";
        this.price = 0;
        this.description = "";
        this.image = null;
        this.amount = "";
    }

    public Product(int iD, String name, String description, double price) {
        this.iD = iD;
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = "";
    }
}
