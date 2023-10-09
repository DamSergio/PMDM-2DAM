package com.serbalced.walterwhite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private final int MAX_SWITCHS = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch sombrero = findViewById(R.id.swSombrero);
        Switch bigote = findViewById(R.id.swBigote);

        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int res = 0;
                if (sombrero.isChecked()){
                    res += 1;
                }
                if (bigote.isChecked()){
                    res += 2;
                }
                int walters[] = {R.drawable.walter00, R.drawable.walter10, R.drawable.walter01, R.drawable.walter11};
                ImageView img = findViewById(R.id.imgWalter);
                img.setImageResource(walters[res]);
            }
        };

        bigote.setOnCheckedChangeListener(listener);
        sombrero.setOnCheckedChangeListener(listener);
    }
}