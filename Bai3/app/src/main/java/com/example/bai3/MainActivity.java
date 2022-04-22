package com.example.bai3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.bai3.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<String> arrayList;
    private  ArrayAdapter<String> arrayAdapter;
    private MyModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewroot = binding.getRoot();
        setContentView(viewroot);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        binding.viewResult.setAdapter(arrayAdapter);

        model = new ViewModelProvider(this).get(MyModel.class);
        model.getList().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                if(arrayList.equals(strings) == false) {
                    arrayList.clear();
                    for(int i = 0;i < strings.size() ; i++)
                    {
                        arrayList.add(strings.get(i));
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
        binding.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(binding.edtextA.getText().toString());
                int b = Integer.parseInt(binding.edtextB.getText().toString());
                String result = Integer.toString(a+b) ;
                model.AddResult(a + " + " + b + " = " + result);
            }
        });
        binding.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(binding.edtextA.getText().toString());
                int b = Integer.parseInt(binding.edtextB.getText().toString());
                String result = Integer.toString(a-b) ;
                model.AddResult(a + " - " + b + " = " + result);
            }
        });
        binding.btnNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(binding.edtextA.getText().toString());
                int b = Integer.parseInt(binding.edtextB.getText().toString());
                String result = Integer.toString(a*b) ;
                model.AddResult(a + " * " + b + " = " + result);
            }
        });
        binding.btnChia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(binding.edtextA.getText().toString());
                int b = Integer.parseInt(binding.edtextB.getText().toString());
                String result = Integer.toString(a/b) ;
                model.AddResult(a + " / " + b + " = " + result);
            }
        });
    }
}