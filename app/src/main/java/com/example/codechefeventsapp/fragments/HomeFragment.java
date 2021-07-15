package com.example.codechefeventsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.adapters.PastEventAdapter;
import com.example.codechefeventsapp.adapters.UpcomingEventAdapter;
import com.example.codechefeventsapp.data.Utils;
import com.example.codechefeventsapp.data.models.Event;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    List<Event> eventList;
    ViewPager viewPager;
    RecyclerView pastRecyclerView;
    Toolbar toolbar;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventList = new ArrayList<>();
        eventList.add(new Event("Orientation Session", "", "1665018184", R.drawable.laptop));
        eventList.add(new Event("Coding", "", "1665018184", R.drawable.laptop));
        eventList.add(new Event("Coding", "", "1665018184", R.drawable.laptop));
        eventList.add(new Event("Coding", "", "1665018184", R.drawable.laptop));
        eventList.add(new Event("STL Series #3", "", "1625811304", R.drawable.laptop));
        eventList.add(new Event("STL Series #2", "", "1625811304", R.drawable.laptop));
        eventList.add(new Event("STL Series #1", "", "1625811304", R.drawable.laptop));
        eventList.add(new Event("Introduction to CP", "", "1625811304", R.drawable.laptop));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = getView().findViewById(R.id.toolbar);
        initUpcomingEvents();
        initPastEvents();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notifications:
                Toast.makeText(getContext(), "Notifications", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initUpcomingEvents() {
        List<Event> upcomingEventList = new ArrayList<>();
        for (Event event : eventList) {
            if (!Utils.isPastEvent(event)) upcomingEventList.add(event);
        }
        viewPager = getView().findViewById(R.id.viewPager);
        viewPager.setAdapter(new UpcomingEventAdapter(upcomingEventList, getContext()));
//        viewPager.setPadding(130, 10, 130, 0);
    }

    private void initPastEvents() {
        List<Event> pastEventList = new ArrayList<>();
        for (Event event : eventList) {
            if (Utils.isPastEvent(event)) pastEventList.add(event);
        }
        pastRecyclerView = getView().findViewById(R.id.pastRecyclerView);
        pastRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pastRecyclerView.setAdapter(new PastEventAdapter(pastEventList));
    }

}