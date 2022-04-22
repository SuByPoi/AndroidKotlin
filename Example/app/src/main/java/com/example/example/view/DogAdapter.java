package com.example.example.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.MainActivity;
import com.example.example.R;
import com.example.example.model.dogbreed;
import com.example.example.viewmodel.DogApi;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder>{
    private List<dogbreed> dogbreeds;
    public DogAdapter(ArrayList<dogbreed> data){
          dogbreeds = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_name;
        TextView tv_decription;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.im_dog);
            tv_name = view.findViewById(R.id.tv_namedog);
            tv_decription = view.findViewById(R.id.tv_decription);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dogitem, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        dogbreed dog = dogbreeds.get(position);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Drawable drawable = Drawable.createFromStream((InputStream) new URL(dog.getUrl()).getContent(),"src");
                    holder.imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.imageView.setImageDrawable(drawable);
                        }
                    });
                    Log.d("debug","succes");
                }
                catch (IOException e)
                {
                    Log.d("debug","error");
                }
            }
        });
        holder.tv_name.setText(dog.getName());
        holder.tv_decription.setText(dog.getTemperament());
    }

    @Override
    public int getItemCount() {
        return dogbreeds.size();
    }
}
