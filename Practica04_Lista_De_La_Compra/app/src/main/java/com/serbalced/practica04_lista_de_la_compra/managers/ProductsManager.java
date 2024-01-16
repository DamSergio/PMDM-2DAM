package com.serbalced.practica04_lista_de_la_compra.managers;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;

import com.serbalced.practica04_lista_de_la_compra.dto.Product;
import com.serbalced.practica04_lista_de_la_compra.ui.products_list.MyProductRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductsManager {
    public static ArrayList<Product> listAllProducts = new ArrayList<>();
    public static ArrayList<Product> productsToShow = new ArrayList<>();
    public static MyProductRecyclerViewAdapter adapter = null;

    public static void loadProductsFromList(ArrayList<Product> products){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (Product p : products){
                    productsToShow.add(p);
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public static void resetProductsAmount() {
        for (Product p : listAllProducts){
            p.amount = "";
        }
    }
}
