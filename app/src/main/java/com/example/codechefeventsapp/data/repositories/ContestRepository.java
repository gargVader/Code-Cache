package com.example.codechefeventsapp.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.codechefeventsapp.data.api.ContestApi;
import com.example.codechefeventsapp.data.dao.ContestDao;
import com.example.codechefeventsapp.data.database.AppDatabase;
import com.example.codechefeventsapp.data.models.Contest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ContestRepository {

    private final ContestDao contestDao;
    private ContestApi contestApi;
    private final LiveData<List<Contest>> allContest;

    public ContestRepository(Application application) {
        contestDao = AppDatabase.getInstance(application).getContestDao();
        allContest = contestDao.getAllContest();
    }

    public void insert(Contest contest) {
        new InsertAsyncTask().execute(contest);
    }

    public void update(Contest contest) {
        new UpdateAsyncTask().execute(contest);
    }

    public void delete(Contest contest) {
        new DeleteAsyncTask().execute(contest);
    }

    public LiveData<List<Contest>> getAllContest() {
        return allContest;
    }

    public LiveData<List<Contest>> getFilteredContests(ArrayList<String> selectedFilters) {
        return contestDao.getFilteredContests(selectedFilters);
    }

    public void makeAPICallAndStore() {
        String platform = "all";
//        Log.d(TAG, "makeAPICallAndStore: ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kontests.net/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        contestApi = retrofit.create(ContestApi.class);

        Call<List<Contest>> call = contestApi.getContests(platform);
        call.enqueue(new Callback<List<Contest>>() {
            @Override
            public void onResponse(Call<List<Contest>> call, Response<List<Contest>> response) {
                if (response.isSuccessful()) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            contestDao.deleteAll();
                            contestDao.insertAll(response.body());
                        }
                    });
                    thread.start();
                }
            }

            @Override
            public void onFailure(Call<List<Contest>> call, Throwable t) {

            }
        });

    }

    private class InsertAsyncTask extends AsyncTask<Contest, Void, Void> {
        @Override
        protected Void doInBackground(Contest... contests) {
            contestDao.insert(contests[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Contest, Void, Void> {
        @Override
        protected Void doInBackground(Contest... contests) {
            contestDao.update(contests[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Contest, Void, Void> {
        @Override
        protected Void doInBackground(Contest... contests) {
            contestDao.delete(contests[0]);
            return null;
        }
    }
}
