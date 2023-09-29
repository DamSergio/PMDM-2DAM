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
    private ConstraintLayout constraintLayout;
    private final int COUNT_DOWN_INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtCountDown = findViewById(R.id.txtTemp);
        txtNumSeries = findViewById(R.id.txtSeriesRest);
        txtAccion = findViewById(R.id.txtAction);

        constraintLayout = findViewById(R.id.constraintLayout);

        ImageButton btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(view -> {
            initComponents();
            initWorkTemp(secWork);
            initRestTemp(secDesc);

            temporizadorTrabajo.start();
        });
    }

    public void initComponents(){
        EditText txtSeries = findViewById(R.id.numSeries);
        try {
            this.numSeries = Integer.parseInt(txtSeries.getText().toString());
        } catch (NumberFormatException e){
            this.numSeries = 1;
            txtSeries.setText("1");
        }

        EditText txtWorkSec = findViewById(R.id.trabajoSec);
        try {
            this.secWork = Integer.parseInt(txtWorkSec.getText().toString());
        } catch (NumberFormatException e){
            this.secWork = 1;
            txtWorkSec.setText("1");
        }

        EditText txtDescSec = findViewById(R.id.descansoSec);
        try {
            this.secDesc = Integer.parseInt(txtDescSec.getText().toString());
        } catch (NumberFormatException e){
            this.secDesc = 1;
            txtDescSec.setText("1");
        }

        constraintLayout.setBackgroundColor(Color.GREEN);
        txtNumSeries.setText("Series restantes: " + numSeries);
        txtAccion.setText("Trabaja");
    }

    public void initWorkTemp(int secs){
        int countDownTimeMiliSec = secs * 1000;
        temporizadorTrabajo = new CountDownTimer(countDownTimeMiliSec, COUNT_DOWN_INTERVAL) {
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
    }

    public void initRestTemp(int secs){
        int countDownTimeMiliSec = secs * 1000;
        temporizadorDescanso = new CountDownTimer(countDownTimeMiliSec, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtCountDown.setText((millisUntilFinished / 1000) + "");
            }

            @Override
            public void onFinish() {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.gong);
                mp.start();
                constraintLayout.setBackgroundColor(Color.WHITE);
                numSeries--;
                if (numSeries > 0){
                    txtAccion.setText("Trabaja");
                    temporizadorTrabajo.start();
                } else {
                    txtAccion.setText("Fin");
                }

                txtNumSeries.setText("Series restantes: " + numSeries);
            }
        };
    }
}