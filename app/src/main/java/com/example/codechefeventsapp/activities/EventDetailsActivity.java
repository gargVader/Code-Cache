package com.example.codechefeventsapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.data.Utils;
import com.example.codechefeventsapp.data.models.Event;

public class EventDetailsActivity extends AppCompatActivity {

    private Button registerB;
    Event event;

    ImageView eventImage;
    TextView eventTitle, eventDate, eventTime, eventDescription, eventLocation;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        eventTitle = findViewById(R.id.eventTitle);
        eventLocation = findViewById(R.id.eventLocation);
        eventImage = findViewById(R.id.eventImage);
        eventDate = findViewById(R.id.eventDate);
        eventTime = findViewById(R.id.eventTime);
        progressBar = findViewById(R.id.progress_load_photo);
        eventDescription = findViewById(R.id.eventDescription);
        event = (Event) getIntent().getSerializableExtra("EVENT");
        setViews();

        registerB = findViewById(R.id.event_regist_button);
        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserForEvent();
            }
        });
    }

    void registerUserForEvent() {

    }

    void setViews() {
        eventTitle.setText(event.getEventTitle());
        eventLocation.setText(event.getEventLocation());
        eventDate.setText(Utils.getDateFull(event.getEventTimeStamp()));
        eventTime.setText(Utils.getTime(event.getEventTimeStamp()));
        if (event.getEventImageUrl() != null) {
            Utils.loadImage(this, event.getEventImageUrl(), eventImage, progressBar);
        } else {
            eventImage.setImageResource(R.drawable.laptop);
        }
    }

}