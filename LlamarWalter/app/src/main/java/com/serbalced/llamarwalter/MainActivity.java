package com.serbalced.llamarwalter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private boolean onCall = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(view -> {
            ImageView walter = findViewById(R.id.imgWalter);
            if (onCall){
                walter.setImageResource(R.drawable.walter01);
                onCall = false;
                btnCall.setBackground(getDrawable(R.drawable.call));
            } else {
                walter.setImageResource(R.drawable.walter11);
                onCall = true;
                btnCall.setBackground(getDrawable(R.drawable.call_off));
            }
        });
    }
}