package com.example.helloworld;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import  androidx.lifecycle.MutableLiveData;

import java.lang.reflect.Array;

public class MyViewModel extends ViewModel {
    private MutableLiveData<Integer> number;
    public LiveData<Integer> getNumber() {
        if (number == null) {
            number = new MutableLiveData<Integer>();
            number.setValue(0);
        }
        return number;
    }
    public void increaseNumber(){
        number.setValue(number.getValue() + 1);
    }
    public void increaseNumber2(){
        number.setValue(number.getValue() - 1);
    }
}
