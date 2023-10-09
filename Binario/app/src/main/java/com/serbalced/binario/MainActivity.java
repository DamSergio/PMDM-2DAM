package com.serbalced.binario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final int MAX_CASILLAS = 8;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int [] chksId = new int[]{R.id.chk0, R.id.chk1, R.id.chk2, R.id.chk3, R.id.chk4, R.id.chk5, R.id.chk6, R.id.chk7};
        CheckBox chks[] = new CheckBox[MAX_CASILLAS];

        for (int i = 0; i < MAX_CASILLAS; i++){
            chks[i] = findViewById(chksId[i]);
            chks[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int res = 0;
                    for (int i = 0; i < chks.length; i++){
                        if (chks[i].isChecked()){
                            res += Math.pow(2, i);
                        }
                    }

                    TextView txtRes = findViewById(R.id.txtRes);
                    txtRes.setText(res+"");
                }
            });
        }
//
//        for (int i = 0; i < chks.length; i++){
//            chks[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked){
//                        for (int i = 0; i < chks.length; i++){
//                            if (chks[i] == buttonView){
//                                res += Math.pow(2, i);
//                            }
//                        }
//                    } else {
//                        for (int i = 0; i < chks.length; i++){
//                            if (chks[i] == buttonView){
//                                res -= Math.pow(2, i);
//                            }
//                        }
//                    }
//
//                    txtRes.setText(res+"");
//                }
//            });
//        }

//        int [] chksId = new int[]{R.id.chk0, R.id.chk1, R.id.chk2, R.id.chk3, R.id.chk4, R.id.chk5, R.id.chk6, R.id.chk7};
//        CheckBox chks[] = new CheckBox[MAX_CASILLAS];
//
//        for (int i = 0; i < MAX_CASILLAS; i++){
//            chks[i] = findViewById(chksId[i]);
//        }
    }
}