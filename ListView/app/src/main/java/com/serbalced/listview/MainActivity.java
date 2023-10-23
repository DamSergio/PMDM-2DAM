package com.serbalced.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lstAlumnos = findViewById(R.id.lstAlumnos);
        String misAlmunos[] = {"Elia", "Rodri", "Carlos", "Marcos", "Francisco",
                "Andrea", "Sergio", "Javier", "Paco", "Pepe", "Pipo"};

        ArrayAdapter<String> miAdaptador = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                misAlmunos
        );

        lstAlumnos.setAdapter(miAdaptador);
        lstAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray checked = ((ListView) parent).getCheckedItemPositions(); //array booleano
                if (checked != null){
                    for (int i = 0; i < checked.size(); i++){
                        Log.d("serbalced", lstAlumnos.getItemAtPosition(checked.keyAt(i)).toString());
                    }
                }

                Toast.makeText(
                        getApplicationContext(),
                        "",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}