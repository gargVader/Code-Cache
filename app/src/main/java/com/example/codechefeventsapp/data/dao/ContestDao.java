package com.example.codechefeventsapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.codechefeventsapp.data.models.Contest;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContestDao {
    @Query("SELECT * FROM contest_table")
    LiveData<List<Contest>> getAllContest();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contest contest);

    @Delete
    void delete(Contest contest);

    @Update
    void update(Contest contest);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Contest> contestList);

    @Query("DELETE FROM contest_table")
    void deleteAll();

    @Query("SELECT * FROM contest_table WHERE contestSite IN (:selectedFilters)")
    LiveData<List<Contest>> getFilteredContests(ArrayList<String> selectedFilters);


}
