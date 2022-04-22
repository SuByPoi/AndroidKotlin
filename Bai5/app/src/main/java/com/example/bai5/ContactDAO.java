package com.example.bai5;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDAO {
    @Query("SELECT * FROM contact")
    List<contact> getAllContacts();

    @Query("SELECT * FROM contact WHERE name LIKE '%' || :name || '%'")
    List<contact> SearchItem(String name);
    @Insert
    void insertAll(contact... contacts);
}
