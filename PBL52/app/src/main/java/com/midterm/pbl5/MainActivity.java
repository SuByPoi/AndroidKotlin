package com.midterm.pbl5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private String URL = "192.168.1.130:80";
    OkHttpClient client;

    Button on;
    Button off;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        on = findViewById(R.id.btn_on);
        off = findViewById(R.id.btn_off);

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TurnLed(1);
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TurnLed(0);
            }
        });




    }
    public void TurnLed(int i){

        Request request = new Request.Builder().url(URL)
                .post(RequestBody.create(JSON, createJSON(i)))
                .build();
        client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("debug1", "TurnOn");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("debug2", "TurnOff");
            }
        });

    }
    private String createJSON(int i) {
        return "{\"turn\": ["+i+"]}";
    }
}