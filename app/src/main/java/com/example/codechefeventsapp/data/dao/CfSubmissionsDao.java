package com.example.codechefeventsapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.codechefeventsapp.data.models.cf.CfContest;
import com.example.codechefeventsapp.data.models.cf.CfSubmission;
import com.example.codechefeventsapp.data.models.cf.CfUserContest;

import java.util.List;

@Dao
public interface CfSubmissionsDao {

    @Query("SELECT * FROM cf_user_submissions_table")
    LiveData<List<CfSubmission>> getAllSubmissions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CfSubmission cfSubmission);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CfSubmission> cfSubmissionList);

    @Delete
    void delete(CfSubmission cfSubmission);

    @Update
    void update(CfSubmission cfSubmission);

    @Query("DELETE FROM cf_user_submissions_table")
    void deleteAll();

}
