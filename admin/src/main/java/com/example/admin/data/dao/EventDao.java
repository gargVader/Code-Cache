package com.example.admin.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.admin.data.models.Event;

import java.util.List;

@Dao
public interface EventDao {

    @Query("SELECT * FROM event_table")
    public LiveData<List<Event>> getAllEvents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Event event);

    @Delete
    public void delete(Event event);

    @Update
    public void update(Event event);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Event> eventList);

    @Query("DELETE FROM event_table")
    public void deleteAll();


}
