package com.example.bai7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bai7.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<Contact> contacts;
    private ContactAdapter contactAdapter;
    private ContactDao contactDao;
    private AppDatabase appDatabase;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_id);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here of search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == ""){
                    contacts.clear();
                    contacts.addAll(contactDao.getAll());
                    contactAdapter.notifyDataSetChanged();
                    binding.rvContact.setAdapter(contactAdapter);
                }
                else
                {
                    contacts.clear();
                    contacts.addAll(contactDao.findByName(newText));
                    contactAdapter.notifyDataSetChanged();
                    binding.rvContact.setAdapter(contactAdapter);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewroot = binding.getRoot();
        setContentView(viewroot);


        contacts = new ArrayList<>();
        appDatabase = AppDatabase.getInstance(this);
        contactDao = appDatabase.contactDao();
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvContact.getContext(),
//                new LinearLayoutManager(MainActivity.this).getOrientation());
//        binding.rvContact.addItemDecoration(dividerItemDecoration);
        //contactDao.insertAll(new Contact("Loc","Nguyen Thai","0905421172","thailoc@gmail.com",null));
        contacts.clear();
        contacts.addAll(contactDao.getAll());
        contactAdapter = new ContactAdapter(contacts,this);
        binding.rvContact.setLayoutManager(new LinearLayoutManager(this));
        binding.rvContact.setAdapter(contactAdapter);
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MainActivity.this,FormContact.class);
                startActivity(in);
            }
        });
    }
}