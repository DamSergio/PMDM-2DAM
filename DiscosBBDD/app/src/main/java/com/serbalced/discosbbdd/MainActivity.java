package com.serbalced.discosbbdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private TextView txtGrupo;
    private TextView txtDisco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtGrupo = findViewById(R.id.txtGrupo);
        txtDisco = findViewById(R.id.txtDisco);

        ConstraintLayout cl = findViewById(R.id.cl);
        cl.post(new Runnable() {
            @Override
            public void run() {
                cargarBD();
            }
        });

        ImageButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadirDisco();
            }
        });

        ImageButton btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarDiscos();
            }
        });

    }

    public void borrarDiscos() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Disco disco = null;
                for (Disco d : ListaDiscos.discos){
                    if (d.grupo.equals(txtGrupo.getText().toString()) && d.disco.equals(txtDisco.getText().toString())){
                        disco = d;
                    }
                }

                if (disco != null){
                    ListaDiscos.discos.remove(disco);

                    db.execSQL("DELETE FROM misDiscos WHERE grupo = '" + disco.grupo + "' AND disco = '" + disco.disco + "'");
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ListaDiscos.adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public void añadirDisco() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                ListaDiscos.discos.add(
                        new Disco(
                                txtGrupo.getText().toString(),
                                txtDisco.getText().toString()
                        )
                );

                db.execSQL("INSERT INTO misDiscos VALUES('" + txtGrupo.getText().toString() + "', '" + txtDisco.getText().toString() + "')");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ListaDiscos.adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public void cargarBD() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                db = openOrCreateDatabase("MisDiscos", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS misDiscos(Grupo VARCHAR,Disco VARCHAR)");

                Cursor c=db.rawQuery("SELECT * FROM misDiscos", null);
                if(c.getCount() > 0){
                    while (c.moveToNext()){
                        ListaDiscos.discos.add(new Disco(c.getString(0), c.getString(1)));
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ListaDiscos.adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}