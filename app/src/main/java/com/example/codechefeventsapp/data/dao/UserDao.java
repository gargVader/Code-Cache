package com.example.codechefeventsapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.codechefeventsapp.data.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);
    @Update
    void update(User user);
    @Delete
    void delete(User user);

    @Query("DELETE FROM user_table")
    public void deleteAll();

    @Query("SELECT * FROM user_table where email LIKE :email")
    LiveData<List<User>> getUser(String email);

    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();
}
