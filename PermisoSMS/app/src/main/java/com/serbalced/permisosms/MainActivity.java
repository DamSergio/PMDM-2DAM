package com.serbalced.permisosms;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static int SOLICITUD_PERMISO_SMS = 1;
    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted){
                    enviarSMS();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "NO PUEDO ENVIAR SMS SIN TU PERMISO",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.btnSMS);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1ºcomprobar si hay permisos
                if (checkSelfPermission("android.permission.SEND_SMS") != PackageManager.PERMISSION_GRANTED){
                    //No tengo permisos y hay que solicitarlos
//                    requestPermissions(
//                            new String[]{"android.permission.SEND_SMS"},
//                            SOLICITUD_PERMISO_SMS
//                    );
                    requestPermissionLauncher.launch(
                            "android.permission.SEND_SMS"
                    );
                } else {
                    //tengo permisos -> enviar sms
                    enviarSMS();
                }
            }
        });
    }

    public void enviarSMS(){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("601274661", null, "wubba dubba dub dub", null, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SOLICITUD_PERMISO_SMS){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //El usuario me ha dado permiso
                enviarSMS();
            } else {
                Toast.makeText(
                        this,
                        "NO PUEDO ENVIAR SMS SIN TU PERMISO",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }
}