package com.serbalced.practica04_lista_de_la_compra.dto;

import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;

import com.serbalced.practica04_lista_de_la_compra.managers.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoppingList {
    public int iD;
    public String name;
    public String date;
    public ArrayList<Product> products;

    public ShoppingList(int iD, String name, ArrayList<Product> products) {
        this.iD = iD;
        this.name = name;
        this.products = products;
    }

    public ShoppingList() {
        getNewID();
        this.name = "";
        this.products = new ArrayList<>();
        Date today = new Date();
        this.date = new SimpleDateFormat("dd/MM/yy").format(today);
    }

    private void getNewID() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Cursor idCursor = DatabaseManager.db.rawQuery("SELECT COALESCE(MAX(Id), -1) + 1 FROM shoppingLists", null);
                if (idCursor.moveToFirst()) {
                    iD = Integer.parseInt(idCursor.getString(0));
                }
            }
        });
    }
}
