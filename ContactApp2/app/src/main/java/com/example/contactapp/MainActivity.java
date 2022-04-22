package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.contactapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private AppDatabase appDatabase;
    private contactDao contactdao;
    private ArrayList<contact> contacts;
    private contactAdapter contactadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewroot = binding.getRoot();
        setContentView(viewroot);

        appDatabase = AppDatabase.getInstance(this);
        contactdao = appDatabase.contactdao();
        contacts = new ArrayList<>();
        contactadapter = new contactAdapter(contacts,this);
        binding.rvContact.setLayoutManager(new LinearLayoutManager(this));

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                contacts.clear();
                contacts.addAll(contactdao.getAll());
                contactadapter.notifyDataSetChanged();
            }
        });
        binding.rvContact.setAdapter(contactadapter);

        contactadapter.setOnItemClickListener(new contactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(contact contact) {
                Intent in = new Intent(MainActivity.this,detailitem.class);
                in.putExtra("id",contact.getId());
                startActivity(in);
            }
        });

    }
}