package com.example.codechefeventsapp.activities;

import static com.example.codechefeventsapp.fragments.ProfileFragment.RC_SIGN_IN;

import android.content.Context;
import android.content.Intent;
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

import com.example.codechefeventsapp.FirebaseLayer;
import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.data.models.Event;
import com.example.codechefeventsapp.utils.FirebaseSignIn;
import com.example.codechefeventsapp.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.seismic.ShakeDetector;

public class EventDetailsActivity extends AppCompatActivity implements ShakeDetector.Listener, FirebaseSignIn.OnFirebaseSignInListener {

    Event event;
    ShakeDetector sd = new ShakeDetector(this);
    FirebaseSignIn firebaseSignIn;
    ImageView eventImage;
    TextView eventTitle, eventDate, eventTime, eventDescription, eventLocation;
    ProgressBar progressBar;
    private Button registerB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        firebaseSignIn = FirebaseSignIn.getInstance(this);

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
                registerForEvent();
            }
        });
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

    @Override
    protected void onPause() {
        super.onPause();
        sd.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSensor();
    }

    void initSensor() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sd.setSensitivity(ShakeDetector.SENSITIVITY_MEDIUM);
        sd.start(sensorManager);
    }

    void vibratePhone() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
            registerForEvent();
        }
    }

    void registerForEvent() {
        String userId = getCurrentUserId();
        if (userId != null) {
            FirebaseLayer.getInstance().registerUserForEvent(userId, event.getId());
        } else {
            firebaseSignIn.setOnFirebaseSignInListener(this);
            firebaseSignIn.signIn();
        }
    }

    private String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            firebaseSignIn.notifyOnActivityResult(data);
        }
    }

    /*************** Callbacks for FirebaseSignIn *********************/
    @Override
    public void signInSuccess(FirebaseUser currentUser) {
        registerForEvent();
    }

    @Override
    public void signInFailure() {

    }
}