package com.example.codechefeventsapp.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.codechefeventsapp.data.Utils;
import com.example.codechefeventsapp.data.dao.UserDao;
import com.example.codechefeventsapp.data.database.UserDatabase;
import com.example.codechefeventsapp.data.models.Contest;
import com.example.codechefeventsapp.data.models.User;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
     LiveData<List<User>> user;

    public UserRepository(Application application){
        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.getUserDao();
        String email = Utils.Constants.userEmail;
        user = userDao.getUser(email);
    }

    public void insert(User user){
        new InsertUserAsyncTask(userDao).execute(user);
    }
    public void update(User user){
        new UpdateUserAsyncTask(userDao).execute(user);
    }
    public void delete(User user){
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<User>> getUser() {
        return user;
    }
    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        private InsertUserAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }
    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;
        private UpdateUserAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }
    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;
        private DeleteUserAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }


}
