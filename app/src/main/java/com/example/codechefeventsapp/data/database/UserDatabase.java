package com.example.codechefeventsapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.codechefeventsapp.data.dao.UserDao;
import com.example.codechefeventsapp.data.models.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;
    public abstract UserDao getUserDao();

    public static synchronized UserDatabase getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context, UserDatabase.class, "user_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
