package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetActivity extends AppCompatActivity implements Runnable {
    private static final String TAG = "Net";
    private TextView show;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        show = findViewById(R.id.net_show);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 5) {
                    String str = (String) msg.obj;
                    Log.i(TAG, "handleMessage: str=" + str);
                    show.setText(str);
                }
            }
        };
    }

    public void onClick(View btn) {
        Log.i(TAG, "onCreate: start Thread");
        new Thread(this).start();
    }

    @Override
    public void run() {
        Log.i(TAG, "run: 子线程run()......");
        String html = "";
        HttpURLConnection http = null;
        try {
            URL url = new URL("https://www.chinamoney.com.cn/chinese/sddshl/");
            http = (HttpURLConnection) url.openConnection();
            try (InputStream in = http.getInputStream()) {
                html = inputStream2String(in);
                Log.i(TAG,  "run: html=" + html);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (http != null) http.disconnect();
        }

        Message msg = handler.obtainMessage(5, html);
        handler.sendMessage(msg);
    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        try (Reader in = new InputStreamReader(inputStream, "UTF-8")) {
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.append(buffer, 0, bytesRead);
            }
        }
        return out.toString();
    }
}