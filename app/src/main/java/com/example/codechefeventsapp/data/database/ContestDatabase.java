package com.example.codechefeventsapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.codechefeventsapp.data.dao.ContestDao;
import com.example.codechefeventsapp.data.models.Contest;

@Database(entities = {Contest.class}, version = 1, exportSchema = false)
public abstract class ContestDatabase extends RoomDatabase {

    private static ContestDatabase instance;

    public abstract ContestDao getContestDao();

    public static synchronized ContestDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, ContestDatabase.class, "contest_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
