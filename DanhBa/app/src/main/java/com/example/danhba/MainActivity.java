package com.example.danhba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.danhba.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ContactAdapter contactAdapter;
    ArrayList<Contact> list_data;
    AppDatabase appDatabase;
    ContactDao contactDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ANhXa();


        list_data = new ArrayList<>();
        appDatabase = AppDatabase.getInstance(MainActivity.this);
        contactDao = appDatabase.contactDao();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                list_data.addAll(contactDao.getAllContact());
                if (list_data.size() == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.noContact.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });

        contactAdapter = new ContactAdapter(list_data, MainActivity.this);
        binding.listContact.setLayoutManager(new LinearLayoutManager(this));
        binding.listContact.setAdapter(contactAdapter);
    }

    private void ANhXa() {

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.listContact.getContext(),
                new LinearLayoutManager(MainActivity.this).getOrientation());
        binding.listContact.addItemDecoration(dividerItemDecoration);

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.title.animate().alpha(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        binding.searchPanel.animate().alpha(1.0f).setDuration(0).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                binding.title.setVisibility(View.GONE);
                                binding.searchPanel.setVisibility(View.VISIBLE);
                            }
                        });
                        ;
                    }
                });
                ;
                binding.editSearch.requestFocus();
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.searchPanel.animate().alpha(0.0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        binding.title.animate().alpha(1.0f).setDuration(0).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                binding.title.setVisibility(View.VISIBLE);
                                binding.searchPanel.setVisibility(View.GONE);
                            }
                        });
                        ;
                    }
                });
                binding.editSearch.setText("");
            }
        });
        binding.editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.editSearch.getText().toString().length() > 0) {
                    binding.clearText.setVisibility(View.VISIBLE);

                    AsyncTask.execute(new Runnable() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void run() {
                            list_data.clear();
                            ArrayList<Contact> contacts = (ArrayList<Contact>) contactDao.getAllContact();
                            for (Contact contact : contacts) {
                                if (contact.getName().toLowerCase().contains(binding.editSearch.getText().toString().trim().toLowerCase())) {
                                    list_data.add(contact);
                                }
                            }
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    if (list_data.size() == 0) {
                                        binding.noContact.setVisibility(View.VISIBLE);
                                    } else {
                                        binding.noContact.setVisibility(View.GONE);
                                    }
                                    contactAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });

                } else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            list_data.clear();
                            list_data.addAll(contactDao.getAllContact());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    contactAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                    binding.clearText.setVisibility(View.INVISIBLE);
                    if(list_data.size() == 0){
                        binding.noContact.setVisibility(View.VISIBLE);
                    }else {
                        binding.noContact.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.editSearch.setText("");
            }
        });

        binding.addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , NewContact.class));
            }
        });

    }
}