package com.example.codechefeventsapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codechefeventsapp.Api.ContestApi;
import com.example.codechefeventsapp.Dao.ContestDao;
import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.Repository.ContestRepository;
import com.example.codechefeventsapp.ViewModel.ContestViewModel;
import com.example.codechefeventsapp.adapters.ContestAdapter;
import com.example.codechefeventsapp.model.Contest;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ContestFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Contest> dataHolder;
    ContestAdapter contestAdapter;
    ContestViewModel contestViewModel;

    public static final String TAG = "Codechef";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contest,container,false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerview_contest);

        initViewModel();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contestAdapter = new ContestAdapter(new ArrayList<>());
        recyclerView.setAdapter(contestAdapter);
    }

    private void initViewModel() {
        contestViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(contestViewModel.getApplication()))
                .get(ContestViewModel.class);

        contestViewModel.getContestsFromAPIAndStore();
        contestViewModel.getAllContests().observe(getViewLifecycleOwner(), new Observer<List<Contest>>() {
            @Override
            public void onChanged(List<Contest> contestList) {
                Log.d(TAG, "onChanged: ");
                contestAdapter.setContestList(contestList);
            }
        });
    }
}

