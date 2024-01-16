package com.serbalced.practica04_lista_de_la_compra.ui.show_lists;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.serbalced.practica04_lista_de_la_compra.R;
import com.serbalced.practica04_lista_de_la_compra.managers.ProductsManager;
import com.serbalced.practica04_lista_de_la_compra.managers.ShoppingListsManager;

public class ShoppingListsDialog extends DialogFragment {
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.shopping_lists_dialog_title);

        ShoppingListsManager.adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                ShoppingListsManager.listsNames
        );
        builder.setAdapter(ShoppingListsManager.adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShoppingListsManager.listToShow = ShoppingListsManager.lists.get(which);
                ProductsManager.loadProductsFromList(ShoppingListsManager.listToShow.products);
            }
        });

        builder.setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
