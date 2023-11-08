package com.serbalced.casopracticointentimplicitos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.serbalced.casopracticointentimplicitos.databinding.ActivityMainBinding;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Quien desperto a mi actividad
        //dos opciones -> La desperto un launcher, alguien la pulso
        //                La desperto un intent de tipo SEND, con datos plain/text
        Intent i = getIntent();
        String accion = i.getAction();
        if (accion.equals(Intent.ACTION_SEND)){
            String msg = i.getStringExtra(Intent.EXTRA_TEXT);
            binding.txtIntent.setText(msg);


            Uri uri = i.getParcelableExtra(Intent.EXTRA_STREAM);
            if (uri != null){
                InputStream is = null;
                try {
                    is = getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                binding.imageView.setImageBitmap(BitmapFactory.decodeStream(is));
            }
        }

        binding.btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = binding.txtUrl.getText().toString();
                Intent intentWeb = new Intent(Intent.ACTION_VIEW);
                intentWeb.setData(Uri.parse(url));

                Intent chooser = Intent.createChooser(intentWeb, "Elige el navegador");

                startActivity(chooser);
            }
        });

        binding.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coord = binding.txtLat.getText().toString();
                coord += "," + binding.txtLong.getText().toString();

                Intent intentCoord = new Intent(Intent.ACTION_VIEW);
                intentCoord.setData(Uri.parse("geo:"+coord));

                Intent chooser = Intent.createChooser(intentCoord, "Elige el gestor de mapas");

                startActivity(chooser);
            }
        });

        binding.btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = binding.txtMail.getText().toString();

                Intent intentMail = new Intent(Intent.ACTION_SEND);
                intentMail.setData(Uri.parse("mailto:"+mail));

                String array_destinatarios[] = new String[]{mail, "aliciaramos@riberadeltajo.es"};
                intentMail.putExtra(Intent.EXTRA_EMAIL, array_destinatarios);
                intentMail.putExtra(Intent.EXTRA_SUBJECT, "MENSAJE AUTOMATICO");
                intentMail.putExtra(Intent.EXTRA_TEXT, "BUENO DIAS ESTE MENSAJE HA SIDO AUTO GENERADO");

                intentMail.setType("message/rfc822");

                startActivity(intentMail);
            }
        });
    }
}