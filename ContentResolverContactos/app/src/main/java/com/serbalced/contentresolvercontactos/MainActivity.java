package com.serbalced.contentresolvercontactos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public final int PERMISO_CONTACTO = 1;
    public EditText edFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edFiltro = findViewById(R.id.txtCont);
        edFiltro.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (checkSelfPermission("android.permission.READ_CONTACTS") != PackageManager.PERMISSION_GRANTED){
                    return false;
                }

                String filtro = ((TextView )v).getText().toString();
                ListaContactos.contactos.clear();
                cargarContactos(filtro);

                //ListaContactos.adapter.notifyDataSetChanged();

                return false;
            }
        });
//        edFiltro.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (checkSelfPermission("android.permission.READ_CONTACTS") != PackageManager.PERMISSION_GRANTED){
//                    return false;
//                }
//
//                String filtro = ((TextView )v).getText().toString();
//                ListaContactos.contactos.clear();
//                cargarContactos(filtro);
//
//                ContactoFragment.adapter.notifyDataSetChanged();
//
//                return false;
//            }
//        });
        ConstraintLayout cl = findViewById(R.id.cl);
        cl.post(new Runnable() {
            @Override
            public void run() {
                if (checkSelfPermission("android.permission.READ_CONTACTS") != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(
                            new String[]{"android.permission.READ_CONTACTS"},
                            PERMISO_CONTACTO
                    );
                } else {
                    cargarContactos("");
                }
            }
        });
    }

    public void cargarContactos(String texto){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("serbalced", "cargando contactos");
                //SELECT _ID, HAS_PHONE_NUMBER, DISPLAY_NAME FROM ContactsContract.Contacts.CONTENT_URI
                //WHERE DISPLAY_NAME LIKE %nombre%

                //projections = _ID, HAS_PHONE_NUMBER, DISPLAY_NAME
                //selection = WHERE DISPLAY_NAME
                //selection_args = LIKE %nombre%
                String projection[] = {
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.HAS_PHONE_NUMBER
                };
                String selection = ContactsContract.Contacts.DISPLAY_NAME + " like ?";
                String selection_args[] = {"%" + texto + "%"};

                ContentResolver cr = getContentResolver();
                Cursor cursor = cr.query(
                        ContactsContract.Contacts.CONTENT_URI,
                        projection,
                        selection,
                        selection_args,
                        null
                );

                Log.d("serbalcel", "cursor cargado");

                if (cursor.getCount() == 0){
                    return;
                }

                while (cursor.moveToNext()){
                    long id = Long.parseLong(cursor.getString(0));
                    String nombre = cursor.getString(1);
                    int has_number = Integer.parseInt(cursor.getString(2));

                    ListaContactos.contactos.add(
                            new Contacto(
                                    nombre,
                                    has_number,
                                    id
                            )
                    );
                }
                //Trabajo en Background aqu�

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        ListaContactos.adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISO_CONTACTO){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                cargarContactos(edFiltro.getText().toString());
            }
        }
    }
}