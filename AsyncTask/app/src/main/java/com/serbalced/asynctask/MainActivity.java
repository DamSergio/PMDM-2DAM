package com.serbalced.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Handler manejador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Paso 1: crear un servicio o componente no bloqueante
                ExecutorService executor = Executors.newSingleThreadExecutor();
                manejador = new Handler(Looper.getMainLooper());
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        //Paso 2: crear un  objeto HTTP y realizar la conexion
                        realizaConexion();
                    }
                });
            }
        });
    }

    public void realizaConexion(){
        ConnectivityManager conManager = (ConnectivityManager) getSystemService(
                getApplicationContext().CONNECTIVITY_SERVICE
        );
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            try {
                //establecer la conexion
                URL url = new URL("https://riberadeltajo.es/text.txt");
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

                conexion.setReadTimeout(10000); //le damos un segundo para leer los datos y si no aborta
                conexion.setConnectTimeout(5000); //le damos un segundo para conectar y si no aborta
                conexion.setRequestMethod("GET");
                conexion.setDoInput(true); //solicita entrada de datos

                conexion.connect();

                //si llegamos aqui es que estamos conectados y listos para recibir la respuesta
                if (conexion.getResponseCode() == 200){
                    String cadena = leer(conexion.getInputStream());
                    Log.d("recibido", cadena);

                    manejador.post(new Runnable() {
                        @Override
                        public void run() {
                            TextView txt = findViewById(R.id.textView);
                            txt.setText(cadena);
                        }
                    });
                }
                conexion.getInputStream().close();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String leer(InputStream is){
        //Paso 3: leer los datos de la url seleccionada
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        try {
            int i = is.read();
            while (i != -1){
                bo.write(i);
                i = is.read();
            }
        } catch (IOException e){
            e.printStackTrace();
            return "";
        }

        return bo.toString();
    }
}