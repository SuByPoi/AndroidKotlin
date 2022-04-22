package com.midterm.nguyenthailoc.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyModel extends ViewModel {
    private MutableLiveData<List<Staff>> result;
    public LiveData<List<Staff>> getList() {
        if (result == null) {
            result = new MutableLiveData<List<Staff>>();
            ArrayList<Staff> arr = new ArrayList<>();
            result.setValue(arr);
        }
        return result;
    }
    public void AddResult(List<Staff> a){
        List<Staff> arr = (List<Staff>) result.getValue();
        arr.addAll(a);
        result.setValue(arr);
    }
}
