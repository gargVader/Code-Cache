package com.example.codechefeventsapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.codechefeventsapp.data.models.Event;

import java.util.List;

@Dao
public interface EventDao {

    @Query("SELECT * FROM event_table")
    LiveData<List<Event>> getAllEvents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Event event);

    @Delete
    void delete(Event event);

    @Update
    void update(Event event);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Event> eventList);

    @Query("DELETE FROM event_table")
    void deleteAll();


}
