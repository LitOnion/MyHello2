package com.example.myapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends ListActivity {

    private static final String TAG = "RateActivity";
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        String[] list_data = {"one","tow","three","four"};
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list_data);
        setListAdapter(adapter);

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                // 接收返回的数据项
                if(msg.what==9){
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter2 = new ArrayAdapter<String>(RateListActivity.this, android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter2);
                }

                super.handleMessage(msg);
            }
        };

        Thread t = new Thread(()->{
            //获取数据，带回到主线程
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            List<String> list1 = new ArrayList<String>();
            for(int i=10;i<100;i++){
                list1.add("item" + i);
            }
            //带回汇率，解析后的

            Log.i(TAG, "onCreate: 返回数据");
            Message msg = handler.obtainMessage(9,list1);
            handler.sendMessage(msg);
        });
        t.start();
    }
}