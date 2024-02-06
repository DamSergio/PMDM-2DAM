package com.example.sharemybike.mainPanel.ui.nav_bikes;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharemybike.databinding.FragmentBikeBinding;
import com.example.sharemybike.dto.Bike;
import com.example.sharemybike.dto.BikesContent;
import com.example.sharemybike.dto.User;
import com.example.sharemybike.dto.UserBooking;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyBikeRecyclerViewAdapter extends RecyclerView.Adapter<MyBikeRecyclerViewAdapter.ViewHolder> {

    private List<Bike> mValues;
    private final Context mContext;
    private DatabaseReference mDatabase;
    private StorageReference mStorageReference;
    private final String TAG = "serbalced";

    public MyBikeRecyclerViewAdapter(ArrayList<Bike> items, Context context) {
        mValues = items;
        mContext = context;

        loadBikesList();
    }

    private void loadBikesList() {
        if (mValues.isEmpty()) {
            mDatabase = FirebaseDatabase.getInstance("https://sharemybike-ba533-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

            mDatabase.child("bikes_list").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                        Bike bike = productSnapshot.getValue(Bike.class);
                        downloadPhoto(bike);
                        //mValues.add(bike);
                        BikesContent.ITEMS.add(bike);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    private void downloadPhoto(Bike c) {

        mStorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(c.getImage());
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            final File localFile = File.createTempFile("PNG_" + timeStamp, ".png");
            mStorageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Insert the downloaded image in its right position at the ArrayList

                    String url = "gs://" + taskSnapshot.getStorage().getBucket() + "/" + taskSnapshot.getStorage().getName();
                    Log.d(TAG, "Loaded " + url);
                    for (Bike c : mValues) {
                        if (c.getImage().equals(url)) {
                            c.setPhoto(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
                            notifyDataSetChanged();
                            Log.d(TAG, "Loaded pic " + c.getImage() + ";" + url + localFile.getAbsolutePath());
                        }
                    }
                }

            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            UserBooking ub = new UserBooking();
            ub.userId = User.getInstance().getUid();
            ub.userEmail = User.getInstance().getEmail();
            ub.bikeCity = mValues.get(position).getCity();
            ub.bikeEmail = mValues.get(position).getEmail();
            ub.bookDate = BikesContent.date;

            ub.addToDatabase();
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
        public Bike mItem;

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