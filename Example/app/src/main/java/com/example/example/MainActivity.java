package com.example.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.example.model.MyModel;
import com.example.example.model.dogbreed;
import com.example.example.room.DogDatabase;
import com.example.example.room.RoomDao;
import com.example.example.view.DogAdapter;
import com.example.example.viewmodel.DogApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private DogAdapter dogAdapter;
    private ArrayList<dogbreed> dogbreeds;
    private DogApiService dogApiService;
    private RecyclerView rvdog;
    private DogDatabase dogDatabase;
    private RoomDao roomDao;
    private Boolean check = false;
    private MyModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvdog = findViewById(R.id.rv_dog);

        dogbreeds = new ArrayList<>();
        dogAdapter = new DogAdapter(dogbreeds);
        rvdog.setAdapter(dogAdapter);
        dogDatabase = DogDatabase.getInstance(this);
        roomDao = dogDatabase.roomDao();
        dogApiService = DogApiService.getInstance(this);
        model = new ViewModelProvider(this).get(MyModel.class);
        model.getList().observe(this, new Observer<List<dogbreed>>() {
            @Override
            public void onChanged(List<dogbreed> dogbreedss) {
                if(dogbreeds.equals(dogbreedss) == false){
                    dogbreeds.clear();
                    for(int i = 0;i < dogbreedss.size() ; i++)
                    {
                        dogbreeds.add(dogbreedss.get(i));
                    }
                    dogAdapter.notifyDataSetChanged();
                }
            }
        });
        dogApiService.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<dogbreed>>() {
                    @Override
                    public void onSuccess(@NonNull List<dogbreed> listdogbreed) {
                        Toast.makeText(MainActivity.this, "Call api success", Toast.LENGTH_SHORT).show();
                            model.AddResult(listdogbreed);
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    for(int i = 0 ; i < listdogbreed.size();i++){
                                    if(roomDao.GetDogByID(listdogbreed.get(i).getId()) == null) {
                                            roomDao.insertAll(listdogbreed.get(i));
                                        }
                                    }
                                }
                            });
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(MainActivity.this,"Call api Error",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}