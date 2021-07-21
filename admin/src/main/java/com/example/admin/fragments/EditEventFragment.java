package com.example.admin.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
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

import com.example.admin.R;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class EditEventFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
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
    public CardView notification;
    public CardView deleteButton;

    public EditEventFragment(){
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
        notification=view.findViewById(R.id.notification_bar);
        deleteButton=view.findViewById(R.id.delete_button);
        addButton=view.findViewById(R.id.add_button);
        cancelButton=view.findViewById(R.id.cancel_button);
        imageUploadButton=view.findViewById(R.id.imageUploadButton);



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
        String time = hourOfDay+"/"+minute+"/"+"00";
        eventTime.setText(time);
    }
}
