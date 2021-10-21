package com.example.codechefeventsapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.codechefeventsapp.data.dao.ContestDao;
import com.example.codechefeventsapp.data.dao.EventDao;
import com.example.codechefeventsapp.data.dao.UserDao;
import com.example.codechefeventsapp.data.models.Contest;
import com.example.codechefeventsapp.data.models.Event;
import com.example.codechefeventsapp.data.models.User;

@Database(entities = {Event.class, Contest.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ContestDao getContestDao();
    public abstract EventDao getEventDao();
    public abstract UserDao getUserDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
