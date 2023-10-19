package com.serbalced.edittexts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText miTexto = findViewById(R.id.txtPass);
        miTexto.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    Toast.makeText(getApplicationContext(), "Se ha pulsado la lupa!", Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

        miTexto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ProgressBar pb = findViewById(R.id.pbPassword);
                pb.setMax(100);
                pb.setMin(0);
                pb.setProgress(0);

                boolean upper = false;
                boolean lower = false;
                boolean number = false;
                boolean specialChar = false;

                String psw = s.toString();
                for (char c : psw.toCharArray()){
                    if (!upper && c >= 'A' && c <= 'Z'){
                        pb.setProgress(pb.getProgress() + 25);
                        upper = true;
                    }

                    if (!lower && Character.isLowerCase(c)){
                        pb.setProgress(pb.getProgress() + 25);
                        lower = true;
                    }

                    if (!number && Character.isDigit(c)){
                        pb.setProgress(pb.getProgress() + 25);
                        number = true;
                    }

                    if (!specialChar && !Character.isLetterOrDigit(c)){
                        pb.setProgress(pb.getProgress() + 25);
                        specialChar = true;
                    }
                }
            }
        });
    }
}