package com.example.sharemybike;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharemybike.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.sharemybike.databinding.FragmentBikeBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBikeRecyclerViewAdapter extends RecyclerView.Adapter<MyBikeRecyclerViewAdapter.ViewHolder> {

    private final List<BikesContent.Bike> mValues;
    private final Context mContext;

    public MyBikeRecyclerViewAdapter(List<BikesContent.Bike> items, Context context) {
        mValues = items;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentBikeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mImg.setImageBitmap(mValues.get(position).getPhoto());
        holder.mCity.setText(mValues.get(position).getCity());
        holder.mName.setText(mValues.get(position).getOwner());
        holder.mDir.setText(mValues.get(position).getLocation());
        holder.mDesc.setText(mValues.get(position).getDescription());
        holder.mBtnMail.setOnClickListener(v -> {
            String mail = mValues.get(position).getEmail();

            Intent intentMail = new Intent(Intent.ACTION_SEND);
            intentMail.setData(Uri.parse("mailto:"+mail));

            intentMail.putExtra(Intent.EXTRA_EMAIL, mail);
            intentMail.putExtra(Intent.EXTRA_SUBJECT, "Dear Mr/Mrs " + mValues.get(position).getOwner());
            intentMail.putExtra(
                    Intent.EXTRA_TEXT,
                    "I'd like to use your bike at " + mValues.get(position).getLocation() + " (" + mValues.get(position).getCity() + ")\n"
                    + "for the following date: " + BikesContent.selectedDate
                    + "\n\n"
                    + "Can you confirm its availability?\n"
                    + "Kindest regards"
            );

            intentMail.setType("message/rfc822");

            mContext.startActivity(intentMail);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mName;
        public final TextView mCity;
        public final TextView mDir;
        public final TextView mDesc;
        public final ImageView mImg;
        public final ImageButton mBtnMail;
        public BikesContent.Bike mItem;

        public ViewHolder(FragmentBikeBinding binding) {
            super(binding.getRoot());
            mCity = binding.txtCity;
            mName = binding.txtName;
            mDir = binding.txtDir;
            mDesc = binding.txtDesc;
            mImg = binding.imgBike;
            mBtnMail = binding.btnMailBike;
        }

        @Override
        public String toString() {
            return "";
        }
    }
}