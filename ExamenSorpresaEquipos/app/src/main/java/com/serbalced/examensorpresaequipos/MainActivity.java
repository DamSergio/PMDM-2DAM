package com.serbalced.examensorpresaequipos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Equipo equipos[] = new Equipo[5];
        equipos[0] = new Equipo("Real Madrid", R.drawable.realmadrid, 0);
        equipos[1] = new Equipo("Barcelona", R.drawable.barcelona, 0);
        equipos[2] = new Equipo("Chelsea", R.drawable.chelsea, 0);
        equipos[3] = new Equipo("Rayo", R.drawable.rayo, 0);
        equipos[4] = new Equipo("Manchester", R.drawable.manchester, 0);

        AdaptadorEquipos adapter = new AdaptadorEquipos(
                this,
                R.layout.mifilaequipos,
                equipos
        );

        ListView lstEquipos = findViewById(R.id.lstEquipos);
        lstEquipos.setAdapter(
                adapter
        );

        lstEquipos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                equipos[position].setPuntos(equipos[position].getPuntos() + 1);
//                TextView puntos = view.findViewById(R.id.tvPuntos);
//                puntos.setText(
//                        equipos[position].getPuntos() + ""
//                );
                adapter.notifyDataSetChanged();
            }
        });

        lstEquipos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                equipos[position].setPuntos(equipos[position].getPuntos() + 3);
//                TextView puntos = view.findViewById(R.id.tvPuntos);
//                puntos.setText(
//                        equipos[position].getPuntos() + ""
//                );
                adapter.notifyDataSetChanged(); //Aviso al adaptador de que han cambiado los datos y que se repinte
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //momento en el que se crea el menu de opciones
        getMenuInflater().inflate(
                R.menu.mi_menu,
                menu
        );
        return true; // true: mostrar 3 puntitos false no
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings_menu){
            Toast.makeText(this, "SE HA SELECCIONADO MENU SETTINGS", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.pj_menu){
            Toast.makeText(this, "SE HA SELECCIONADO MENU PERSONAJES", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "SE HA SELECCIONADO MENU Otro menu", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public class AdaptadorEquipos extends ArrayAdapter<Equipo>{
        Equipo equipos[];

        public AdaptadorEquipos(@NonNull Context context, int resource, @NonNull Equipo[] objects) {
            super(context, resource, objects);
            this.equipos = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return crearFila(position, convertView, parent);
        }

        public View crearFila(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater miInflador = getLayoutInflater();
            View miFila = miInflador.inflate(
                    R.layout.mifilaequipos,
                    parent,
                    false
            );

            TextView nombre = miFila.findViewById(R.id.tvNombre);
            ImageView img = miFila.findViewById(R.id.ivEscudo);
            TextView puntos = miFila.findViewById(R.id.tvPuntos);

            nombre.setText(
                    equipos[position].getNombre()
            );
            img.setImageResource(
                    equipos[position].getImg()
            );
            puntos.setText(
                    equipos[position].getPuntos() + ""
            );

            return miFila;
        }
    }
}