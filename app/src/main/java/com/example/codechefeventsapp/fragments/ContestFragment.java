package com.example.codechefeventsapp.fragments;

import static com.example.codechefeventsapp.data.Constants.*;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import java.util.Map;

public class ContestFragment extends Fragment {

    RecyclerView recyclerView;
    ContestAdapter contestAdapter;
    ContestViewModel contestViewModel;

    List<Contest> contestList;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    boolean[] isSiteSelected;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        View view = inflater.inflate(R.layout.fragment_contest, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerview_contest);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contestAdapter = new ContestAdapter(new ArrayList<>(), getActivity());
        recyclerView.setAdapter(contestAdapter);
        initViewModel();
        contestAdapter.setOnItemClickListener(new ContestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contest contest) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(contest.getContestUrl())));
            }
        });
    }

    private void initViewModel() {
        contestViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(ContestViewModel.class);
        contestViewModel.getContestsFromAPIAndStore();
//        contestViewModel.getAllContests().observe(getViewLifecycleOwner(), new Observer<List<Contest>>() {
//            @Override
//            public void onChanged(List<Contest> list) {
//                contestList = list;
//                contestAdapter.setContestList(contestList);
//            }
//        });
        applyFilters();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_contest, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_action:
                showFilters();
                return true;
            default:
                return true;
        }
    }

    public void showFilters() {
        isSiteSelected = generateArrayFromSharedPref();
        new AlertDialog.Builder(getActivity())
                .setTitle("Select Platforms")
                .setCancelable(true)
                .setMultiChoiceItems(sites, isSiteSelected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        isSiteSelected[which] = isChecked;
                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateSharedPref();
                        applyFilters();
                    }
                })
                .show();
    }

    void applyFilters() {
        // Pass in an arrayList of selected filters
        contestViewModel.getFilteredContests(generateSelectedFiltersArrayList()).observe(
                getViewLifecycleOwner(), new Observer<List<Contest>>() {
                    @Override
                    public void onChanged(List<Contest> list) {
//                contestList = list;
                        contestAdapter.setContestList(list);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    void updateSharedPref() {
        for (int i = 0; i < sites.length; i++) {
            editor.putBoolean(sites[i], isSiteSelected[i]);
        }
        editor.apply();
    }

    /**
     * @return ArrayList<String> of selected filters
     */
    ArrayList<String> generateSelectedFiltersArrayList() {
        isSiteSelected = generateArrayFromSharedPref();
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i=0; i<sites.length; i++){
            if(isSiteSelected[i]) arrayList.add(sites[i]);
        }
        return arrayList;
    }

    boolean[] generateArrayFromSharedPref() {
        boolean[] booleanArray = new boolean[sites.length];
        for (int i = 0; i < sites.length; i++) {
            String site = sites[i];
            boolean isChecked = sharedPref.getBoolean(site, getDefault.get(site));
            booleanArray[i] = isChecked;
        }
        return booleanArray;
    }


}

