package com.serbalced.onrespuesta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogoSexo  extends DialogFragment {
    OnRespuestaSexo miRespuesta;

    @Override
    public void onAttach(@NonNull Context context) { //sucede cuando se crea y pasa a formar parte de la actividad
        super.onAttach(context);
        miRespuesta = (OnRespuestaSexo) getActivity();
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
        myBuilder.setTitle("Pregunta para el usuario");
        myBuilder.setMessage("Eres una chica?");
        myBuilder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                miRespuesta.onRespuesta(true);
            }
        });

        myBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                miRespuesta.onRespuesta(false);
            }
        });

        return myBuilder.create();
    }

    public interface OnRespuestaSexo {
        public void onRespuesta(Boolean respuesta);
    }
}
