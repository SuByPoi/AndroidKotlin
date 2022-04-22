package com.example.contactapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class contactAdapter extends RecyclerView.Adapter<contactAdapter.ViewHolder> {
    public ArrayList<contact> contacts;
    private OnItemClickListener listener;
    private Context context;
    public contactAdapter(ArrayList<contact> contacts, Context context) {
        this.context = context;
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contactitem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        contact a = contacts.get(position);
        if(a == null){
            return;
        }
        else
        {
            holder.textView.setText(a.getFirstname() + " " + a.getLastname());
        }

    }

    @Override
    public int getItemCount() {
        if(contacts != null) return contacts.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.idTVContactName);
            imageView = view.findViewById((R.id.idIVContact));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    if (listener != null && index != RecyclerView.NO_POSITION) {
                        listener.onItemClick(contacts.get(index));
                    }
                }
            });
        }


    }
    public interface OnItemClickListener {
        void onItemClick(contact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}


