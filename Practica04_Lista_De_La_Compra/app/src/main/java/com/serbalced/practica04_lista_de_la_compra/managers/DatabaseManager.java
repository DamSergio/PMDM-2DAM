package com.serbalced.practica04_lista_de_la_compra.managers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.serbalced.practica04_lista_de_la_compra.dto.Product;
import com.serbalced.practica04_lista_de_la_compra.dto.ShoppingList;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseManager {
    public static SQLiteDatabase db;

    public static void loadAllProducts() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Cursor products = DatabaseManager.db.rawQuery("SELECT Id, Name, Description, Price FROM products ORDER BY TimesBought", null);
                if (products.getCount() != 0){
                    while (products.moveToNext()){
                        ProductsManager.listAllProducts.add(new Product(
                                products.getInt(0),
                                products.getString(1),
                                products.getString(2),
                                products.getDouble(3)
                        ));

                        if (ProductsManager.adapter != null){
                            ProductsManager.adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    public static void insertProduct(int iD, String name, String description, double price) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues values = new ContentValues();
                values.put("Id", iD);
                values.put("Name", name);
                values.put("Description", description);
                values.put("Price", price);

                DatabaseManager.db.insert(
                        "products",
                        null,
                        values
                );
            }
        });
    }

    public static void insertProductWithImage(Product p){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Cursor cursorId = DatabaseManager.db.rawQuery("SELECT MAX(Id) FROM products", null);
                if (cursorId.moveToFirst()){
                    int id = Integer.parseInt(cursorId.getString(0)) + 1;

                    p.iD = id;
                    ProductsManager.listAllProducts.add(p);

                    ContentValues product = new ContentValues();
                    product.put("Id", id);
                    product.put("Name", p.name);
                    product.put("Description", p.description);
                    product.put("Price", p.price);

                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    p.image.compress(
                            Bitmap.CompressFormat.PNG,
                            0,
                            os
                    );
                    byte imageBLOB[] = os.toByteArray();

                    product.put("Image", imageBLOB);

                    DatabaseManager.db.insert(
                            "products",
                            null,
                            product
                    );
                }
            }
        });
    }

    public static void insertShoppingList() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ContentValues shoppingList = new ContentValues();
                shoppingList.put("Id", ShoppingListsManager.newList.iD);
                shoppingList.put("Title", ShoppingListsManager.newList.name);
                shoppingList.put("Date", ShoppingListsManager.newList.date);

                DatabaseManager.db.insert(
                        "ShoppingLists",
                        null,
                        shoppingList
                );

                ShoppingListsManager.lists.add(ShoppingListsManager.newList);
                ShoppingListsManager.listsNames.add(ShoppingListsManager.newList.name);
                //ShoppingListsManager.adapter.notifyDataSetChanged();

                for (Product p : ShoppingListsManager.newList.products){
                    ContentValues productsLists = new ContentValues();
                    productsLists.put("ListId", ShoppingListsManager.newList.iD);
                    productsLists.put("ProductId", p.iD);
                    productsLists.put("Amount", p.amount);

                    DatabaseManager.db.insert(
                            "productsList",
                            null,
                            productsLists
                    );
                }
            }
        });
    }

    public static void loadShoppingLists() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Cursor shoppingLists = DatabaseManager.db.rawQuery("SELECT * FROM shoppingLists", null);
                if (shoppingLists.getCount() != 0){
                    while (shoppingLists.moveToNext()){
                        ShoppingList sl = new ShoppingList();
                        sl.iD = Integer.parseInt(shoppingLists.getString(0));
                        sl.name = shoppingLists.getString(1);
                        sl.date = shoppingLists.getString(2);

                        ShoppingListsManager.lists.add(sl);
                        //ShoppingListsManager.adapter.notifyDataSetChanged();
                        ShoppingListsManager.listsNames.add(sl.name);
                    }
                }

                for (ShoppingList s : ShoppingListsManager.lists){
                    Cursor products = DatabaseManager.db.rawQuery("SELECT Id, Name, Price, Description, Amount FROM products p JOIN  productsList l ON p.Id = l.ProductId WHERE ListId = " + s.iD + "", null);
                    if (products.getCount() != 0){
                        while (products.moveToNext()) {
                            Product p = new Product();
                            p.iD = Integer.parseInt(products.getString(0));
                            p.name = products.getString(1);
                            p.price = Double.parseDouble(products.getString(2));
                            p.description = products.getString(3);
                            p.amount = products.getString(4);

                            s.products.add(p);
                        }
                    }
                }
            }
        });
    }

    public static void updateList(int listId, ArrayList<Product> products) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<Product> pro = new ArrayList<>();
                for (Product p : products){
                    if (p.amount.equals("")){
                        DatabaseManager.db.delete(
                                "productsList",
                                "ListId = ? AND ProductId = ?",
                                new String[]{listId + "", p.iD + ""}
                        );

                        continue;
                    }

                    pro.add(p);

                    ContentValues amounts = new ContentValues();
                    amounts.put("Amount", p.amount);

                    DatabaseManager.db.update(
                            "productsList",
                            amounts,
                            "ListId = ? AND ProductId = ?",
                            new String[]{listId + "", p.iD + ""}
                    );
                }

                ShoppingListsManager.listToShow.products = pro;
                if (pro.isEmpty()) {
                    ShoppingListsManager.lists.remove(ShoppingListsManager.listToShow);
                    ShoppingListsManager.listsNames.remove(ShoppingListsManager.listToShow.name);

                    DatabaseManager.db.delete(
                            "shoppingLists",
                            "Id = ?",
                            new String[]{ShoppingListsManager.listToShow.iD + ""}
                    );
                }
            }
        });
    }
}
