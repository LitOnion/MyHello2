package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RateActivity extends AppCompatActivity implements Runnable {

    private static final String TAG = "RateActivity";
    private EditText inputRmb;
    private TextView tvResult;
    float dollarRate = 34.5f;
    float euroRate = 666.66f;
    float wonRate = 345f;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputRmb = findViewById(R.id.input_rmb);
        tvResult = findViewById(R.id.result);
        // 读取数据 load from sp
        SharedPreferences sp = getSharedPreferences("myrate", RateActivity.MODE_PRIVATE);
        dollarRate = sp.getFloat("sp_dollar_key",1.23f);
        euroRate = sp.getFloat("sp_euro_key",2.34f);
        wonRate = sp.getFloat("sp_won_key",3.45f);
        Log.i(TAG, "onCreate: from sp dollarRate=" + dollarRate);
        Log.i(TAG, "onCreate: from sp euroRate=" + euroRate);
        Log.i(TAG, "onCreate: from sp wonRate=" + wonRate);

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "handleMessage: 接收消息");
                if(msg.what==7){
                    Bundle bdl2 = (Bundle) msg.obj;
                    dollarRate = bdl2.getFloat("web_dollar");
                    euroRate = bdl2.getFloat("web_euro");
                    wonRate = bdl2.getFloat("web_won");
                    Log.i(TAG, "handleMessage: ret dollarRate = " + dollarRate);
                    Log.i(TAG, "handleMessage: ret euroRate = " + euroRate);
                    Log.i(TAG, "handleMessage: ret wonRate = " + wonRate);
                }
                super.handleMessage(msg);
            }
        };

        // thread
        Log.i(TAG, "onCreate: 启动线程");
        Thread t = new Thread(this);
        t.start();// this.run()

        // Thread t2 = new Thread(new Runnable() {
        //     @Override
        //     public void run() {
        //         Log.i(TAG, "run: Thread 222222222222");
        //     }
        // });
        // t2.start();
        //
        // Thread t3 = new Thread(()->{
        //     Log.i(TAG, "onCreate: thread 333333333333333");
        // });
        // t3.start();
        //
        // new Thread(()->{
        //     Log.i(TAG, "onCreate: thread 444444444444444");
        // }).start();
        //
        // MyTask task = new MyTask();
        // Thread t5 = new Thread(task);
        // // Thread t5 = new Thread(new MyTask());
        // t5.start();//task.run()
        // //new Thread(new MyTask()).start();

    }

    public void myclick(View btn){
        Log.i(TAG, "myclick: 2222222222222");





        //获取输入
        String strInput = inputRmb.getText().toString();

        try {
            //计算 输入x汇率=result
            float inputf = Float.parseFloat(strInput);
            float result = 0;
            if(btn.getId()==R.id.btn_dollar){
                result = inputf * dollarRate;
            }else if (btn.getId()==R.id.btn_euro){
                result = inputf * euroRate;
            }else {
                result = inputf * wonRate;
            }

            // 显示结果
            tvResult.setText(String.valueOf(result));
        } catch (NumberFormatException e) {
            // tvResult.setText("请输入正确的数据");
            Toast.makeText(this, "请输入正确的数据", Toast.LENGTH_SHORT).show();
        }
    }

    ActivityResultLauncher luncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // 处理返回
                if(result.getResultCode()==3){
                    Intent retIntent = result.getData();
                    Bundle retBundle = retIntent.getExtras();
                    dollarRate = retBundle.getFloat("ret_dollar");
                    euroRate = retBundle.getFloat("ret_euro");
                    wonRate = retBundle.getFloat("ret_won");
                    Log.i(TAG, "onActivityResult: dollarRate=" + dollarRate);
                    Log.i(TAG, "onActivityResult: euroRate=" + euroRate);
                    Log.i(TAG, "onActivityResult: wonRate=" + wonRate);

                    // save to sp
                    SharedPreferences sp = getSharedPreferences("myrate", RateActivity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putFloat("sp_dollar_key",dollarRate);
                    editor.putFloat("sp_euro_key",euroRate);
                    editor.putFloat("sp_won_key",wonRate);
                    editor.apply();
                    Log.i(TAG, "save to sp");
                }
            });


    public void openConfig(View btn) {
        // //打开新窗口
        Intent intent = new Intent(this, SettingActivity.class);
        intent.putExtra("key_dollar", dollarRate);
        intent.putExtra("key_euro", euroRate);
        intent.putExtra("key_won", wonRate);

        Log.i(TAG, "openConfig: dollarRate=" + dollarRate);
        Log.i(TAG, "openConfig: euroRate=" + euroRate);
        Log.i(TAG, "openConfig: wonRate=" + wonRate);

        // startActivityForResult(intent,6);
        luncher.launch(intent);
    }

    @Override
    public void run() {
        Log.i(TAG, "run: running.........");
        //忙了很久
        Bundle retbdl = new Bundle();
        try {
            Thread.sleep(2000);

            URL url = null;
            try {
                // url = new URL("https://www.boc.cn/sourcedb/whpj/");
                // HttpURLConnection http = (HttpURLConnection) url.openConnection();
                // InputStream in = http.getInputStream();
                //
                // String html = inputStream2String(in);
                // Log.i(TAG, "run: html=" + html);


                Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
                Log.i(TAG, "run: title = " + doc.title());

                Elements tables = doc.getElementsByTag("table");
                Element table = tables.get(1);
                Elements trs = table.getElementsByTag("tr");
                trs.remove(0);
                for(Element tr : trs){
                    Elements tds = tr.children();
                    Element td1 = tds.first();
                    Element td2 = tds.get(5);
                    String str1 = td1.text();
                    String str2 = td2.text();

                    Log.i(TAG, "run: " + str1 + " ==> " + str2);
                    float r = 100/Float.parseFloat(str2);
                    if("美元".equals(str1)){
                        retbdl.putFloat("web_dollar",r);
                    }else if("欧元".equals(str1)){
                        retbdl.putFloat("web_euro",r);
                    }else if("韩国元".equals(str1)){
                        retbdl.putFloat("web_won",r);
                    }
                }

            } catch (
                    MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 向主线程发送消息

        Message msg = handler.obtainMessage(7,retbdl);
        // msg.what = 7;
        // msg.obj = retbdl;
        handler.sendMessage(msg);
        Log.i(TAG, "run: 消息发送完毕");
    }

    private String inputStream2String(InputStream inputStream)
            throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "UTF-8");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();

    }


    // @Override
    // protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    //     if(requestCode==6 && resultCode==3){
    //         // 从data获取带回的数据
    //         Bundle ret = data.getExtras();
    //         dollarRate = ret.getFloat("ret_dollar");
    //         euroRate = ret.getFloat("ret_euro");
    //         wonRate = ret.getFloat("ret_won");
    //         Log.i(TAG, "onActivityResult: dollarRate=" + dollarRate);
    //         Log.i(TAG, "onActivityResult: euroRate=" + euroRate);
    //         Log.i(TAG, "onActivityResult: wonRate=" + wonRate);
    //     }
    //     super.onActivityResult(requestCode, resultCode, data);
    //
    //
    // }
}