package com.serbalced.practica04_lista_de_la_compra.managers;

import android.widget.ArrayAdapter;

import com.serbalced.practica04_lista_de_la_compra.dto.Product;
import com.serbalced.practica04_lista_de_la_compra.dto.ShoppingList;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoppingListsManager {
    public static ArrayList<ShoppingList> lists = new ArrayList<>();
    public static ShoppingList newList;
    public static ShoppingList listToShow;
    public static ArrayList<String> listsNames = new ArrayList<>();
    public static ArrayAdapter<String> adapter;

    public static void addProductsToList(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (Product p : ProductsManager.listAllProducts){
                    if (p.amount.equals("")){
                        continue;
                    }

                    Product pro = new Product();
                    pro.iD = p.iD;
                    pro.name = p.name;
                    pro.description = p.description;
                    pro.price = p.price;
                    pro.amount = p.amount;

                    ShoppingListsManager.newList.products.add(pro);
                    DatabaseManager.db.execSQL("UPDATE products SET TimesBought = TimesBought + ? WHERE Id = ?", new Object[]{1, p.iD});
                }

                DatabaseManager.insertShoppingList();
                ProductsManager.resetProductsAmount();
            }
        });
    }
}
