package com.serbalced.recyclerview;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.serbalced.recyclerview.databinding.FragmentCiudadBinding;

import java.util.ArrayList;
import java.util.List;

public class MyCiudadRecyclerViewAdapter extends RecyclerView.Adapter<MyCiudadRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Ciudad> mValues;

    public MyCiudadRecyclerViewAdapter(ArrayList<Ciudad> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentCiudadBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNombreCiudad.setText(mValues.get(position).mNombre);
        holder.desc.setText(mValues.get(position).mDesc);
        holder.image.setImageResource(mValues.get(position).mImage);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView desc;
        public final ImageView image;
        public Ciudad mItem;

        public final TextView mNombreCiudad;

        public ViewHolder(FragmentCiudadBinding binding) {
            super(binding.getRoot());
            desc = binding.txtDesc;
            image = binding.imgCiudad;
            mNombreCiudad = binding.txtNombre;
        }

        @Override
        public String toString() {
            return super.toString() + " '" +  "'";
        }
    }
}