package com.serbalced.practica04_lista_de_la_compra.ui.show_lists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.serbalced.practica04_lista_de_la_compra.databinding.FragmentShowListsBinding;
import com.serbalced.practica04_lista_de_la_compra.managers.DatabaseManager;
import com.serbalced.practica04_lista_de_la_compra.managers.ProductsManager;
import com.serbalced.practica04_lista_de_la_compra.managers.ShoppingListsManager;

import java.util.ArrayList;

public class ShowListsFragment extends Fragment {

    private FragmentShowListsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ShowListsViewModel showListsViewModel =
                new ViewModelProvider(this).get(ShowListsViewModel.class);

        binding = FragmentShowListsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        ProductsManager.productsToShow = new ArrayList<>();
//        if (ProductsManager.adapter != null)
//            ProductsManager.adapter.notifyDataSetChanged();

        ShoppingListsDialog dialog = new ShoppingListsDialog();
        dialog.show(getParentFragmentManager(), "show_lists");

        binding.btnShoppingListAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManager.updateList(
                        ShoppingListsManager.listToShow.iD,
                        ShoppingListsManager.listToShow.products
                );
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ProductsManager.productsToShow = new ArrayList<>();
        binding = null;
    }
}