package com.example.bai7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.bai7.databinding.ActivityMainBinding;
import com.example.bai7.databinding.DetailContactBinding;

public class DetailContact extends AppCompatActivity {
    private DetailContactBinding binding;
    Contact contact;
    ContactDao contactDao;
    AppDatabase appDatabase;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = DetailContactBinding.inflate(getLayoutInflater());
        View viewroot = binding.getRoot();
        setContentView(viewroot);



//        contact = (Contact) getIntent().getSerializableExtra("contact");
        id = getIntent().getIntExtra("id",-1);
        appDatabase = AppDatabase.getInstance(this);
        contactDao = appDatabase.contactDao();
        contact = contactDao.getContactByid(id);
        if(contact != null) {
            binding.txName.setText(contact.getLastname() + " " + contact.getFirstname());
            binding.txMail.setText(contact.getEmail());
            binding.phoneNumber.setText(contact.getPhone());
            if (contact.getAvatar() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getAvatar(), 0, contact.getAvatar().length);
                binding.imPerson.setImageBitmap(bitmap);
                binding.imPerson.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(DetailContact.this,FormContact.class);
                in.putExtra("id",contact.getId());
                startActivity(in);
            }
        });
    }
}