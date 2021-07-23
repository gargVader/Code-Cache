package com.example.admin.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.admin.R;
import com.example.admin.data.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class EditEventFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
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
        View view= inflater.inflate(R.layout.fragment_edit_event, container, false);
        eventName=view.findViewById(R.id.eventName);
        eventLocation=view.findViewById(R.id.eventLocation);
        eventImage=view.findViewById(R.id.eventImage);
        eventDate=view.findViewById(R.id.eventDate);
        eventTime=view.findViewById(R.id.eventTime);
        calenderButton=view.findViewById(R.id.calenderImage);
        clockButton=view.findViewById(R.id.clockImage);
        notification=view.findViewById(R.id.notification_bar);
        deleteButton=view.findViewById(R.id.delete_button);
        addButton=view.findViewById(R.id.edit_button);
        cancelButton=view.findViewById(R.id.cancel_button);
        imageUploadButton=view.findViewById(R.id.imageUploadButton);

        Bundle bundle =this.getArguments();
        if(bundle==null){
            Toast.makeText(getContext(),"1",Toast.LENGTH_LONG).show();
        }
        if (bundle!=null) {
                String data;
                int EImage;
                data=bundle.getString("Title");
                eventName.setText(data);
                data=getArguments().getString("Location");
                eventLocation.setText(data);
                data=getArguments().getString("TimeStamp");
                EImage=getArguments().getInt("Image");
                eventImage.setImageResource(EImage);
                eventDate.setText(Utils.getDate(data)+ "/" + Utils.getMonth(data) + "/" + Utils.getYear(data));
                eventTime.setText(Utils.getHour(data)+ ":" + Utils.getMinute(data) + ":00"+Utils.getAmPm(data));
        }
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

        imageUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_TO_LOAD)  ;
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog=new AlertDialog.Builder(getActivity())
                        .setTitle("Warning")
                        .setMessage("Do you want to delete this event?")
                        .setPositiveButton("proceed", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*
                                Implement Deletion Into Database
                                */
                                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_editEventFragment_to_eventsFragment);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Implement Addition Into Database
                */

                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_editEventFragment_to_eventsFragment);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_editEventFragment_to_eventsFragment);
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