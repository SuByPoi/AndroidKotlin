package com.example.example.viewmodel;


import android.content.Context;

import com.example.example.model.dogbreed;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogApiService {
    private static DogApiService instance;
    private static DogApi dogApi;
    private String base_url = "https://raw.githubusercontent.com/";

    private  DogApiService(){
        dogApi = new Retrofit.Builder()
                .baseUrl(base_url)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DogApi.class);
    }
    public static DogApiService getInstance(Context context) {
        if (instance == null) {
            instance = new DogApiService();
        }
        return instance;
    }

    public Single<List<dogbreed>> getDogs() {
        return dogApi.getDogs();
    }
}
