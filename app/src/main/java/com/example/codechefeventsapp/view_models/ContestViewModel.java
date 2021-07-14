package com.example.codechefeventsapp.view_models;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codechefeventsapp.data.models.Contest;
import com.example.codechefeventsapp.data.repositories.ContestRepository;

import java.util.List;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class ContestViewModel extends AndroidViewModel {

    private LiveData<List<Contest>> allContests;
    private ContestRepository contestRepository;

    public ContestViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "ContestViewModel: ");
        contestRepository = new ContestRepository(application);
        allContests = contestRepository.getAllContest();
    }

    public void insert(Contest contest) {
        contestRepository.insert(contest);
    }

    public void update(Contest contest) {
        contestRepository.update(contest);
    }

    public void delete(Contest contest) {
        contestRepository.delete(contest);
    }

    public LiveData<List<Contest>> getAllContests() {
        return allContests;
    }

    public void getContestsFromAPIAndStore() {
        contestRepository.makeAPICallAndStore();
    }
}
