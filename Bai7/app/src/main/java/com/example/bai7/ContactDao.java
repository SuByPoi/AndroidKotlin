package com.example.bai7;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    List<Contact> getAll();
    @Query("SELECT * FROM Contact WHERE id = :id")
    Contact getContactByid(int id);

    @Query("SELECT * FROM Contact WHERE firstName LIKE '%' || :name || '%' OR lastName LIKE '%' || :name || '%' ORDER BY firstName")
    List<Contact> findByName(String name);

    @Query("UPDATE Contact SET firstname = :firstname, lastname = :lastname , phone = :phone  , email = :email , avatar = :avatar WHERE id = :id")
    void updateContact(String firstname,String lastname , String phone , String email , int id , byte[] avatar);
    @Insert
    void insertAll(Contact... contacts);

    @Delete
    void deleteAll(Contact contact);
}
