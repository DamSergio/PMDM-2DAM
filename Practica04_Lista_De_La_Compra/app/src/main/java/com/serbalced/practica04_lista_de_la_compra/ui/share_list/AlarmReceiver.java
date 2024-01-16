package com.serbalced.practica04_lista_de_la_compra.ui.share_list;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import com.serbalced.practica04_lista_de_la_compra.R;
import com.serbalced.practica04_lista_de_la_compra.dto.Product;
import com.serbalced.practica04_lista_de_la_compra.managers.ContactsManager;
import com.serbalced.practica04_lista_de_la_compra.managers.ShoppingListsManager;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ContactsManager.NOTIFICATION_CODE)) {
            String tlf = intent.getExtras().getString("tlf");
            String list = ContactsManager.formatList();

            //notificacion
            sendNotification(context);

            if (ContactsManager.tipeOfMsg.equals("SMS")) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(tlf, null, list, null, null);
            } else {
                context.startActivity(
                        new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(
                                        String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                                                tlf,
                                                list
                                        )
                                )
                        )
                );
            }
        }
    }

    public void sendNotification(Context context) {
        NotificationCompat.Builder notificationShoppingList = new NotificationCompat.Builder(
                context,
                "notificationShoppingList"
        );

        notificationShoppingList.setSmallIcon(android.R.drawable.star_big_on);
        notificationShoppingList.setContentTitle("Notification");
        notificationShoppingList.setContentText("Shopping list sent");

        NotificationManager notificator = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        NotificationChannel notificationChannel = new NotificationChannel(
                "notificationShoppingList",
                "Shopping List",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        notificator.createNotificationChannel(notificationChannel);

        int notificationId = 1;
        notificator.notify(
                notificationId,
                notificationShoppingList.build()
        );
    }
}
