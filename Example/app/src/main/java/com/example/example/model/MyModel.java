package com.example.example.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyModel extends ViewModel {
    private MutableLiveData<List<dogbreed>> result;
    public LiveData<List<dogbreed>> getList() {
        if (result == null) {
            result = new MutableLiveData<List<dogbreed>>();
            ArrayList<dogbreed> arr = new ArrayList<>();
            result.setValue(arr);
        }
        return result;
    }
    public void AddResult(List<dogbreed> a){
        List<dogbreed> arr = (List<dogbreed>) result.getValue();
        arr.addAll(a);
        result.setValue(arr);
    }
}
