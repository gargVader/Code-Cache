package com.example.codechefeventsapp.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.codechefeventsapp.data.api.CfApi;
import com.example.codechefeventsapp.data.dao.CfSubmissionsDao;
import com.example.codechefeventsapp.data.dao.CfUserContestsDao;
import com.example.codechefeventsapp.data.dao.UserDao;
import com.example.codechefeventsapp.data.database.AppDatabase;
import com.example.codechefeventsapp.data.models.User;
import com.example.codechefeventsapp.data.models.cf.CfContest;
import com.example.codechefeventsapp.data.models.cf.CfSubmission;
import com.example.codechefeventsapp.data.models.cf.CfUserContest;
import com.example.codechefeventsapp.data.models.cf.CfUserSubmissions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private final CfUserContestsDao cfUserContestsDao;
    private final CfSubmissionsDao cfSubmissionDao;
    private CfApi cfApi;
    private Application application;


    public UserRepository(Application application) {
        this.application = application;
        AppDatabase database = AppDatabase.getInstance(application);
        cfUserContestsDao = database.getCfUserContestsDao();
        cfSubmissionDao = database.getCfSubmissionsDao();
    }

    public LiveData<List<CfContest>> getAllCfUserContest() {
        return cfUserContestsDao.getAllUserCfContest();
    }

    public LiveData<List<CfSubmission>> getAllCfUserSubmissions() {
        return cfSubmissionDao.getAllSubmissions();
    }

    public void makeCFAPICallAndStore(String handle) {
        if(handle==null || handle.trim().isEmpty()) return;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CfApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cfApi = retrofit.create(CfApi.class);

        cfApi.getUserContests(handle).enqueue(new Callback<CfUserContest>() {
            @Override
            public void onResponse(Call<CfUserContest> call, Response<CfUserContest> response) {
                if (response.isSuccessful()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            cfUserContestsDao.deleteAll();
                            cfUserContestsDao.insertAll(response.body().getResult());
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<CfUserContest> call, Throwable t) {

            }
        });

        cfApi.getUserSubmissions(handle, 1, 100000).
                enqueue(new Callback<CfUserSubmissions>() {
                    @Override
                    public void onResponse
                            (Call<CfUserSubmissions> call, Response<CfUserSubmissions> response) {
                        if (response.isSuccessful()) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    cfSubmissionDao.deleteAll();
//                            cfSubmissionDao.insertAll(response.body().getResult());
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<CfUserSubmissions> call, Throwable t) {

                    }
                });
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

}
