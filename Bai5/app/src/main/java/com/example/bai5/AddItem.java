package com.example.bai5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bai5.databinding.ActivityAddItemBinding;
import com.example.bai5.databinding.ActivityMainBinding;

public class AddItem extends AppCompatActivity {
    private ActivityAddItemBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddItem.this,MainActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("name",binding.editName.getText().toString());
                intent.putExtra("phone",binding.editPhone.getText().toString());
                intent.putExtra("mail",binding.editMail.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }
}