package com.example.roomandretrofit.network;

import com.example.roomandretrofit.model.DogBreed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogApi {
    @GET("DevTides/DogsApi/master/dogs.json")
    Call<List<DogBreed>> getDogList();
}
