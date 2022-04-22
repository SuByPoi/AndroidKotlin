package com.example.helloworld;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> arrayNumber;
    public LiveData<ArrayList<String>> getArray() {
        if (arrayNumber == null) {
            arrayNumber = new MutableLiveData<ArrayList<String>>();
            ArrayList<String> arr = new ArrayList<>();
            arr.add("0");
            arrayNumber.setValue(arr);
        }
        return arrayNumber;
    }
    public void addNumber(String number) {
        ArrayList<String> arr = (ArrayList<String>) arrayNumber.getValue();
        arr.add(number);
        arrayNumber.setValue(arr);
    }

    public void delNumber(int index) {
        ArrayList<String> arr = (ArrayList<String>) arrayNumber.getValue();
        arr.remove(index);
        arrayNumber.setValue(arr);
    }

    public void updateNumber(int index, String value) {
        ArrayList<String> arr = (ArrayList<String>) arrayNumber.getValue();
        arr.set(index, value);
        arrayNumber.setValue(arr);
    }
}
