package com.serbalced.autocomplete;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String paises[] = {"Albania", "Alemania", "Andorra", "Arabia", "etc", "España", "Eslovaquia", "Eslovenia", "Etiopia"};
        ArrayAdapter<String> adapterPaises = new ArrayAdapter<String>(
                this,
                R.layout.autocompleta_paises,
                paises
        );
        AutoCompleteTextView actvEdPais = findViewById(R.id.actvEdPais);
        actvEdPais.setAdapter(adapterPaises);
        actvEdPais.setThreshold(1); //por defecto 2

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button) v;
                Toast.makeText(getApplicationContext(), btn.getText().toString() + " - " + btn.getId(), Toast.LENGTH_LONG).show();
            }
        };

        LinearLayout l = findViewById(R.id.lLayoutVertical);
        for (int i = 0; i < 17; i++){
            Button b = new Button(this);
            b.setText("Boton " + i);
            b.setId(View.generateViewId()); //NO ES NECESARIO PARA ESTE EJEMPLO
            b.setOnClickListener(listener);

            l.addView(b);
        }

        for (int i = 0; i < l.getChildCount(); i++){
            Button b = (Button) l.getChildAt(i);
            b.setBackgroundColor(Color.GREEN);
        }
    }
}