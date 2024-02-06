package com.serbalced.tarea6elreproductordemedios.ui.settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.serbalced.tarea6elreproductordemedios.R;
import com.serbalced.tarea6elreproductordemedios.dto.Reproductor;
import com.serbalced.tarea6elreproductordemedios.managers.ReproductorManager;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences myDefaultPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myDefaultPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
        ReproductorManager.ITEMS_TO_SHOW.clear();
        for (Reproductor r : ReproductorManager.ITEMS) {
            ReproductorManager.añadirReproduccion(sharedPreferences, r);
        }

        ReproductorManager.adapter.notifyDataSetChanged();
    }
}