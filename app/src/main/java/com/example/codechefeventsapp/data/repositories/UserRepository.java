package com.example.codechefeventsapp.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.codechefeventsapp.data.dao.UserDao;
import com.example.codechefeventsapp.data.database.AppDatabase;
import com.example.codechefeventsapp.data.models.User;
import com.example.codechefeventsapp.utils.Utils;

import java.util.List;

public class UserRepository {
    LiveData<List<User>> user;
    LiveData<List<User>> allUsers;
    private final UserDao userDao;


    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        userDao = database.getUserDao();
        String email = Utils.Constants.userEmail;
        user = userDao.getUser(email);
        allUsers = userDao.getAllUsers();
    }

    public void insert(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void delete(User user) {
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public void deleteAll() {
        new DeleteAllUserAsyncTask(userDao).execute();
    }

    public LiveData<List<User>> getUser() {
        return user;
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private final UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private final UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private final UserDao userDao;

        private DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

    private static class DeleteAllUserAsyncTask extends AsyncTask<Void, Void, Void> {
        private final UserDao userDao;

        private DeleteAllUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAll();
            return null;
        }
    }
}
