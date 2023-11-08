package com.serbalced.pruebaintent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> miLanzadorSeleccionaActivity; //CREARLO FUERA SIEMPRE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        miLanzadorSeleccionaActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        //Aqui me devulven los resultados
                        TextView txtSel = findViewById(R.id.txtSel);
                        txtSel.setText("La ciudad seleccionada es: " + o.getData().getExtras().get("ciudad_elegida"));
                    }
                }
        );

        Button btnProv = findViewById(R.id.btnProv);
        btnProv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrancarProv();
            }
        });
    }

    public void arrancarProv(){
        Intent intentProv = new Intent(getApplicationContext(), ProvinciasActivity.class);
        miLanzadorSeleccionaActivity.launch(intentProv);
    }
}