package com.serbalced.practica04_lista_de_la_compra.ui.new_product;

import android.content.Intent;
import android.graphics.Bitmap;
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

import com.serbalced.practica04_lista_de_la_compra.R;
import com.serbalced.practica04_lista_de_la_compra.databinding.FragmentNewProductBinding;
import com.serbalced.practica04_lista_de_la_compra.dto.Product;
import com.serbalced.practica04_lista_de_la_compra.managers.DatabaseManager;

import java.text.ParseException;

public class NewProductFragment extends Fragment {
    private final int CAMERA_IMG_REQUEST_CODE = 100;
    private FragmentNewProductBinding binding;
    private Bitmap imgBitmap;
    private boolean imgSelected;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewProductViewModel newProductViewModel =
                new ViewModelProvider(this).get(NewProductViewModel.class);

        binding = FragmentNewProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imgSelected = false;

        binding.btnImgNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(img, CAMERA_IMG_REQUEST_CODE);
            }
        });

        binding.btnNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imgSelected){
                    Toast.makeText(
                            getContext(),
                            R.string.toast_no_img_selected,
                            Toast.LENGTH_LONG
                    ).show();

                    return;
                }

                if (binding.txtNameNewProduct.getText().toString().trim().equals("")) {
                    Toast.makeText(
                            getContext(),
                            R.string.toast_no_name,
                            Toast.LENGTH_LONG
                    ).show();

                    return;
                }

                if (binding.txtDescNewProduct.getText().toString().trim().equals("")) {
                    Toast.makeText(
                            getContext(),
                            R.string.toast_no_desc,
                            Toast.LENGTH_LONG
                    ).show();

                    return;
                }

                Product p = new Product();
                p.name = binding.txtNameNewProduct.getText().toString();
                try {
                    p.price = Double.parseDouble(
                            binding.txtPriceNewProduct.getText().toString()
                    );
                } catch (Exception e){
                    p.price = 0;
                }
                p.description = binding.txtDescNewProduct.getText().toString();
                p.image = imgBitmap;

                DatabaseManager.insertProductWithImage(p);

                Toast.makeText(
                        getContext(),
                        R.string.product_created,
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
        if (requestCode == CAMERA_IMG_REQUEST_CODE && resultCode == -1){
            Bundle extras = data.getExtras();
            imgBitmap = (Bitmap) extras.get("data");
            binding.imgNewProduct.setImageBitmap(imgBitmap);

            imgSelected = true;
        }
    }
}