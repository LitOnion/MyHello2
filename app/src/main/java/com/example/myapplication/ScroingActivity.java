package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScroingActivity extends AppCompatActivity {

    private static final String TAG = "ScroingActivity";
    private TextView scroing1,scroing2;
    private int teams1 = 0,teams2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scroing);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        scroing1 = findViewById(R.id.sa1);
        scroing2 = findViewById(R.id.sb1);
        Button btnReset = findViewById(R.id.btn_reset);
        btnReset.setOnClickListener((v)->{
            teams1 = 0;
            teams2 = 0;
            scroing1.setText(String.valueOf(teams1));
            scroing2.setText(String.valueOf(teams2));
        });

    }

    public void click(View btn){

        Log.i(TAG, "click: 111111111111111");

        if(btn.getId()==R.id.btn3a){
            teams1 += 3;
        }else if(btn.getId()==R.id.btn2a){
            teams1 += 2;
        }else if(btn.getId()==R.id.btn1a){
            teams1 ++;
        }else if(btn.getId()==R.id.btn3b){
            teams2 += 3;
        }else if(btn.getId()==R.id.btn2b){
            teams2 += 2;
        }else if(btn.getId()==R.id.btn1b){
            teams2 ++;
        }

        //显示结果
        scroing1.setText(String.valueOf(teams1));
        scroing2.setText(String.valueOf(teams2));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("key1",teams1);
        outState.putInt("key2",teams2);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        teams1 = savedInstanceState.getInt("key1");
        teams2 = savedInstanceState.getInt("key2");
        scroing1.setText(String.valueOf(teams1));
        scroing2.setText(String.valueOf(teams2));
    }
}