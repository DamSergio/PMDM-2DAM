package com.serbalced.notificacion;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted){
                    enviarNotificacion();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "NO PUEDO ENVIAR NOTIFICACION SIN TU PERMISO",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.btnNoti);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission("android.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED){
                    requestPermissionLauncher.launch("android.permission.POST_NOTIFICATIONS");
                } else {
                    enviarNotificacion();
                }
            }
        });
    }

    public void enviarNotificacion(){
        //1º crear la notificacion
        NotificationCompat.Builder miConstructor = new NotificationCompat.Builder(
                this,
                "notificacionesListaCompra"
        );

        miConstructor.setSmallIcon(android.R.drawable.star_big_on);
        miConstructor.setContentTitle("Notificacion de hacienda");
        miConstructor.setContentText("Ha recibido usted una notificacion para pagar impuestos");

        //2º OPCIONAL -> cuando pulsas se abra una activity
        Intent apertura = new Intent(
                this,
                MainActivity2.class
        );

        TaskStackBuilder pila = TaskStackBuilder.create(this);
        pila.addParentStack(MainActivity2.class);
        pila.addNextIntent(apertura);

        PendingIntent resultadoPendingIntent = pila.getPendingIntent(
                0,
                PendingIntent.FLAG_MUTABLE
        );
        miConstructor.setContentIntent(resultadoPendingIntent);

        //3º paso -> enviar la notificacion
        //3.1 crear un notificador
        NotificationManager notificador = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

        //3.2 crear un canal de notificacion
        NotificationChannel miCanal = new NotificationChannel(
                "notificacionesListaCompra",
                "Agencia tributaria",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        notificador.createNotificationChannel(miCanal);

        //3.3 enviarla "de verdad"
        int idNotificacion = 1;
        notificador.notify(
                idNotificacion,
                miConstructor.build()
        );
    }
}