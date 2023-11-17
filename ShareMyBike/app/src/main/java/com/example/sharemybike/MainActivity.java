package com.example.sharemybike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtMail = findViewById(R.id.txtMail);
                String mail = txtMail.getText().toString();

                Intent intentMail = new Intent(Intent.ACTION_SEND);
                intentMail.setData(Uri.parse("mailto:"+mail));

                intentMail.putExtra(Intent.EXTRA_EMAIL, mail);
                intentMail.putExtra(Intent.EXTRA_SUBJECT, "Share My Bike");
                intentMail.putExtra(Intent.EXTRA_TEXT, "");

                intentMail.setType("message/rfc822");

                startActivity(intentMail);
            }
        });

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bikeActivity = new Intent(getApplicationContext(), BikeActivity.class);
                startActivity(bikeActivity);
            }
        });
    }
}