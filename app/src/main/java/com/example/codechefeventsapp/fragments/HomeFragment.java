package com.example.codechefeventsapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.adapters.EventAdapter;
import com.example.codechefeventsapp.data.models.Event;
import static com.example.codechefeventsapp.activities.MainActivity.TAG;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    List<Event> eventList;
    ViewPager viewPager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventList = new ArrayList<>();
        eventList.add(new Event("Coding", "", "APR", "25", R.drawable.laptop));
        eventList.add(new Event("Coding", "", "APR", "25", R.drawable.laptop));
        eventList.add(new Event("Coding", "", "APR", "25", R.drawable.laptop));
        eventList.add(new Event("Coding", "", "APR", "25", R.drawable.laptop));
        eventList.add(new Event("Coding", "", "APR", "25", R.drawable.laptop));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = getView().findViewById(R.id.viewPager);
        viewPager.setAdapter(new EventAdapter(eventList, getContext()));
//        viewPager.setPadding(130, 10, 130, 0);
    }
}