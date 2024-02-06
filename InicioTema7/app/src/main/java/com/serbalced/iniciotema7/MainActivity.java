package com.serbalced.iniciotema7;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    AnimationDrawable animacionRobot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.meteorito_rota));

        ImageView imgRobot = findViewById(R.id.imageView2);
        imgRobot.setImageResource(R.drawable.animacion_robot);
        animacionRobot = (AnimationDrawable) imgRobot.getDrawable();
        imgRobot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                animacionRobot.start();

                return true;
            }
        });

        Button btn = findViewById(R.id.btn);
        animarBoton(btn);
    }

    public void animarBoton(Button botonJuego){
        AnimatorSet animadorBoton = new AnimatorSet();
        //1ª animación, trasladar desde la izquierda (800 pixeles menos hasta la posición
        //inicial (0)
        ObjectAnimator trasladar=ObjectAnimator.ofFloat(botonJuego,"translationX",-800,0);
        trasladar.setDuration(5000); //duración 5 segundos

        ObjectAnimator trasladarY=ObjectAnimator.ofFloat(botonJuego,"translationY",-800,0);
        trasladarY.setDuration(5000);
        //2ª Animación fade in de 8 segundos
        ObjectAnimator fade = ObjectAnimator.ofFloat(botonJuego, "alpha", 0f, 1f);
        fade.setDuration(8000);
        //se visualizan las dos animaciones a la vez
        animadorBoton.play(trasladar).with(fade).with(trasladarY);
        //comenzar animación
        animadorBoton.start();
    }
}