package com.example.sharemybike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnLoc = findViewById(R.id.btnLoc);
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coord = "39.966537364132726, -4.807427984571561";

                Intent intentCoord = new Intent(Intent.ACTION_VIEW);
                intentCoord.setData(Uri.parse("geo:"+coord));

                Intent chooser = Intent.createChooser(intentCoord, "Elige el gestor de mapas");

                startActivity(chooser);
            }
        });

        ImageButton btnMail = findViewById(R.id.btnMail);
    }
}