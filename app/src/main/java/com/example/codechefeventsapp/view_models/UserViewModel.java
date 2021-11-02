package com.example.codechefeventsapp.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codechefeventsapp.data.models.cf.CfContest;
import com.example.codechefeventsapp.data.models.cf.CfSubmission;
import com.example.codechefeventsapp.data.repositories.UserRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final LiveData<List<CfContest>> cfUserContests;
    private final LiveData<List<CfSubmission>> cfUserSubmissions;
    private final UserRepository repository;

    public UserViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new UserRepository(application);
        cfUserContests = repository.getAllCfUserContest();
        cfUserSubmissions = repository.getAllCfUserSubmissions();
    }

    public LiveData<List<CfContest>> getAllCfUserContest(){
        return cfUserContests;
    }

    public LiveData<List<CfSubmission>> getAllCfUserSubmissions(){
        return cfUserSubmissions;
    }

    public void getUserCfDetailsAndStore(String handle) {
        repository.makeCFAPICallAndStore(handle);
    }

}
