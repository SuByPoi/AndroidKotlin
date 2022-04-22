package com.example.viewdog.viewmodel;

import android.content.Context;

import com.example.viewdog.model.DogBreed;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogsApiServie {
    private static final String BASE_URL = "https://raw.githubusercontent.com/";
    private static DogsApi api;
    private static DogsApiServie instance;

    private DogsApiServie() {
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(DogsApi.class);
    }

    public static DogsApiServie getInstance(Context context) {
        if (instance == null) {
            instance = new DogsApiServie();
        }
        return instance;
    }

    public Single<List<DogBreed>> getDogs() {
        return api.getDogs();
    }
}
