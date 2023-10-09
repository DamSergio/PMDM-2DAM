package com.serbalced.radiogrouptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup r = findViewById(R.id.grupoOpcion);
        r.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ImageView img = findViewById(R.id.imgEscudo);
                if (checkedId == R.id.rbTalavera){
                    img.setImageResource(R.drawable.talavera);
                }
                if (checkedId == R.id.rbMadrid){
                    img.setImageResource(R.drawable.madrid);
                }
                if (checkedId == R.id.rbAtletic){
                    img.setImageResource(R.drawable.atletic);
                }
                if (checkedId == R.id.rbSevilla){
                    img.setImageResource(R.drawable.sevilla);
                }
            }
        });
    }
}