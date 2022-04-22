package com.example.example.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.example.model.dogbreed;

@Database(entities = {dogbreed.class},version = 1)
public abstract  class DogDatabase extends RoomDatabase {
    public abstract RoomDao roomDao();
    private static DogDatabase instance;
    public static DogDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,DogDatabase.class, "contact_database5")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
//            instance = Room.databaseBuilder(context,DogDatabase.class, "contact_database").build();
        }
        return instance;
    }
}
