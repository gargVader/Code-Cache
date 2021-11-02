package com.example.codechefeventsapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.codechefeventsapp.data.models.cf.CfContest;
import java.util.List;

@Dao
public interface CfUserContestsDao {

    @Query("SELECT * FROM cf_user_contest_table")
    LiveData<List<CfContest>> getAllUserCfContest();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CfContest cfContest);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CfContest> cfContestList);

    @Delete
    void delete(CfContest cfContest);

    @Update
    void update(CfContest cfContest);

    @Query("DELETE FROM cf_user_contest_table")
    void deleteAll();
}
