package com.example.danhba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danhba.databinding.ActivityDetailAccountBinding;

public class DetailAccount extends AppCompatActivity {


    ActivityDetailAccountBinding binding;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contact = (Contact) getIntent().getSerializableExtra("contact");
        setTitle("");
        AnhXa();
    }

    private void AnhXa() {
        binding.name.setText(contact.getName());
        binding.phoneNumber.setText(contact.getPhone_number());
        binding.email.setText(contact.getEmail());

        if (contact.getAvatar() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getAvatar(), 0, contact.getAvatar().length);
            binding.avatar.setImageBitmap(bitmap);
            binding.avatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        menu.add("Delete");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getTitle().equals("Delete")) {

            final Dialog dialog = new Dialog(DetailAccount.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.custom_dialog);

            TextView yes = dialog.findViewById(R.id.yes);
            TextView no = dialog.findViewById(R.id.no);
            dialog.show();
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase.getInstance(DetailAccount.this).contactDao().deleteContact(contact.getId());
                        }
                    });
                    Intent intent = new Intent(DetailAccount.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });


        } else if (item.getTitle().equals("edit")) {
            Intent intent = new Intent(DetailAccount.this, NewContact.class);
            intent.putExtra("contact", contact);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}