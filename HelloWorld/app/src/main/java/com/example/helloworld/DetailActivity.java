package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.helloworld.databinding.ActivityDetailBinding;
import com.example.helloworld.databinding.ActivityMainBinding;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        String data;
        int index;
        Intent intent = getIntent();
        if(intent != null){
             data = intent.getStringExtra("number");
             index = intent.getIntExtra("index",-1);
            binding.editText.setText("" + data);
        }
        else{
             index = -1;
             data = "";
        }
        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("index",index);
                intent.putExtra("data",binding.editText.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}