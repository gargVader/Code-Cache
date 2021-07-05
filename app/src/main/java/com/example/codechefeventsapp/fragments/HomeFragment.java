package com.example.codechefeventsapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.adapters.EventAdapter;
import com.example.codechefeventsapp.model.Event;
import com.example.codechefeventsapp.pastEventAdapter;
import com.example.codechefeventsapp.pastEventData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    List<Event> eventList;
    ViewPager viewPager;

    public static final String TAG = "Codechef";

    RecyclerView recyclerView;
    ArrayList<pastEventData> dataHolder;
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.pastRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataHolder=new ArrayList<>();

        dataHolder.add(new pastEventData("JULY CHALLENGE","7JUL 19:00","17JUL 19:00","10 Days","Coding"));
        dataHolder.add(new pastEventData("JULY CHALLENGE","7JUL 19:00","17JUL 19:00","10 Days","Coding"));
        dataHolder.add(new pastEventData("JULY CHALLENGE","7JUL 19:00","17JUL 19:00","10 Days","Coding"));
        dataHolder.add(new pastEventData("JULY CHALLENGE","7JUL 19:00","17JUL 19:00","10 Days","Coding"));
        dataHolder.add(new pastEventData("JULY CHALLENGE","7JUL 19:00","17JUL 19:00","10 Days","Coding"));
        dataHolder.add(new pastEventData("JULY CHALLENGE","7JUL 19:00","17JUL 19:00","10 Days","Coding"));
        dataHolder.add(new pastEventData("JULY CHALLENGE","7JUL 19:00","17JUL 19:00","10 Days","Coding"));

        recyclerView.setAdapter(new pastEventAdapter(dataHolder));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = getView().findViewById(R.id.viewPager);
        viewPager.setAdapter(new EventAdapter(eventList, getContext()));
//        viewPager.setPadding(130, 10, 130, 0);
    }
}