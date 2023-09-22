package com.serbalced.numerosprimos;

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

        Button btnCalc = findViewById(R.id.btnCalc);
        btnCalc.setOnClickListener(view -> {
            EditText num = findViewById(R.id.numPosicion);
            int pos = Integer.parseInt(num.getText().toString());
            int numero = 1;

            while (pos > 0){
                numero++;
                boolean noEsPrimo = false;
                for (int i = 2; i <= numero / 2; i++){
                    if (numero % i == 0){
                        noEsPrimo = true;
                        break;
                    }
                }

                if (!noEsPrimo){
                    pos--;
                }
            }

            TextView res = findViewById(R.id.resultado);
            res.setText(numero+"");
        });
    }
}