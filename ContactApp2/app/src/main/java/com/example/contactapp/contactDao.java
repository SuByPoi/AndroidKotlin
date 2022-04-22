package com.example.contactapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface contactDao {
    @Query("SELECT * FROM contact")
    List<contact> getAll();

    @Query("SELECT * FROM contact WHERE firstName LIKE '%' || :name || '%' OR lastname LIKE '%' || :name || '%' ORDER BY firstname")
    List<contact> findByName(String name);

    @Query("SELECT * FROM contact WHERE id = :id")
    contact findById(int id);

    @Query("UPDATE contact SET  firstName = :fistName, lastName = :lastName, " +
            "phone = :mobile, email = :email,avatar = :avatar WHERE id = :id")
    void update(int id, String fistName, String lastName, String mobile, String email, byte[] avatar);

    @Insert
    void insertAll(contact... contacts);

    @Delete
    void delete(contact contact);
}
