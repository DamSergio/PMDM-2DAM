package com.serbalced.pruebaintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProvinciasActivity extends AppCompatActivity {
    public final int RESULTADO_SELECCION_PROVINCIA = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provincias);
        String provincias[] = new String[]{"Toledo", "Ciudad Real", "Cuenca", "Guadalajara", "Albacete"};
        ArrayAdapter<String> adapterProv = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                provincias
        );
        ListView listProv = findViewById(R.id.lstProv);
        listProv.setAdapter(adapterProv);

        listProv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent resultado = new Intent();
                resultado.putExtra("ciudad_elegida", provincias[position]);
                resultado.putExtra("posicion_ciudad_elegida", position);
                setResult(RESULTADO_SELECCION_PROVINCIA, resultado);
                finish();
            }
        });
    }
}