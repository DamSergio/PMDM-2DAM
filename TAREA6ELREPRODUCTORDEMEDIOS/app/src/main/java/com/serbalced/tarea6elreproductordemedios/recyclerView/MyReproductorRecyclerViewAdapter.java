package com.serbalced.tarea6elreproductordemedios.recyclerView;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.serbalced.tarea6elreproductordemedios.MainActivity;
import com.serbalced.tarea6elreproductordemedios.R;
import com.serbalced.tarea6elreproductordemedios.databinding.FragmentReproductorBinding;
import com.serbalced.tarea6elreproductordemedios.dto.Reproductor;
import com.serbalced.tarea6elreproductordemedios.managers.ReproductorManager;
import com.serbalced.tarea6elreproductordemedios.ui.video.VideoActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyReproductorRecyclerViewAdapter extends RecyclerView.Adapter<MyReproductorRecyclerViewAdapter.ViewHolder> {

    private final List<Reproductor> mValues;
    private Context context;
    private int iconos[] = {R.drawable.audio, R.drawable.video, R.drawable.streaming};

    public MyReproductorRecyclerViewAdapter(List<Reproductor> items, Context context) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentReproductorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mMiniatura.setImageBitmap(getImagen(mValues.get(position).imagen));
        holder.mNombre.setText(mValues.get(position).nombre);
        holder.mDesc.setText(mValues.get(position).desc);
        holder.mIcono.setImageResource(iconos[mValues.get(position).tipo]);

        holder.mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mValues.get(position).tipo == 0) {
                    audio(mValues.get(position));
                } else {
                    video(mValues.get(position));
                }
            }
        });
    }

    private void video(Reproductor r) {
        VideoActivity.uri = r.uri;
        VideoActivity.isWebm = r.tipo == 1;

        Intent video = new Intent(context, VideoActivity.class);
        video.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(video);
    }

    private void audio(Reproductor r) {
        for (Reproductor item : ReproductorManager.ITEMS) {
            if (item.tipo == r.tipo && item != r) {
                item.mp.stop();
                try {
                    item.mp.prepare();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (!r.mp.isPlaying()) {
            r.mp.start();
        }

        if (r.mp != MainActivity.mp) {
            MainActivity.mp = r.mp;
        }

        MainActivity.mc.show(r.mp.getDuration());
    }

    private Bitmap getImagen(String imagen) {
        try {
            InputStream is = context.getAssets().open("images/" + imagen);
            Bitmap bm = BitmapFactory.decodeStream(is);

            return bm;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mMiniatura;
        public final ImageView mIcono;
        public final TextView mNombre;
        public final TextView mDesc;
        public final ImageButton mBtnPlay;
        public Reproductor mItem;

        public ViewHolder(FragmentReproductorBinding binding) {
            super(binding.getRoot());
            mMiniatura = binding.imgMiniatura;
            mIcono = binding.imgIcono;
            mNombre = binding.txtNombre;
            mDesc = binding.txtDesc;
            mBtnPlay = binding.btnPlay;
        }
    }
}