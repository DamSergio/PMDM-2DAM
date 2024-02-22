package com.ilm.arkanoidsergioballesteros;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.ilm.arkanoidsergioballesteros.game.ActividadJuego;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView panel = findViewById(R.id.imgPanel);
        panel.startAnimation(AnimationUtils.loadAnimation(this, R.anim.panel_start));

        ImageView ball = findViewById(R.id.imgBall);
        ball.startAnimation(AnimationUtils.loadAnimation(this, R.anim.panel_start));

        Button btnPlay = findViewById(R.id.btnPlay);
        animateButton(btnPlay);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game = new Intent(getApplicationContext(), ActividadJuego.class);
                startActivity(game);
            }
        });
    }

    public void animateButton(Button btn) {
        AnimatorSet buttonAnimator = new AnimatorSet();

        ObjectAnimator translate=ObjectAnimator.ofFloat(btn,"translationY",-800,0);
        translate.setDuration(5000);

        ObjectAnimator fade = ObjectAnimator.ofFloat(btn, "alpha", 0f, 1f);
        fade.setDuration(8000);

        buttonAnimator.play(translate).with(fade);

        buttonAnimator.start();
    }
}