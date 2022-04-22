package com.example.bai5;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bai5.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ArrayList<contact> contacts;
    private ContactsAdapter contactsAdapter;
    private AppDatabase appDatabase;
    private ContactDAO contactDao;

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
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            contacts.addAll(contactDao.getAllContacts());
                        }
                    });
                    contactsAdapter.notifyDataSetChanged();
                }
                else
                {
                    contacts.clear();
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            contacts.addAll(contactDao.SearchItem(newText));
                        }
                    });
                    contactsAdapter.notifyDataSetChanged();
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

        appDatabase = AppDatabase.getInstance(this);
        contactDao = appDatabase.contactDao();

        contactsAdapter = new ContactsAdapter();
        contacts = new ArrayList<>();


        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            String name = data.getStringExtra("name");
                            String phone = data.getStringExtra("phone");
                            String mail = data.getStringExtra("mail");
                            contact cn = new contact(name,phone,mail);
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    contactDao.insertAll(cn);
                                }
                            });
                            contacts.clear();
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    contacts.addAll(contactDao.getAllContacts());
                                }
                            });
                            contactsAdapter.notifyDataSetChanged();
                        }
                    }
                }
        );



        binding.reView.setLayoutManager(new LinearLayoutManager(this));


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                contacts.addAll(contactDao.getAllContacts());
                contactsAdapter.setData(contacts);
            }
        });
        binding.reView.setAdapter(contactsAdapter);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddItem.class);
                resultLauncher.launch(intent);
            }
        });


    }
}