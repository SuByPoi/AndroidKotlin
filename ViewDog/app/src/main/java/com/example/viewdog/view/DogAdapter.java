package com.example.viewdog.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewdog.R;
import com.example.viewdog.model.DogBreed;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.http.Url;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder>  {
    private List<DogBreed> dogBreeds;

    public DogAdapter(ArrayList<DogBreed> data){
            dogBreeds = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_dog_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DogBreed dogBreed = dogBreeds.get(position);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Drawable drawable = Drawable.createFromStream((InputStream) new URL(dogBreed.getUrl()).getContent(),"src");
                    holder.imageView.post(new Runnable() {
                        @Override
                        public void run() {
                             holder.imageView.setImageDrawable(drawable);
                        }
                    });
                }
                catch (IOException e)
                {
                     e.printStackTrace();
                }
            }
        });
        holder.txname.setText(dogBreed.getName());
        holder.txdecription.setText(dogBreed.getBredFor());
    }

    @Override
    public int getItemCount() {
        return dogBreeds.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txname;
        private TextView txdecription;

        public ViewHolder(@NonNull View itemview) {
            super(itemview);
            // Define click listener for the ViewHolder's View

            imageView = (ImageView) itemview.findViewById(R.id.im_dog);
            txname = (TextView) itemview.findViewById((R.id.tx_name));
            txdecription = (TextView) itemview.findViewById((R.id.tx_decription));
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DogBreed dogBreed = dogBreeds.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("DogBreed",dogBreed);
                    Navigation.findNavController(view).navigate(R.id.detailFragment);
                }
            });
        }
    }

}
