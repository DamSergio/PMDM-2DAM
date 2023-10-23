package com.serbalced.onrespuesta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DialogoSexo.OnRespuestaSexo {
    public boolean respuestaUsuario;

    public boolean isRespuestaUsuario() {
        return respuestaUsuario;
    }

    public void setRespuestaUsuario(boolean respuestaUsuario) {
        this.respuestaUsuario = respuestaUsuario;
        Toast.makeText(this, "La respuesta es " + respuestaUsuario, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSacarDialogo = findViewById(R.id.btnSacarDialogo);
        btnSacarDialogo.setOnClickListener(view -> {
            DialogoSexo miDialogo = new DialogoSexo();
            miDialogo.show(getSupportFragmentManager(), "Dialogo Sexo");
        });
    }

    @Override
    public void onRespuesta(Boolean respuesta) {
        setRespuestaUsuario(respuesta);
    }
}