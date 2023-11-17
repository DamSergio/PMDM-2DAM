package com.serbalced.broadcastsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MiReceptorSMS extends BroadcastReceiver {
    private final String SMS_RECEIVED="android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)){
            leerSMS(context, intent);
        }
    }

    public void leerSMS(Context context, Intent intent){
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String origen = null;
        String msg = null;

        if (bundle != null) {
            //obtenemos el mensaje original SMS:
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            for (int i = 0; i < msgs.length; i++) {
                String format = bundle.getString("format");
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                origen = msgs[i].getOriginatingAddress();
                msg = msgs[i].getMessageBody().toString();
            }

        }

        Toast.makeText(
                context,
                "HAS RECIBIDO UN SMS",
                Toast.LENGTH_LONG
        ).show();

        Log.d("Recibido", "HAS RECIBIDO UN SMS DE " + origen + " CON CONTENIDO " + msg);
    }
}