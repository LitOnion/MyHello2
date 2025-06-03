package com.example.myapplication;

import static kotlin.text.Typography.dollar;
import static kotlin.text.Typography.euro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class config extends AppCompatActivity {

    private static final String TAG="Rate";
    private EditText dollarText;
    private EditText euroText;
    private EditText wonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_config);

        Intent intent = getIntent();
        float dollar =intent.getFloatExtra("dollar_rate_key",0.0f);
        float euro =intent.getFloatExtra("euro_rate_key",1.0f);
        float won =intent.getFloatExtra("won_rate_key",2.0f);

        Log.i(TAG,"onCreate:dollar="+dollar);
        Log.i(TAG,"onCreate:euro="+euro);
        Log.i(TAG,"onCreate:won="+won);

        dollarText = findViewById(R.id.dollar_edit);
        euroText = findViewById(R.id.euro_edit);
        wonText = findViewById(R.id.won_edit);

        dollarText.setText(String.valueOf(dollar));
        euroText.setText(String.valueOf(euro));
        wonText.setText(String.valueOf(won));
    }

    public void save(View btn){
        Log.i(TAG,"save:");

        String dollarStr = dollarText.getText().toString();
        String euroStr = euroText.getText().toString();
        String wonStr = wonText.getText().toString();

        Log.i(TAG,"save:dollarStr="+dollarStr);
        Log.i(TAG,"save:euroStr="+euroStr);
        Log.i(TAG,"save:wonStr="+wonStr);

        Intent intent = getIntent();

        Bundle bundle = new Bundle();
        bundle.putFloat("key_dollar2",Float.parseFloat(dollarStr));
        bundle.putFloat("key_dollar2",Float.parseFloat(dollarStr));
        bundle.putFloat("key_dollar2",Float.parseFloat(dollarStr));
        intent.putExtras(bundle);

        setResult(6,intent);

        finish();
    }
}
