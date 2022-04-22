package com.example.roomandretrofit.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomandretrofit.R;
import com.example.roomandretrofit.model.DogBreed;

import java.io.IOException;
import java.io.InputStream;
import java.net.ContentHandler;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder>{
    private List<DogBreed> dogBreeds;
    private Context context;
    public DogAdapter(Context context,List<DogBreed> dogBreeds){
        this.context = context;
        this.dogBreeds = dogBreeds;
    }
    public void setDogList(List<DogBreed> dogBreeds){
        this.dogBreeds = dogBreeds;
        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txname;
        private TextView txdecription;


        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.im_dog);
            txname = (TextView) view.findViewById((R.id.tx_name));
            txdecription = (TextView) view.findViewById((R.id.tx_decription));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
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
        holder.txdecription.setText(dogBreed.getTemperament());

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
