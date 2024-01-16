package com.serbalced.practica04_lista_de_la_compra.ui.new_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.serbalced.practica04_lista_de_la_compra.R;
import com.serbalced.practica04_lista_de_la_compra.databinding.ActivityNewListBinding;
import com.serbalced.practica04_lista_de_la_compra.managers.ProductsManager;
import com.serbalced.practica04_lista_de_la_compra.managers.ShoppingListsManager;

import java.util.ArrayList;

public class NewListActivity extends AppCompatActivity {
    private ActivityNewListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNewListAccept.setOnClickListener(v -> {
            ShoppingListsManager.addProductsToList();
            ProductsManager.productsToShow = new ArrayList<>();
            finish();
        });
    }
}