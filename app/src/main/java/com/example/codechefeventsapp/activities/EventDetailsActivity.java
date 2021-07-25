package com.example.codechefeventsapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.widget.ImageView;

import com.example.codechefeventsapp.R;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.explode));
    }
}