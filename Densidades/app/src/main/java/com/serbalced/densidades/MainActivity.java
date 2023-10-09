package com.serbalced.densidades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView img = findViewById(R.id.imageView);
        img.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "dimensiones: " + img.getHeight() + " x " + img.getWidth(), Toast.LENGTH_LONG).show();
        });
    }
}