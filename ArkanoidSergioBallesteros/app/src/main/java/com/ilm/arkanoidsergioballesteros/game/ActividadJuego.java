package com.ilm.arkanoidsergioballesteros.game;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;


public class ActividadJuego extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Juego(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BucleJuego.JuegoEnEjecucion = false;
    }
}
