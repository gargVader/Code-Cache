package com.example.codechefeventsapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.codechefeventsapp.data.models.Contest;

import java.util.List;

@Dao
public interface ContestDao {
    @Query("SELECT * FROM contest_table")
    public LiveData<List<Contest>> getAllContest();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Contest contest);

    @Delete
    public void delete(Contest contest);

    @Update
    public void update(Contest contest);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<Contest> contestList);

    @Query("DELETE FROM contest_table")
    public void deleteAll();

}
