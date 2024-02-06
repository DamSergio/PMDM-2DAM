package com.serbalced.tarea6elreproductordemedios.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import androidx.preference.PreferenceManager;

import com.serbalced.tarea6elreproductordemedios.R;
import com.serbalced.tarea6elreproductordemedios.dto.Reproductor;
import com.serbalced.tarea6elreproductordemedios.recyclerView.MyReproductorRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReproductorManager {
    public static ArrayList<Reproductor> ITEMS = new ArrayList<>();
    public static ArrayList<Reproductor> ITEMS_TO_SHOW = new ArrayList<>();
    public static MyReproductorRecyclerViewAdapter adapter;

    public static void cargarJSON(Context c) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                String json = null;
                try {
                    InputStream is = c.getAssets().open("recursosList.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();
                    json = new String(buffer, "UTF-8");

                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray couchList = jsonObject.getJSONArray("recursos_list");
                    for (int i = 0; i < couchList.length(); i++) {
                        JSONObject jsonCouch = couchList.getJSONObject(i);
                        String name = jsonCouch.getString("nombre");
                        String desc = jsonCouch.getString("descripcion");
                        int tipo = Integer.parseInt(jsonCouch.getString("tipo"));
                        String uri = jsonCouch.getString("URI");
                        String imagen = jsonCouch.getString("imagen");

                        MediaPlayer mp = null;
                        if (tipo == 0) {
                            int resID = c.getResources().getIdentifier(uri, "raw", c.getPackageName());
                            mp = MediaPlayer.create(c, resID);
                        }

                        Reproductor r = new Reproductor(name, desc, tipo, uri, imagen, mp);
                        ITEMS.add(r);

                        SharedPreferences myDefaultPreferences = PreferenceManager.getDefaultSharedPreferences(c);
                        añadirReproduccion(myDefaultPreferences, r);
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ReproductorManager.adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void añadirReproduccion(SharedPreferences sharedPreferences, Reproductor r) {
        boolean audio = sharedPreferences.getBoolean("audio", true);
        boolean video = sharedPreferences.getBoolean("video", true);
        boolean stream = sharedPreferences.getBoolean("stream", true);

        switch (r.tipo) {
            case 0:
                if (audio)
                    ReproductorManager.ITEMS_TO_SHOW.add(r);
                break;

            case 1:
                if (video)
                    ReproductorManager.ITEMS_TO_SHOW.add(r);
                break;

            case 2:
                if (stream)
                    ReproductorManager.ITEMS_TO_SHOW.add(r);
                break;
        }
    }
}
