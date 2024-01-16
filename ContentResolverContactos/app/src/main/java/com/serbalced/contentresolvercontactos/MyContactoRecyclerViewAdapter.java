package com.serbalced.contentresolvercontactos;

import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.serbalced.contentresolvercontactos.databinding.FragmentContactoBinding;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import java.util.logging.Handler;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Contacto}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyContactoRecyclerViewAdapter extends RecyclerView.Adapter<MyContactoRecyclerViewAdapter.ViewHolder> {

    private final List<Contacto> mValues;
    private Context con;

    public MyContactoRecyclerViewAdapter(List<Contacto> items, Context con) {
        mValues = items;
        this.con = con;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentContactoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).tiene_telefono + "");
        holder.mContentView.setText(mValues.get(position).nombre);

        //hacerlo asyncronamente
        getAsyncFoto(holder.mImg, mValues.get(position).id);
        //holder.mImg.setImageBitmap(getFoto(mValues.get(position).id));
    }

    private void getAsyncFoto(ImageView img, long id){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap b = getFoto(id);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        img.setImageBitmap(b);
                    }
                });
            }
        });
    }

    public Bitmap getFoto(long id){
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
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImg;
        public Contacto mItem;

        public ViewHolder(FragmentContactoBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            mImg = binding.imgCon;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}