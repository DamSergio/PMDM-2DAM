package com.serbalced.sergioballesteroscedenilla;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class AgregarPelicula extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private boolean photoTaken = false;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pelicula);

        Button btnPhoto = findViewById(R.id.btnPhoto);
        btnPhoto.setOnClickListener(v -> {
            Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(photo, REQUEST_IMAGE_CAPTURE);
        });

        ImageButton btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            TextView txtCod = findViewById(R.id.txtAddCod);
            TextView txtName = findViewById(R.id.txtAddName);
            TextView txtDir = findViewById(R.id.txtAddDir);
            TextView txtYear = findViewById(R.id.txtAddYear);

            Random r = new Random();
            Pelicula p;

            if (photoTaken){
                p = new Pelicula(
                        Integer.parseInt(txtCod.getText().toString()),
                        txtName.getText().toString(),
                        txtDir.getText().toString(),
                        Integer.parseInt(txtYear.getText().toString()),
                        image,
                        (r.nextFloat() * 10) / 2
                );
            } else {
                p = new Pelicula(
                        Integer.parseInt(txtCod.getText().toString()),
                        txtName.getText().toString(),
                        txtDir.getText().toString(),
                        Integer.parseInt(txtYear.getText().toString()),
                        R.drawable.sin_portada,
                        (r.nextFloat() * 10) / 2
                );
            }

            MainActivity.peliculas.add(p);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            image = (Bitmap) extras.get("data");
            ImageView img = findViewById(R.id.imgAddPort);
            img.setImageBitmap(image);

            photoTaken = true;
        }
    }
}