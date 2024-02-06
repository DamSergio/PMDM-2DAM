package com.example.sharemybike.mainPanel.ui.nav_new_bike;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharemybike.databinding.FragmentNewBikeBinding;
import com.example.sharemybike.dto.Bike;
import com.example.sharemybike.dto.BikesContent;
import com.example.sharemybike.dto.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class NewBikeFragment extends Fragment {

    private FragmentNewBikeBinding binding;
    private Bike bike;
    private Bitmap bitmap;
    private final int PHOTO_CODE = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewBikeViewModel newBikeViewModel =
                new ViewModelProvider(this).get(NewBikeViewModel.class);

        binding = FragmentNewBikeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        bike = new Bike();

        binding.txtUserInfo.setText("Bike of " + User.getInstance().getName() + " [" + User.getInstance().getEmail() + "]");
        binding.btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photo, PHOTO_CODE);
            }
        });

        binding.btnAddBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edLong.getText().equals("")) {
                    Toast.makeText(
                            getContext(),
                            "Must enter longitude",
                            Toast.LENGTH_LONG
                    ).show();

                    return;
                }

                if (binding.edLat.getText().equals("")) {
                    Toast.makeText(
                            getContext(),
                            "Must enter latitude",
                            Toast.LENGTH_LONG
                    ).show();

                    return;
                }

                if (binding.edAdd.getText().equals("")) {
                    Toast.makeText(
                            getContext(),
                            "Must enter address",
                            Toast.LENGTH_LONG
                    ).show();

                    return;
                }

                if (binding.edCity.getText().equals("")) {
                    Toast.makeText(
                            getContext(),
                            "Must enter city",
                            Toast.LENGTH_LONG
                    ).show();

                    return;
                }

                if (binding.edDesc.getText().equals("")) {
                    Toast.makeText(
                            getContext(),
                            "Must enter description",
                            Toast.LENGTH_LONG
                    ).show();

                    return;
                }

                double lat = 0;
                try {
                    lat = Double.parseDouble(binding.edLat.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(
                            getContext(),
                            "latitude must be a number",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }

                double longi = 0;
                try {
                    longi = Double.parseDouble(binding.edLat.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(
                            getContext(),
                            "longitude must be a number",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }

                if(bitmap == null) {
                    Toast.makeText(
                            getContext(),
                            "Must take a photo",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }

                bike.setCity(binding.edCity.getText().toString());
                bike.setDescription(binding.edDesc.getText().toString());
                bike.setEmail(User.getInstance().getEmail());
                bike.setLatitude(lat);
                bike.setLongitude(longi);
                bike.setLocation(binding.edAdd.getText().toString());
                bike.setOwner(User.getInstance().getName());
                bike.setPhoto(bitmap);

                BikesContent.ITEMS.add(bike);

                Toast.makeText(
                        getContext(),
                        "Bike created",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PHOTO_CODE) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            binding.imgNewBike.setImageBitmap(bitmap);
        }
    }
}