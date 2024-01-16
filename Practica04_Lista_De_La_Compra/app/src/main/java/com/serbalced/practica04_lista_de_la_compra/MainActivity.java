package com.serbalced.practica04_lista_de_la_compra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.serbalced.practica04_lista_de_la_compra.databinding.ActivityMainBinding;
import com.serbalced.practica04_lista_de_la_compra.dto.Product;
import com.serbalced.practica04_lista_de_la_compra.managers.ContactsManager;
import com.serbalced.practica04_lista_de_la_compra.managers.DatabaseManager;
import com.serbalced.practica04_lista_de_la_compra.managers.ProductsManager;
import com.serbalced.practica04_lista_de_la_compra.managers.ShoppingListsManager;
import com.serbalced.practica04_lista_de_la_compra.ui.products_list.MyProductRecyclerViewAdapter;
import com.serbalced.practica04_lista_de_la_compra.ui.show_lists.ShoppingListsDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Handler handler;
    private final String CREATE_SHOPPING_LISTS_TABLE = "CREATE TABLE IF NOT EXISTS shoppingLists(Id INTEGER PRIMARY KEY, Title TEXT, Date Date)";
    private final String CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS products(Id INTEGER PRIMARY KEY, Name TEXT, Description TEXT, Image BLOB, Price NUMBER, TimesBought INTEGER DEFAULT 0)";
    private final String CREATE_PRODUCTS_LIST_TABLE = "CREATE TABLE IF NOT EXISTS productsList(ListId INTEGER, ProductId INTEGER, Amount TEXT, PRIMARY KEY (ListId, ProductId), FOREIGN KEY (ListId) REFERENCES shoppingLists(Id), FOREIGN KEY (ProductId) REFERENCES products(Id))";
    private final String JSON_URL = "https://fp.cloud.riberadeltajo.es/listacompra/listaproductos.json";
    private final String IMAGES_URL = "https://fp.cloud.riberadeltajo.es/listacompra/images/";
    private final int CONNECTION_OK = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_new_list, R.id.nav_show_lists, R.id.nav_new_product)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        innitDatabase();
        
        ContactsManager.context = getApplicationContext();
        ProductsManager.adapter = new MyProductRecyclerViewAdapter(ProductsManager.productsToShow);
    }

    public void innitDatabase(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Trabajo en Background aqu�
                DatabaseManager.db = openOrCreateDatabase("ShoppingLists", Context.MODE_PRIVATE, null);

                DatabaseManager.db.execSQL(CREATE_SHOPPING_LISTS_TABLE);
                DatabaseManager.db.execSQL(CREATE_PRODUCTS_TABLE);
                DatabaseManager.db.execSQL(CREATE_PRODUCTS_LIST_TABLE);

                DatabaseManager.loadShoppingLists();

                Cursor producsCursor = DatabaseManager.db.rawQuery("SELECT * FROM products", null);
                if (producsCursor.getCount() <= 0){
                    DownloadJSON();
                } else {
                    DatabaseManager.loadAllProducts();
                }
            }
        });
    }

    public void DownloadJSON() {
        ConnectivityManager conManager = (ConnectivityManager) getSystemService(
                getApplicationContext().CONNECTIVITY_SERVICE
        );
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        if (conManager == null || !networkInfo.isConnected()){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.connectivity_error,
                            Toast.LENGTH_LONG
                    ).show();
                }
            });

            return;
        }

        try {
            URL url = new URL(JSON_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000); // Tiempo para leer los datos antes de abortar
            connection.setConnectTimeout(3000); // Tiempo para conectrse antes de abortar
            connection.setRequestMethod("GET"); // Metodo de conexion, obtener datos
            connection.setDoInput(true); // Solicita la entrada de datos

            connection.connect();
            if (connection.getResponseCode() == CONNECTION_OK){
                readJSON(connection.getInputStream());
            }

            connection.getInputStream().close();
        } catch (MalformedURLException e){
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readJSON(InputStream is) {
        String json;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();

            while((json = reader.readLine()) !=  null){
                stringBuilder.append(json).append("\n");
            }

            JSONArray jsonArray = new JSONObject(stringBuilder.toString()).getJSONArray("productos");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject product = jsonArray.getJSONObject(i);

                String name = product.getString("nombre");
                String description = product.getString("descripcion");
                String imageName = product.getString("imagen");
                double price = Double.parseDouble(product.getString("precio"));

                Log.d("serbalced", "imagen: " + imageName);
                DatabaseManager.insertProduct(i, name, description, price); // Inserta en la base de datos
                getImage(imageName, i);
            }

            DatabaseManager.loadAllProducts();
        } catch (IOException e){
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void getImage(String imageName, int iD) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(IMAGES_URL + imageName);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setReadTimeout(5000); // Tiempo para leer los datos antes de abortar
                    connection.setConnectTimeout(3000); // Tiempo para conectrse antes de abortar
                    connection.setRequestMethod("GET"); // Metodo de conexion, obtener datos
                    connection.setDoInput(true); // Solicita la entrada de datos

                    if (connection.getResponseCode() == CONNECTION_OK){
                        Bitmap image = BitmapFactory.decodeStream(connection.getInputStream());
                        byte imageBLOB[] = getBitmapAsByteArray(image);

                        ContentValues values = new ContentValues();
                        values.put("Image", imageBLOB);

                        int updatedRows = DatabaseManager.db.update(
                                "products",
                                values,
                                "Id = ?",
                                new String[] {iD + ""}
                        );
                        Log.d("serbalced", updatedRows + "");
                        Log.d("serbalced", imageName);
//                        DatabaseManager.db.execSQL("UPDATE products SET Image = " + imageBLOB + " WHERE Id = " + iD + " ");
                    }

                    connection.getInputStream().close();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (ProtocolException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public byte[] getBitmapAsByteArray(Bitmap bitmap){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(
                Bitmap.CompressFormat.PNG,
                0,
                os
        );

        return os.toByteArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}