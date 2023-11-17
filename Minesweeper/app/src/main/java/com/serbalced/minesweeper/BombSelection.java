package com.serbalced.minesweeper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BombSelection extends DialogFragment {
    private OnBombSel bombSel;
    private Bomb[] bombs = {
            new Bomb("Clasic", R.drawable.bomb),
            new Bomb("HD bomb", R.drawable.hd_bomb),
            new Bomb("C4", R.drawable.c4),
            new Bomb("TNT", R.drawable.tnt)
    };

    private int bomb = 0;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bombSel = (OnBombSel)getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
        myBuilder.setTitle(R.string.bombsTitle);

        BombAdapter bombAdapter = new BombAdapter(
                getActivity(),
                R.layout.bombs_sel,
                bombs
        );

        myBuilder.setAdapter(bombAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bombSel.onBombSel(which);
            }
        });

        myBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bombSel.onBombSel(bomb);
            }
        });

        return myBuilder.create();
    }

    public class BombAdapter extends ArrayAdapter<Bomb>{
        private Bomb bombs[];
        public BombAdapter(@NonNull Context context, int resource, @NonNull Bomb[] bombs) {
            super(context, resource, bombs);
            this.bombs = bombs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return crearFila(position, convertView, parent);
        }

        public View crearFila(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater miInflador = getLayoutInflater();
            View miFila = miInflador.inflate(
                    R.layout.bombs_sel,
                    parent,
                    false
            );

            TextView nombre = miFila.findViewById(R.id.txtName);
            ImageView img = miFila.findViewById(R.id.imgBomb);

            nombre.setText(
                    bombs[position].getNombre()
            );
            img.setImageResource(
                    bombs[position].getImg()
            );

            return miFila;
        }
    }

    public interface OnBombSel{
        public void onBombSel(int pos);
    }
}
