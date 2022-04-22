package com.example.bai7;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private ArrayList<Contact> contacts;
    private Context context;
    public ContactAdapter(ArrayList<Contact> list_data, Context context) {
        this.contacts = list_data;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.title_name.setText(contact.getFirstname().substring(0,1));
        holder.avatar_text.setText(contact.getFirstname().substring(0,1));
        holder.tv_name.setText(contact.getLastname() + " " + contact.getFirstname());
        if(contact.getAvatar() != null){
            holder.avatar_img.setImageBitmap(BitmapFactory.decodeByteArray(contact.getAvatar(), 0, contact.getAvatar().length));
        }
        else
        {
            holder.avatar_img.setVisibility(View.INVISIBLE);
            holder.avatar_text.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , DetailContact.class);
                intent.putExtra("id" , contact.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView avatar_text;
        ImageView avatar_img;
        TextView title_name;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tv_name = (TextView) view.findViewById(R.id.tx_name);
            avatar_text = (TextView) view.findViewById(R.id.avatar_text);
            avatar_img = (ImageView) view.findViewById(R.id.avatar_img);
            title_name = (TextView) view.findViewById(R.id.txsymbol_name);
        }

    }

}
