package com.example.example.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.example.model.dogbreed;

import java.util.List;

@Dao
public interface RoomDao {
    @Query("SELECT * FROM dogbreed")
    List<dogbreed> getAll();

    @Query("SELECT * FROM dogbreed WHERE id=:id")
    dogbreed GetDogByID(int id);

    @Delete
    void delete(dogbreed dogBreed);

    @Insert
    void insertAll(dogbreed... dogbreeds);
}
