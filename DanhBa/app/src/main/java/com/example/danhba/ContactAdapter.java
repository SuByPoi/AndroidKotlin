package com.example.danhba;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.viewHolder> {
    ArrayList<Contact> list_data;
    Context context;

    public ContactAdapter(ArrayList<Contact> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.contact,parent  ,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Contact contact = list_data.get(position);
        holder.tv_name.setText(contact.getName());
        holder.avatar_text.setText(contact.getName().substring(0, 1));
        holder.title_name.setText(contact.getName().substring(0, 1));


        if(contact.getAvatar() != null){
            holder.avatar_img.setImageBitmap(BitmapFactory.decodeByteArray(contact.getAvatar(), 0, contact.getAvatar().length));
        }else{
            holder.avatar_img.setVisibility(View.INVISIBLE);
            holder.avatar_text.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , DetailAccount.class);
                intent.putExtra("contact" , contact);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView avatar_text;
        ImageView avatar_img;
        TextView title_name;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.name);
            avatar_text = itemView.findViewById(R.id.avatar_text);
            avatar_img = itemView.findViewById(R.id.avatar_img);
            title_name = itemView.findViewById(R.id.title_name);
        }
    }
}
