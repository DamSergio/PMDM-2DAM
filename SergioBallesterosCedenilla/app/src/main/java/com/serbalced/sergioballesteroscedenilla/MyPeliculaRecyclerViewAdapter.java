package com.serbalced.sergioballesteroscedenilla;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.serbalced.sergioballesteroscedenilla.placeholder.PlaceholderContent.PlaceholderItem;
import com.serbalced.sergioballesteroscedenilla.databinding.FragmentPeliculaBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPeliculaRecyclerViewAdapter extends RecyclerView.Adapter<MyPeliculaRecyclerViewAdapter.ViewHolder> {

    private final List<Pelicula> mValues;

    public MyPeliculaRecyclerViewAdapter(List<Pelicula> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentPeliculaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mName.setText(mValues.get(position).getNombre());
        holder.mDir.setText(mValues.get(position).getDirector());
        holder.mYear.setText(mValues.get(position).getAño() + "");
        holder.mCod.setText(mValues.get(position).getCodigo() + "");

        if (mValues.get(position).getBmp() == null){
            holder.mImg.setImageResource(mValues.get(position).getImagen());
        } else {
            holder.mImg.setImageBitmap(mValues.get(position).getBmp());
        }

        holder.mRatingBar.setRating(mValues.get(position).getRating());

        holder.mRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            mValues.get(position).setRating(rating);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mName;
        public final TextView mDir;
        public final TextView mYear;
        public final TextView mCod;
        public final ImageView mImg;
        public final RatingBar mRatingBar;
        public Pelicula mItem;

        public ViewHolder(FragmentPeliculaBinding binding) {
            super(binding.getRoot());
            mName = binding.txtName;
            mDir = binding.txtDir;
            mYear = binding.txtYear;
            mCod = binding.txtCod;
            mImg = binding.imgPort;
            mRatingBar = binding.rbRating;
        }

        @Override
        public String toString() {
            return "";
        }
    }
}