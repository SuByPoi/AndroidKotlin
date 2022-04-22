package com.midterm.nguyenthailoc.viewmodel;

import android.content.Context;

import com.midterm.nguyenthailoc.model.Staff;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StaffApiService {
    private static StaffApiService instance;
    private static StaffApi staffApi;
    private String base_url = "https://api.jsonbin.io/";

    private  StaffApiService(){
        staffApi = new Retrofit.Builder()
                .baseUrl(base_url)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StaffApi.class);
    }
    public static StaffApiService getInstance(Context context) {
        if (instance == null) {
            instance = new StaffApiService();
        }
        return instance;
    }

    public Single<List<Staff>> getStaffs() {
        return staffApi.getStaffs();
    }

}
