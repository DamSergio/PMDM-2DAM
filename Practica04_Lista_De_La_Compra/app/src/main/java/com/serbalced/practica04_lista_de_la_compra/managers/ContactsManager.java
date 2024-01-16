package com.serbalced.practica04_lista_de_la_compra.managers;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;

import com.serbalced.practica04_lista_de_la_compra.dto.Contact;
import com.serbalced.practica04_lista_de_la_compra.dto.Product;
import com.serbalced.practica04_lista_de_la_compra.ui.share_list.MyContactRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactsManager {
    public static ArrayList<Contact> contacts = new ArrayList<>();
    public static MyContactRecyclerViewAdapter adapter;
    public static Context context;
    public static boolean setAlarm = false;
    public static int hour;
    public static int minute;
    public static String tipeOfMsg;
    public final static String NOTIFICATION_CODE = "send_msg_notification";
    public final static int REQUEST_CODE = 0;
    public final static int FLAGS = 0;

    public static void loadContacts(String name) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                String projection[] = {
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.HAS_PHONE_NUMBER
                };
                String selection = ContactsContract.Contacts.DISPLAY_NAME + " like ?";
                String selection_args[] = {"%" + name + "%"};

                ContentResolver cr = context.getContentResolver();
                Cursor cursor = cr.query(
                        ContactsContract.Contacts.CONTENT_URI,
                        projection,
                        selection,
                        selection_args,
                        null
                );
                if (cursor.getCount() <= 0){
                    return;
                }

                int i = 0;

                while (cursor.moveToNext()){
                    long id = Long.parseLong(cursor.getString(0));
                    String contactName = cursor.getString(1);
                    int hasNumber = Integer.parseInt(cursor.getString(2));

                    Contact c = new Contact();
                    c.id = id;
                    c.name = contactName;

                    if (hasNumber > 0){
                        ArrayList<String> phoneNumbers = ContactsManager.getContacNumbers(cr, id);
                        c.numbers = phoneNumbers;
                    }

                    contacts.add(c);

                    i++;
                    if (i % 5 == 0) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ContactsManager.adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }

                 cursor.close();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ContactsManager.adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public static ArrayList<String> getContacNumbers(ContentResolver cr, long contactId) {
        ArrayList<String> phoneNumbers = new ArrayList<>();

        String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{contactId + ""},
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String phoneNumber = cursor.getString(0);
                phoneNumbers.add(phoneNumber);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return phoneNumbers;
    }

    public static String formatList() {
        String formatedList = ShoppingListsManager.listToShow.name + ".\n";
        for (Product p : ShoppingListsManager.listToShow.products) {
            formatedList += "\t" + p.name + ". Cantidad: " + p.amount + ".\n";
        }

        return formatedList;
    }
}
