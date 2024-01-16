package com.serbalced.preferencias;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity2 extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SharedPreferences myDefaultPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean godMode = myDefaultPreferences.getBoolean("godMode", false);
        Log.d("serbalced", "Modo dios = " + godMode);

        myDefaultPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
        if (key.equals("godMode")){
            boolean godMode = sharedPreferences.getBoolean("godMode", false);
            Log.d("serbalced", "Modo dios = " + godMode);
        }
    }
}