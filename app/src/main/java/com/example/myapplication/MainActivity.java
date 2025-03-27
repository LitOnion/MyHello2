package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
        private EditText weightEditText, heightEditText;
        private TextView resultTextView, categoryTextView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // 初始化视图组件
            weightEditText = findViewById(R.id.weight);
            heightEditText = findViewById(R.id.height);
            resultTextView = findViewById(R.id.result);
            categoryTextView = findViewById(R.id.category);
            Button calculateButton = findViewById(R.id.calculate);

            // 设置按钮点击监听器
            calculateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calculateBMI();
                }
            });
        }

        private void calculateBMI() {
            // 获取输入值
            String weightStr = weightEditText.getText().toString();
            String heightStr = heightEditText.getText().toString();

            if (!weightStr.isEmpty() && !heightStr.isEmpty()) {
                try {
                    // 转换输入为数值
                    float weight = Float.parseFloat(weightStr);
                    float height = Float.parseFloat(heightStr);

                    if (height > 0) {
                        // 计算BMI
                        float bmi = weight / (height * height);

                        // 显示结果
                        resultTextView.setText(String.format("BMI: %.2f", bmi));
                        categoryTextView.setText(getBMICategory(bmi));
                    } else {
                        showError("身高必须大于0");
                    }
                } catch (NumberFormatException e) {
                    showError("请输入有效数字");
                }
            } else {
                showError("请填写所有字段");
            }
        }

        private String getBMICategory(float bmi) {
            if (bmi < 18.5) {
                return "体重过轻";
            } else if (bmi < 24.9) {
                return "健康体重";
            } else if (bmi < 29.9) {
                return "超重";
            } else {
                return "肥胖";
            }
        }

        private void showError(String message) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            resultTextView.setText("");
            categoryTextView.setText("");
        }
    }