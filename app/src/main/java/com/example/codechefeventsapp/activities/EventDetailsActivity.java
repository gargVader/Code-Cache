package com.example.codechefeventsapp.activities;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.data.Utils;
import com.example.codechefeventsapp.data.models.Event;
import com.squareup.seismic.ShakeDetector;

public class EventDetailsActivity extends AppCompatActivity implements ShakeDetector.Listener {

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

        initSensor();
    }

    void registerUserForEvent() {

    }

    void setViews() {
        eventTitle.setText(event.getEventTitle());
        eventLocation.setText(event.getEventLocation());
        eventDate.setText(Utils.getDateFull(event.getEventStartTimeStamp()));
        eventTime.setText(Utils.getTime(event.getEventStartTimeStamp()));
        if (event.getEventImageUrl() != null) {
            Utils.loadImage(this, event.getEventImageUrl(), eventImage, progressBar);
        } else {
            eventImage.setImageResource(R.drawable.laptop);
        }
        if (isUseable(event.getEventLongDescription())) {
            eventDescription.setVisibility(View.VISIBLE);
            eventDescription.setText(event.getEventLongDescription());
        } else if (isUseable(event.getEventShortDescription())) {
            eventDescription.setVisibility(View.VISIBLE);
            eventDescription.setText(event.getEventShortDescription());
        } else {
            eventDescription.setVisibility(View.GONE);
        }
    }

    boolean isUseable(String string) {
        return string != null && !(string.trim().isEmpty());
    }

    @Override
    public void hearShake() {
        vibratePhone();
    }

    void initSensor() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.setSensitivity(10);
        sd.start(sensorManager);
    }

    void vibratePhone() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));

    }

}