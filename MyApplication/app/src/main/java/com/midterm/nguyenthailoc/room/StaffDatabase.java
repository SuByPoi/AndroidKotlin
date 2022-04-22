package com.midterm.nguyenthailoc.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.midterm.nguyenthailoc.model.Staff;

@Database(entities = {Staff.class},version = 1)
public abstract class StaffDatabase extends RoomDatabase {
    public abstract StaffDao roomDao();
    private static StaffDatabase instance;
    public static StaffDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,StaffDatabase.class, "staff_database1")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
//            instance = Room.databaseBuilder(context,DogDatabase.class, "contact_database").build();
        }
        return instance;
    }

}
