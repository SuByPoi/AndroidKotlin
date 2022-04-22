package com.example.roomandretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.roomandretrofit.adapter.DogAdapter;
import com.example.roomandretrofit.model.DogBreed;
import com.example.roomandretrofit.viewmodel.DogListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<DogBreed> dogbreeds;
    private DogAdapter dogAdapter;
    private DogListViewModel dogListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_dog);
        LinearLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        DogAdapter dogAdapter = new DogAdapter(this,dogbreeds);
        recyclerView.setAdapter(dogAdapter);
        dogListViewModel = ViewModelProviders.of(this).get(DogListViewModel.class);
        dogListViewModel.getDogListObserve().observe(this, new Observer<List<DogBreed>>() {
            @Override
            public void onChanged(List<DogBreed> dogBreeds) {
                if(dogBreeds != null){
                    dogbreeds = dogBreeds;
                    dogAdapter.setDogList(dogBreeds);
                }
            }
        });
    }
}