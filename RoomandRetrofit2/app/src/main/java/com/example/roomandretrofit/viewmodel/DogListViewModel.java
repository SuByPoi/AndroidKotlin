package com.example.roomandretrofit.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.roomandretrofit.model.DogBreed;
import com.example.roomandretrofit.network.DogApi;
import com.example.roomandretrofit.network.DogApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogListViewModel extends ViewModel {
    private MutableLiveData<List<DogBreed>> dogbreeds;
    public DogListViewModel(){
        dogbreeds = new MutableLiveData<>();
    }
    public MutableLiveData<List<DogBreed>> getDogListObserve(){
        return dogbreeds;
    }
    public void makeApiCall(){
        DogApi dogApi = DogApiService.getRetroClient().create(DogApi.class);
        Call<List<DogBreed>> call = dogApi.getDogList();
        call.enqueue(new Callback<List<DogBreed>>() {
            @Override
            public void onResponse(Call<List<DogBreed>> call, Response<List<DogBreed>> response) {
                     dogbreeds.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<DogBreed>> call, Throwable t) {
                     dogbreeds.postValue(null);
            }
        });
    }
}
