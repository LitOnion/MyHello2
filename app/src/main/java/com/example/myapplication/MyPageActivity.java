package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);
        MyPageAdapter pageAdapter = new MyPageAdapter(this);
        viewPager2.setAdapter(pageAdapter);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        // new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
        //     @Override
        //     public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        //         //tab.setText(new String[]{"Monday","Tuesday","Wednesday"}[position]);
        //         tab.setText("Monday,Tuesday,Wednesday".split(",")[position]);
        //     }
        // }).attach();
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position)-> {
            //tab.setText(new String[]{"Monday","Tuesday","Wednesday"}[position]);
            tab.setText("Monday,Tuesday,Wednesday".split(",")[position]);
        }).attach();
    }
}