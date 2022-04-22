package com.example.viewdog.view;

import android.os.Bundle;

import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.viewdog.R;
import com.example.viewdog.databinding.FragmentDetailBinding;
import com.example.viewdog.model.DogBreed;


public class DetailFragment extends Fragment {
    private DogBreed dog;
    FragmentDetailBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dog = (DogBreed) getArguments().getSerializable("dogBreed");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_detail, null, false);
        binding.setDog(dog);
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }
}