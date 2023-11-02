package com.serbalced.minesweeper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class InstructionsDialog extends DialogFragment {
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
        myBuilder.setTitle(R.string.insTittle);
        myBuilder.setMessage(R.string.insMsg);
        myBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //NO HACE NADA, SOLO CIERRA
            }
        });

        return myBuilder.create();
    }
}
