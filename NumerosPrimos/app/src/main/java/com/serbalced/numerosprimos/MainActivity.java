package com.serbalced.numerosprimos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Integer> numerosPrimos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCalc = findViewById(R.id.btnCalc);
        btnCalc.setOnClickListener(view -> {
            long tiempoInicio = System.currentTimeMillis();
            EditText num = findViewById(R.id.numPosicion);
            int pos = Integer.parseInt(num.getText().toString());
            int test = 2;

            while (pos > 0){
                if (esPrimoConCriba(test)){
                    pos--;
                }
                test++;
            }

            long tiempoFin = System.currentTimeMillis();
            TextView res = findViewById(R.id.resultado);
            res.setText((test-1)+" es el numero primo, y ha tardado: " + (tiempoFin - tiempoInicio));
        });
    }

    public boolean esPrimo(int x){
        for (int i = 2; i <= x / 2; i++){
            if (x % i == 0){
                return false;
            }
        }
        return true;
    }

    public boolean esPrimoConCriba(int x){
        for (Integer i : this.numerosPrimos){
            if (x % i == 0){
                return false;
            }
        }

        this.numerosPrimos.add(x);
        return true;
    }
}