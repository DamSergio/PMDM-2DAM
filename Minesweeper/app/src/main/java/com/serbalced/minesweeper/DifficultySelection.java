package com.serbalced.minesweeper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class DifficultySelection extends DialogFragment {
    OnDiffChange diff;
    int dim;
    int bombs;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        diff = (OnDiffChange) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
        myBuilder.setTitle(R.string.diffTitle);

        ArrayAdapter<String> difficulties = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_single_choice,
                new String[]{
                        getString(R.string.diffNoob),
                        getString(R.string.diffAma),
                        getString(R.string.diffAdv)
                }
        );

        myBuilder.setSingleChoiceItems(difficulties, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        dim = 8;
                        bombs = 10;
                        break;
                    case 1:
                        dim = 12;
                        bombs = 16;
                        break;
                    case 2:
                        dim = 16;
                        bombs = 22;
                        break;
                    default:
                        dim = 8;
                        bombs = 10;
                }
            }
        });

        myBuilder.setPositiveButton("Volver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                diff.onDiffChange(dim, bombs);
            }
        });

        return myBuilder.create();
    }

    public interface OnDiffChange {
        public void onDiffChange(int dim, int bombs);
    }
}
