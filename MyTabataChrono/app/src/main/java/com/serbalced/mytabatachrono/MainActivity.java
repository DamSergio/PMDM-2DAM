package com.serbalced.mytabatachrono;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private int numSeries = 0;
    private int secWork = 0;
    private int secDesc = 0;
    private TextView txtCountDown;
    private TextView txtNumSeries;
    private TextView txtAccion;

    private CountDownTimer temporizadorDescanso;
    private CountDownTimer temporizadorTrabajo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        txtCountDown = findViewById(R.id.txtTemp);
        txtNumSeries = findViewById(R.id.txtSeriesRest);
        txtAccion = findViewById(R.id.txtAction);

        ImageButton btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(view -> {
            EditText txtSeries = findViewById(R.id.numSeries);
            this.numSeries = Integer.parseInt(txtSeries.getText().toString());

            EditText txtWorkSec = findViewById(R.id.trabajoSec);
            this.secWork = Integer.parseInt(txtWorkSec.getText().toString());

            EditText txtDescSec = findViewById(R.id.descansoSec);
            this.secDesc = Integer.parseInt(txtDescSec.getText().toString());
            
            constraintLayout.setBackgroundColor(Color.GREEN);
            txtNumSeries.setText("Series restantes: " + numSeries);

            temporizadorDescanso = new CountDownTimer(secDesc * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    txtCountDown.setText((millisUntilFinished / 1000) + "");
                }

                @Override
                public void onFinish() {
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.gong);
                    mp.start();
                    constraintLayout.setBackgroundColor(Color.GREEN);

                    if (numSeries > 1){
                        numSeries--;
                        txtAccion.setText("Trabaja");
                        txtNumSeries.setText("Series restantes: " + numSeries);
                        temporizadorTrabajo.start();
                    } else {
                        numSeries--;
                        txtNumSeries.setText("Series restantes: " + numSeries);
                        txtAccion.setText("Fin");
                    }
                }
            };

            temporizadorTrabajo = new CountDownTimer(secWork * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    txtCountDown.setText((millisUntilFinished / 1000) + "");
                }

                @Override
                public void onFinish() {
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.gong);
                    mp.start();
                    constraintLayout.setBackgroundColor(Color.RED);
                    txtAccion.setText("Descansa");
                    temporizadorDescanso.start();
                }
            };

            temporizadorTrabajo.start();
            txtAccion.setText("Trabaja");
        });
    }
}