package com.serbalced.sergioballesteroscedenilla;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static final int PELICULA_ADD = 1;
    static ArrayList<Pelicula> peliculas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random r = new Random();

        MainActivity.peliculas.add(new Pelicula(1, "Gremlins", "Joe Dante", 1984, R.drawable.gremlins, (r.nextFloat() * 10) / 2));
        MainActivity.peliculas.add(new Pelicula(2, "Dune", "Denis Villenueve", 2021, R.drawable.dune, (r.nextFloat() * 10) / 2));
        MainActivity.peliculas.add(new Pelicula(3, "El Marciano", "Ridley Scott", 2015, R.drawable.elmarciano, (r.nextFloat() * 10) / 2));
        MainActivity.peliculas.add(new Pelicula(4, "Encuentros en la tercera fase", "Steven Spielberg",1978, R.drawable.encuentro_tercera_fase, (r.nextFloat() * 10) / 2));
        MainActivity.peliculas.add(new Pelicula(5, "Cazafantasmas", "Ivan Reitman", 1984, R.drawable.cazafantasmas, (r.nextFloat() * 10) / 2));
        MainActivity.peliculas.add(new Pelicula(6, "Indiana Jones", "Steven Spielberg", 1984, R.drawable.indiana_jones, (r.nextFloat() * 10) / 2));

        Button btnPeli = findViewById(R.id.btnPeli);
        btnPeli.setOnClickListener(v -> {
            Intent peli = new Intent(this, AgregarPelicula.class);
            startActivityForResult(peli, PELICULA_ADD);
        });
    }

    public float [][] getRandomRatings (int numPeliculas){
        int random = (int) (1 + Math.random() * 100);
        float matriz[][] = new float[numPeliculas][random];
        Random r = new Random();
        for (int i = 0; i < numPeliculas; i++){
            for (int j = 0; j < random; j++){
                matriz[i][j] = (r.nextFloat() * 10) / 2;
            }
        }
        return matriz;
    }

    public void notificacion(){
        NotificationCompat.Builder constructorNotif = new
                NotificationCompat.Builder(this,"mi_canal");
        constructorNotif.setSmallIcon(android.R.drawable.ic_dialog_alert);
        constructorNotif.setContentTitle("pelis");
        constructorNotif.setContentText("ratings actualizados");

        NotificationManager notificador =
                (NotificationManager)
                        getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel("mi_canal",
                    "título de mi canal",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificador.createNotificationChannel(channel);
        }
        notificador.notify(1, constructorNotif.build());
    }

    public void recalcularRatings(ArrayList<Pelicula> listaPeliculas){
        float matriz[][] = getRandomRatings(listaPeliculas.size());
        for (int i = 0; i < listaPeliculas.size(); i++){
            float media = 0;
            for (int j = 0; j < matriz[0].length; j++){
                media += matriz[i][j];
            }
            media /= matriz[0].length;
            listaPeliculas.get(i).setRating(media);
        }

        RecyclerView rec = findViewById(R.id.list);
        rec.setAdapter(new MyPeliculaRecyclerViewAdapter(MainActivity.peliculas));

        if (ContextCompat.checkSelfPermission(this, "POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{"POST_NOTIFICATIONS"},
                    1
            );
        } else {
            notificacion();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnReload){
            recalcularRatings(MainActivity.peliculas);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RecyclerView rec = findViewById(R.id.list);
        rec.setAdapter(new MyPeliculaRecyclerViewAdapter(MainActivity.peliculas));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            notificacion();
        }
    }
}