package com.example.admin.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.admin.data.dao.EventDao;
import com.example.admin.data.models.Event;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class EventDatabase extends RoomDatabase {

    private static EventDatabase instance;
    public abstract EventDao getEventDao();

    public static synchronized EventDatabase getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context, EventDatabase.class, "event_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }



}
