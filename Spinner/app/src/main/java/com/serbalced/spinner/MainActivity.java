package com.serbalced.spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String misAlmunos[] = {"Elia", "Rodri", "Carlos", "Marcos", "Francisco",
                "Andrea", "Sergio", "Javier", "Paco", "Pepe", "Pipo"};

        Spinner miSpinner = findViewById(R.id.spinner);
        ArrayAdapter<String> miAdaptador = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                misAlmunos
        );
        miAdaptador.setDropDownViewResource(R.layout.mi_fila);
        miSpinner.setAdapter(miAdaptador);

        miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nombre = misAlmunos[position];
                int nota = 0;
                if (nombre.startsWith("P")){
                    nota = 10;
                }

                Toast.makeText(
                        getApplicationContext(),
                        "" + nota,
                        Toast.LENGTH_LONG
                ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}