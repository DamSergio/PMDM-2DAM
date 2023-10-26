package com.serbalced.customlayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ciudad miArrayCiudades[] = new Ciudad[5];
        miArrayCiudades[0] = new Ciudad("Toledo", "La ciudad imperial", R.drawable.toledo);
        miArrayCiudades[1] = new Ciudad("Ciudad Real", "unida ciudad muy bonita", R.drawable.ciudadreal);
        miArrayCiudades[2] = new Ciudad("Cuenca", "La ciudad de las casas colgantes", R.drawable.cuenca);
        miArrayCiudades[3] = new Ciudad("Guadalajara", "Otra ciudad muy bonita", R.drawable.guadalajara);
        miArrayCiudades[4] = new Ciudad("Albacete", "La feria es esencial en albacete", R.drawable.albacete);

        ListView lstCiudades = findViewById(R.id.lstCiudades);
        lstCiudades.setAdapter(
                new MiAdaptadorCiudad(
                        this,
                        R.layout.mifilaciudad,
                        miArrayCiudades
                )
        );
    }

    public class MiAdaptadorCiudad extends ArrayAdapter<Ciudad>{
        Ciudad misObjetos[];

        public MiAdaptadorCiudad(@NonNull Context context, int resource, @NonNull Ciudad[] objects) {
            super(context, resource, objects);
            this.misObjetos = objects;
        }

        //Este para lista (tambien se utiliza en spinners para la view original)
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return crearFile(position, convertView, parent);
        }

        //Este para spinners
        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return crearFile(position, convertView, parent);
        }

        public View crearFile(int position, View convertView, ViewGroup parent){
            //1º inflamos el xml con nuestra vista personalizada
            LayoutInflater miInflador = getLayoutInflater();
            View miFila = miInflador.inflate(
                    R.layout.mifilaciudad,
                    parent,
                    false
            );

            //2º encontramos las referencias a los componentes de nuestra vista
            TextView nombre = miFila.findViewById(R.id.tvNombre);
            TextView desc = miFila.findViewById(R.id.tvDesc);
            ImageView img = miFila.findViewById(R.id.ivCiudad);

            //3º rellenamos la vista
            nombre.setText(
                    misObjetos[position].getNombre()
            );
            desc.setText(
                    misObjetos[position].getDescripcion()
            );
            img.setImageResource(
                    misObjetos[position].getImagen()
            );

            return miFila;
        }
    }
}