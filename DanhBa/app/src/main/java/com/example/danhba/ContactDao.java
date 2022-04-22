package com.example.danhba;

import android.view.textclassifier.SelectionEvent;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM Contact ORDER BY name ASC")
    List<Contact> getAllContact();

    @Query("UPDATE Contact SET name = :name , phone_number = :phone  , email = :email , avatar = :avatar WHERE id = :id")
    void updateContact(String name , String phone , String email , int id , byte[] avatar);

    @Query("DELETE FROM Contact WHERE id = :id")
    void deleteContact(int id);

    @Insert
    void insertAll(Contact... contacts);


}
