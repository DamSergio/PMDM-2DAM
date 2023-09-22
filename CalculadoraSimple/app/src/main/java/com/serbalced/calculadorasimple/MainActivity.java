package com.serbalced.calculadorasimple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSuma = findViewById(R.id.btnSuma);
        btnSuma.setOnClickListener(view -> {
            EditText num1 = findViewById(R.id.num1);
            EditText num2 = findViewById(R.id.num2);
            int op1 = Integer.parseInt(num1.getText().toString());
            int op2 = Integer.parseInt(num2.getText().toString());
            int suma = op1 + op2;

            TextView textResultado = findViewById(R.id.resultado);
            textResultado.setText(suma+"");
        });
    }
}