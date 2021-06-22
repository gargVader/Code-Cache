package com.example.codechefeventsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Event> eventList;
    ViewPager viewPager;

    public static final String TAG = "Codechef";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);

        eventList = new ArrayList<>();
        eventList.add(new Event("Tomorrowland", "", "APR", "25", R.drawable.dj_image));
        eventList.add(new Event("Tomorrowland", "", "APR", "25", R.drawable.dj_image));
        eventList.add(new Event("Tomorrowland", "", "APR", "25", R.drawable.dj_image));
        eventList.add(new Event("Tomorrowland", "", "APR", "25", R.drawable.dj_image));
        eventList.add(new Event("Tomorrowland", "", "APR", "25", R.drawable.dj_image));

        viewPager.setAdapter(new EventAdapter(eventList, this));
//        viewPager.setPadding(130, 10, 130, 0);
    }

}

