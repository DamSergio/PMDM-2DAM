package com.serbalced.pruebaparcelable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String mensajeOriginal = "Te envio un contacto";
        int codigoMensaje = 1;
        int otrosValores[] = {2, 3, 4, 5, 6, 7, 8, 100};

        Contacto carlos = new Contacto(
                "Carlos",
                "Perez",
                25,
                "666666666",
                false
        );
        Contacto juana = new Contacto(
                "Juana",
                "Sanchez",
                27,
                "666666667",
                true
        );
        Contacto francisco = new Contacto(
                "Francisco",
                "Suarez",
                24,
                "666666665",
                true
        );

        francisco.addFamiliares(juana);
        francisco.addFamiliares(carlos);

        Button btn = findViewById(R.id.btnActivity);
        btn.setOnClickListener(view -> {
            Intent segundaActividad = new Intent(this, MainActivity2.class);
            segundaActividad.putExtra("mensaje", mensajeOriginal);
            segundaActividad.putExtra("codigo", codigoMensaje);
            segundaActividad.putExtra("otros_valores", otrosValores);
            segundaActividad.putExtra("contacto", francisco);

            startActivity(segundaActividad);
        });
    }
}