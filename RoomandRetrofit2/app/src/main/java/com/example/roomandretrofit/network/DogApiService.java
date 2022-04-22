package com.example.roomandretrofit.network;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogApiService {

    public static String BASE_URL = "https://raw.githubusercontent.com/";
    public static Retrofit retrofit;
    public static Retrofit getRetroClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return  retrofit;
    }
}
