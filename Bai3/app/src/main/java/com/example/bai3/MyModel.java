package com.example.bai3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MyModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> result;
    public LiveData<ArrayList<String>> getList() {
        if (result == null) {
            result = new MutableLiveData<ArrayList<String>>();
            ArrayList<String> arr = new ArrayList<>();
            result.setValue(arr);
        }
        return result;
    }
    public void AddResult(String a){
        ArrayList<String> arr = (ArrayList<String>) result.getValue();
        arr.add(a);
        result.setValue(arr);
    }
}
