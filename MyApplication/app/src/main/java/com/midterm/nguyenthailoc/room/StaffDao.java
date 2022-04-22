package com.midterm.nguyenthailoc.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.midterm.nguyenthailoc.model.Staff;

import java.util.List;

@Dao
public interface StaffDao {
    @Query("SELECT * FROM Staff")
    List<Staff> getAll();

    @Query("SELECT * FROM Staff WHERE id=:id")
    Staff GetDogByID(int id);

    @Query("SELECT * FROM Staff WHERE `desc` LIKE '%' || :desc || '%'")
    List<Staff> findByDesc(String desc);

    @Query("SELECT * FROM Staff WHERE `desc` = :desc")
    Staff getStaffBydesc(String desc);

    @Delete
    void delete(Staff staff);

    @Insert
    void insertAll(Staff... staff);
}
