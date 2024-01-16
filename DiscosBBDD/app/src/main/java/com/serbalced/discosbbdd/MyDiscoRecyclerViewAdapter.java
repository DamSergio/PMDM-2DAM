package com.serbalced.discosbbdd;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serbalced.discosbbdd.databinding.FragmentDiscoBinding;

import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyDiscoRecyclerViewAdapter extends RecyclerView.Adapter<MyDiscoRecyclerViewAdapter.ViewHolder> {

    private final List<Disco> mValues;

    public MyDiscoRecyclerViewAdapter(List<Disco> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        return new ViewHolder(FragmentDiscoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.data.setText(mValues.get(position).toString());
        holder.data.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView data;
        public Disco mItem;

        public ViewHolder(FragmentDiscoBinding binding) {
            super(binding.getRoot());
            data = binding.txtGrupoDisco;
        }

        @Override
        public String toString() {
            return "";
        }
    }
}