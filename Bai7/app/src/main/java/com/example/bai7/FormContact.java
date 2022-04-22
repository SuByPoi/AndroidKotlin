package com.example.bai7;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.bai7.databinding.ActivityMainBinding;
import com.example.bai7.databinding.FormContactBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FormContact extends AppCompatActivity {
    private FormContactBinding binding;
    Bitmap bitmap;
    boolean checkEdit = false;
    Contact contact;
    ContactDao contactDao;
    AppDatabase appDatabase;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = FormContactBinding.inflate(getLayoutInflater());
        View viewroot = binding.getRoot();
        setContentView(viewroot);



        id = getIntent().getIntExtra("id",-1);
        if(id != -1){
        appDatabase = AppDatabase.getInstance(this);
        contactDao = appDatabase.contactDao();
        contact = contactDao.getContactByid(id);}
        else{
            contact = (Contact) getIntent().getSerializableExtra("contact");
        }

        if(contact != null){
               checkEdit = true;
               EditContact();
        }

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
        binding.addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] change_photo = {"Take photo" , "Choose photo"};

                AlertDialog.Builder builder = new AlertDialog.Builder(FormContact.this);
                builder.setTitle("Change photo");
                builder.setItems(change_photo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            Intent intent = new Intent();
                            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
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
                } else {
                    byte[] byteArray = null;
                    if(bitmap != null){
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteArray = stream.toByteArray();
                        bitmap.recycle();
                    }
                    Contact contact = new Contact(first_name , last_name, phone_number, email , byteArray);
                    if(checkEdit){
                        AppDatabase.getInstance(FormContact.this).contactDao().updateContact(first_name , last_name
                                , phone_number , email , FormContact.this.contact.getId() , byteArray);

                    }else{
                        AppDatabase.getInstance(FormContact.this).contactDao().insertAll(contact);
                    }
//                    AsyncTask.execute(new Runnable() {
//                        @Override
//                        public void run() {
//
//
//                        }
//                    });
                    Intent intent = new Intent(FormContact.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
    public void EditContact(){
        binding.firstName.setText(contact.getFirstname());
        binding.lastName.setText(contact.getLastname());
        binding.phoneNumber.setText(contact.getPhone());
        binding.email.setText(contact.getEmail());
        if(contact.getAvatar() != null){
            binding.avatar.setImageBitmap(BitmapFactory.decodeByteArray(contact.getAvatar(), 0, contact.getAvatar().length));
            binding.avatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }
}