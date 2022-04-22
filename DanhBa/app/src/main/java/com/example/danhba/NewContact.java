package com.example.danhba;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Insert;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.danhba.databinding.ActivityNewContactBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class NewContact extends AppCompatActivity {
    ActivityNewContactBinding binding;
    Contact contact;
    boolean checkEdit = false;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AnhXa();

        contact = (Contact) getIntent().getSerializableExtra("contact");

        if(contact != null){
            EditContact();
            checkEdit = true;
        }


    }

    @SuppressLint("SetTextI18n")
    private void EditContact() {
        binding.title.setText("Edit contact");
        String[] strings = contact.getName().split(" ");
        String first_name = "";
        String last_name = "";
        for (int i = 0; i < strings.length - 1; i++) {
            first_name += strings[i] + " ";
        }
        last_name = strings[strings.length - 1];
        binding.firstName.setText(first_name.trim());
        binding.lastName.setText(last_name.trim());
        binding.phoneNumber.setText(contact.getPhone_number());
        binding.email.setText(contact.getEmail());

        if(contact.getAvatar() != null){
            bitmap = BitmapFactory.decodeByteArray(contact.getAvatar(), 0, contact.getAvatar().length);
            binding.avatar.setImageBitmap(bitmap);
            binding.avatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    private void AnhXa() {
        ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            assert data != null;
                            Uri uri = data.getData();
                            if(uri != null){

                                InputStream inputStream = null;
                                try {
                                    inputStream = getContentResolver().openInputStream(uri);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                bitmap = BitmapFactory.decodeStream(inputStream);


                            }else{
                                bitmap = (Bitmap) data.getExtras().get("data");
                            }

                            binding.avatar.setImageBitmap(bitmap);
                            binding.avatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        }
                    }
                });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] change_photo = {"Take photo" , "Choose photo"};

                AlertDialog.Builder builder = new AlertDialog.Builder(NewContact.this);
                builder.setTitle("Change photo");
                builder.setItems(change_photo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            launchSomeActivity.launch(intent);
                        }else {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            launchSomeActivity.launch(intent);
                        }
                    }
                });
                builder.show();
            }
        });

        binding.saveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String first_name = binding.firstName.getText().toString().trim();
                String last_name = binding.lastName.getText().toString().trim();
                String phone_number = binding.phoneNumber.getText().toString().trim();
                String email = binding.email.getText().toString().trim();
                if (first_name.isEmpty()) {
                    binding.firstName.setError("Please enter first name");
                } else if (last_name.isEmpty()) {
                    binding.lastName.setError("Please enter last name");
                } else if (phone_number.isEmpty()) {
                    binding.phoneNumber.setError("Please enter phone number");
                } else if (email.isEmpty()) {
                    binding.email.setError("Please enter email");
                } else if (!isEmailValid(email)) {
                    binding.email.setError("Email is invalid");
                } else {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            byte[] byteArray = null;
                            if(bitmap != null){
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byteArray = stream.toByteArray();
                                bitmap.recycle();
                            }
                            Contact contact = new Contact(first_name + " " + last_name, phone_number, email , byteArray);
                            if(checkEdit){
                                AppDatabase.getInstance(NewContact.this).contactDao().updateContact(first_name + " " + last_name
                                        , phone_number , email , NewContact.this.contact.getId() , byteArray);

                            }else{
                                AppDatabase.getInstance(NewContact.this).contactDao().insertAll(contact);
                            }

                        }
                    });
                    Intent intent = new Intent(NewContact.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        binding.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bitmap != null){
                    String[] change_photo = {"Remove photo" , "Take new photo" , "Choose new photo"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(NewContact.this);
                    builder.setTitle("Change photo");
                    builder.setItems(change_photo, new DialogInterface.OnClickListener() {
                        @SuppressLint("UseCompatLoadingForDrawables")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which == 0){
                                bitmap = null;
                                binding.avatar.setImageDrawable(NewContact.this.getResources().getDrawable(R.drawable.ic_baseline_person_24));
                            }else if(which == 1) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                launchSomeActivity.launch(intent);
                            }else{
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                launchSomeActivity.launch(intent);
                            }
                        }
                    });
                    builder.show();
                }
            }
        });
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}