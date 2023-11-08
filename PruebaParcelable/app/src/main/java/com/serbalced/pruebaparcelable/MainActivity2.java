package com.serbalced.pruebaparcelable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent i = getIntent();
        String msg = i.getStringExtra("mensaje");
        int cod = i.getIntExtra("codigo", -1);
        int otrosValores[] = i.getIntArrayExtra("otros_valores");
        Contacto c = i.getParcelableExtra("contacto");

        TextView t = findViewById(R.id.txtRes);
        t.setText(c.toString());
    }
}