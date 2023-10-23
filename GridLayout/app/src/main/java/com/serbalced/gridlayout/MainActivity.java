package com.serbalced.gridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.gridlayout.widget.GridLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public int colors[] = new int[18];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout g = findViewById(R.id.gridBotones);
        g.setRowCount(6);
        g.setColumnCount(3);

        View.OnClickListener setWhite = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                b.setBackgroundColor(Color.WHITE);
            }
        };

        View.OnClickListener reset = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                for (int i = 0; i < g.getChildCount(); i++){
                    Button b = (Button) g.getChildAt(i);
                    b.setBackgroundColor(colors[i]);
                }
            }
        };

        Random r = new Random();
        for (int i = 0; i < 18; i++){
            Button b = new Button(this);
            colors[i] = Color.argb(128, r.nextInt(), r.nextInt(), r.nextInt());
            b.setBackgroundColor(colors[i]);
            if (i == 17){
                b.setText("RESET");
                b.setOnClickListener(reset);
            } else {
                b.setText("btn " + i);
                b.setOnClickListener(setWhite);
            }

            g.addView(b);
        }
    }
}