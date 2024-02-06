package com.serbalced.tarea6elreproductordemedios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;

import com.serbalced.tarea6elreproductordemedios.dto.Reproductor;
import com.serbalced.tarea6elreproductordemedios.managers.ReproductorManager;
import com.serbalced.tarea6elreproductordemedios.recyclerView.MyReproductorRecyclerViewAdapter;
import com.serbalced.tarea6elreproductordemedios.ui.settings.SettingsActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {
    public static MediaPlayer mp;
    public static MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mc = new MediaController(this);
        mc.setMediaPlayer(this);
        mc.setAnchorView(findViewById(R.id.cl));

        ReproductorManager.adapter = new MyReproductorRecyclerViewAdapter(ReproductorManager.ITEMS_TO_SHOW, getApplicationContext());
        ReproductorManager.cargarJSON(this);
    }

    @Override
    public void start() {
        if (!mp.isPlaying())
            mp.start();
    }

    @Override
    public void pause() {
        if (mp.isPlaying())
            mp.pause();
    }

    @Override
    public int getDuration() {
        return mp.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mp.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mp.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mp.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mp.getAudioSessionId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(
                R.menu.menu,
                menu
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnOpciones) {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);
        }

        return super.onOptionsItemSelected(item);
    }
}