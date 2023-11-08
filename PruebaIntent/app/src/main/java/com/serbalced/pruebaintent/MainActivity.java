package com.serbalced.pruebaintent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final int CAPTURA_FOTO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("serbalced", "MainActivity.onCreate()");

        //intent explicito
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrancaActividadLogin();
            }
        });

        //intent implicito

        ImageButton btnCamara = findViewById(R.id.btnCamara);
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(foto, CAPTURA_FOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==  CAPTURA_FOTO){
            Bitmap miImagen = (Bitmap) data.getExtras().get("data");
            ImageView imgCam = findViewById(R.id.imgCam);
            imgCam.setImageBitmap(miImagen);
        }
    }

    public void arrancaActividadLogin(){
        //1º crear un objeto intent con la clase a instanciar
        Intent intentLogin = new Intent(this, LoginActivity.class);

        //2º arrancar la actividad
        startActivity(intentLogin);
    }
}