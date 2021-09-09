package com.example.codechefeventsapp.fragments;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.adapters.ContestAdapter;
import com.example.codechefeventsapp.data.models.Contest;
import com.example.codechefeventsapp.view_models.ContestViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.example.codechefeventsapp.activities.MainActivity.TAG;

public class ContestFragment extends Fragment {

    RecyclerView recyclerView;
    ContestAdapter contestAdapter;
    ContestViewModel contestViewModel;
    List<Contest> contestList;
    private String[] filters = {"CodeForces", "CodeForces::Gym", "TopCoder", "AtCoder", "CodeChef", "HackerRank", "HackerEarth", "LeetCode"};
    private ArrayList<Integer> selectedFilters;
    private boolean[] checkedFilters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contest, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerview_contest);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contestAdapter = new ContestAdapter(new ArrayList<>(), getActivity());
        recyclerView.setAdapter(contestAdapter);
        initViewModel();
        contestAdapter.setOnItemClickListener(new ContestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contest contest) {
                Uri newsUri = Uri.parse(contest.getContestUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(intent);
            }
        });
    }

    private void initViewModel() {
        contestViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(ContestViewModel.class);

        contestViewModel.getContestsFromAPIAndStore();
        contestViewModel.getAllContests().observe(getViewLifecycleOwner(), new Observer<List<Contest>>() {
            @Override
            public void onChanged(List<Contest> list) {
                contestList = list;
                Log.d(TAG, "onChanged: ");
                contestAdapter.setContestList(contestList);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        inflater.inflate(R.menu.menu_contest, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        switch (item.getItemId()){
            case R.id.filter_action:
                showFilters();
                return true;
            default:
                return true;
        }
    }

    public void showFilters(){
        selectedFilters = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select filters");
        builder.setCancelable(true);
        builder.setMultiChoiceItems(filters, checkedFilters ,new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                selectedFilters.add(which);
            }
        });
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                applyFilters();
            }
        });
        builder.show();
    }

    public void applyFilters(){
        Log.d(TAG, "applyFilters: ");
        List<Contest> contests = new ArrayList<>();
        for(Contest contest : contestList){
            for(int pos : selectedFilters){
                if(contest.getContestSite().equals(filters[pos])){
                    contests.add(contest);
                }
            }
        }
        contestAdapter.setContestList(contests);
    }
}

