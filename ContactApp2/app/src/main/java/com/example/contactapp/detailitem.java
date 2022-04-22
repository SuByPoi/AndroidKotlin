package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.contactapp.databinding.ActivityDetailitemBinding;
import com.example.contactapp.databinding.ActivityMainBinding;

public class detailitem extends AppCompatActivity {
    private ActivityDetailitemBinding binding;
    private int id;
    private contactDao contactdao;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailitemBinding.inflate(getLayoutInflater());
        View viewroot = binding.getRoot();
        setContentView(viewroot);

        appDatabase = AppDatabase.getInstance(this);
        contactdao = appDatabase.contactdao();
        Intent intent = getIntent();
        if(intent != null){
              id = intent.getIntExtra("id",-1);
        }
        contact a = contactdao.findById(id);

        if(a.getAvatar() != null){
                binding.imPhoto.setImageBitmap(BitmapHelper.byteArrayToBitmap(a.getAvatar()));
        }
        binding.txName.setText(a.getFirstname() + " " + a.getLastname());
        binding.txPhone.setText(a.getPhone());
        binding.txGmail.setText(a.getEmail());

    }
}