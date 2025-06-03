package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "bmi";

    private EditText inputWeight,inputHeight;
    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 初始化控件
        inputHeight = findViewById(R.id.inph);
        inputWeight = findViewById(R.id.inpw);
        show = findViewById(R.id.result);

    }

    public void click(View v){
        Log.i(TAG, "click: 1111111111");
        //获取用户输入数据
        String strh = inputHeight.getText().toString();
        String strw = inputWeight.getText().toString();
        Log.i(TAG, "click: h=" + strh);
        Log.i(TAG, "click: w=" + strw);


        try {
            //类型转换 String -> float/double
            float h = Float.parseFloat(strh);
            float w = Float.parseFloat(strw);
            float bmi = w/(h*h);

            //判断并给出结论
            if(bmi<18){
                show.setText(String.format("BMI=%.2f %s",bmi,"多吃点"));
            }else if (bmi<22){
                show.setText(String.format("BMI=%.2f %s",bmi,"要保持哟"));
            }else if (bmi<28){
                show.setText(String.format("BMI=%.2f %s",bmi,"不要太胖了"));
            }else{
                show.setText(String.format("BMI=%.2f %s",bmi,"开心就好"));
            }
        } catch (NumberFormatException e) {
            show.setText("请输入完成数据后计算");
        }
    }
    //添加了一行内容

}