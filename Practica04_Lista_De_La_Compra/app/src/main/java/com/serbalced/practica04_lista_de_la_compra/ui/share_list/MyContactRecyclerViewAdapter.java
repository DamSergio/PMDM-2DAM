package com.serbalced.practica04_lista_de_la_compra.ui.share_list;

import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.serbalced.practica04_lista_de_la_compra.R;
import com.serbalced.practica04_lista_de_la_compra.dto.Contact;
import com.serbalced.practica04_lista_de_la_compra.databinding.FragmentContactBinding;
import com.serbalced.practica04_lista_de_la_compra.managers.ContactsManager;
import com.serbalced.practica04_lista_de_la_compra.managers.DatabaseManager;
import com.serbalced.practica04_lista_de_la_compra.managers.ShoppingListsManager;

import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyContactRecyclerViewAdapter extends RecyclerView.Adapter<MyContactRecyclerViewAdapter.ViewHolder> {

    private final List<Contact> mValues;
    private Context con;

    public MyContactRecyclerViewAdapter(List<Contact> items, Context con) {
        mValues = items;
        this.con = con;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mName.setText(mValues.get(position).name);

        holder.mWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactsManager.tipeOfMsg = "WSP";

                if (ShoppingListsManager.listToShow == null){
                    return;
                }

                if (ContactsManager.setAlarm) {
                    setAlarm(mValues.get(position).numbers.get(0));
                } else {
                    String list = ContactsManager.formatList();

                    con.startActivity(
                            new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(
                                            String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                                                    mValues.get(position).numbers.get(0),
                                                    list
                                            )
                                    )
                            )
                    );

                    Toast.makeText(
                            con,
                            R.string.list_sent,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        holder.mSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactsManager.tipeOfMsg = "SMS";

                if (ShoppingListsManager.listToShow == null){
                    return;
                }

                if (ContactsManager.setAlarm) {
                    setAlarm(mValues.get(position).numbers.get(0));
                } else {
                    String list = ContactsManager.formatList();

                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(mValues.get(position).numbers.get(0), null, list, null, null);

                    Toast.makeText(
                            con,
                            R.string.list_sent,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        if (mValues.get(position).numbers.isEmpty()) {
            holder.mSMS.setEnabled(false);
            holder.mWhatsApp.setEnabled(false);
        }

        getContactImage(holder.mImage, mValues.get(position).id);
    }

    public void setAlarm(String tlf) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, ContactsManager.hour);
        calendar.set(Calendar.MINUTE, ContactsManager.minute);

        AlarmManager alarmManager = (AlarmManager) con.getSystemService(Context.ALARM_SERVICE);

        Intent alarm = new Intent(con, AlarmReceiver.class);
        alarm.setAction(ContactsManager.NOTIFICATION_CODE);
        alarm.putExtra("tlf", tlf);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(
                con,
                ContactsManager.REQUEST_CODE,
                alarm,
                PendingIntent.FLAG_MUTABLE
        );

        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                alarmIntent
        );
    }

    public void getContactImage(ImageView img, long id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap b = getImg(id);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        img.setImageBitmap(b);
                    }
                });
            }
        });
    }

    public Bitmap getImg(long id){
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(
                con.getContentResolver(),
                contactUri,
                true /*preferHighres*/
        );
        return BitmapFactory.decodeStream(inputStream);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImage;
        public final TextView mName;
        public final Button mWhatsApp;
        public final Button mSMS;
        public Contact mItem;

        public ViewHolder(FragmentContactBinding binding) {
            super(binding.getRoot());
            mImage = binding.imgContact;
            mName = binding.txtConName;
            mWhatsApp = binding.btnWSP;
            mSMS = binding.btnSMS;
        }

        @Override
        public String toString() {
            return "";
        }
    }
}