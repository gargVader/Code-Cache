package com.example.codechefeventsapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.codechefeventsapp.R;
import static com.example.codechefeventsapp.activities.MainActivity.TAG;

public class EventDetailsActivity extends AppCompatActivity {
    private Button registerB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

//        Log.d(TAG, "onCreate: "+this.getClass().getSimpleName());

        registerB = findViewById(R.id.event_regist_button);
        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}