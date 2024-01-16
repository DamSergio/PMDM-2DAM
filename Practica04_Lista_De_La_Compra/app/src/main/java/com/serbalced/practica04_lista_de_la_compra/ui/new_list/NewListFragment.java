package com.serbalced.practica04_lista_de_la_compra.ui.new_list;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.serbalced.practica04_lista_de_la_compra.R;
import com.serbalced.practica04_lista_de_la_compra.databinding.FragmentNewListBinding;
import com.serbalced.practica04_lista_de_la_compra.dto.Product;
import com.serbalced.practica04_lista_de_la_compra.dto.ShoppingList;
import com.serbalced.practica04_lista_de_la_compra.managers.DatabaseManager;
import com.serbalced.practica04_lista_de_la_compra.managers.ProductsManager;
import com.serbalced.practica04_lista_de_la_compra.managers.ShoppingListsManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewListFragment extends Fragment {

    private FragmentNewListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewListViewModel newListViewModel =
                new ViewModelProvider(this).get(NewListViewModel.class);

        binding = FragmentNewListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.txtListName.getText().toString().trim();
                if (nombre.equals("") || binding.txtListName.getText() == null) {
                    Toast.makeText(
                            getContext(),
                            R.string.no_list_name_error,
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }

                ShoppingListsManager.newList = new ShoppingList();
                ShoppingListsManager.newList.name = nombre;

                ProductsManager.productsToShow = new ArrayList<>(ProductsManager.listAllProducts);

                Intent newListActivity = new Intent(getContext(), NewListActivity.class);
                startActivity(newListActivity);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}