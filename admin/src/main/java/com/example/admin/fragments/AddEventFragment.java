package com.example.admin.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.admin.R;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.Calendar;

public class AddEventFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private static final int RESULT_TO_LOAD=1;
    public EditText eventName;
    public EditText eventLocation;
    public EditText eventDate;
    public EditText eventTime;
    public ImageView eventImage;
    public CardView cancelButton;
    public CardView addButton;
    public CardView imageUploadButton;
    public ImageView calenderButton;
    public ImageView clockButton;
    public long timeStamp;

    public TextInputLayout leventName;
    public TextInputLayout leventLocation;
    public TextInputLayout leventDate;
    public TextInputLayout leventTime;
    public TextInputLayout leventImage;
    public AddEventFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_event, container, false);
        eventName=view.findViewById(R.id.eventName);
        eventLocation=view.findViewById(R.id.eventLocation);
        eventImage=view.findViewById(R.id.eventImage);
        eventDate=view.findViewById(R.id.eventDate);
        eventTime=view.findViewById(R.id.eventTime);
        calenderButton=view.findViewById(R.id.calenderImage);
        clockButton=view.findViewById(R.id.clockImage);
        addButton=view.findViewById(R.id.add_button);
        cancelButton=view.findViewById(R.id.cancel_button);
        imageUploadButton=view.findViewById(R.id.imageUploadButton);
        try {
            timeStamp= new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse("01/01/1970 01:00:00").getTime() / 1000;
        } catch (ParseException e) {
            timeStamp = 0;
        }
        imageUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_TO_LOAD)  ;
            }
        });
        calenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        clockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Implement Addition Into Database (FireBase)
                */

                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_addEventFragment_to_eventsFragment);

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_addEventFragment_to_eventsFragment);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                getActivity(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date= dayOfMonth+"/"+month+"/"+year;
        eventDate.setText(date);
    }
    private void showTimePickerDialog(){
        TimePickerDialog timePickerDialog=new TimePickerDialog(
                getActivity(),
                this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                false
        );
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = hourOfDay+":"+minute+":"+"00";
        eventTime.setText(time);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_TO_LOAD && data!=null){
            Uri selectedImage=data.getData();
            eventImage.setImageURI(selectedImage);
        }
    }
}