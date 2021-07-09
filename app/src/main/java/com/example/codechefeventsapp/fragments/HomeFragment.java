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
import com.example.codechefeventsapp.data.models.Event;
import com.example.codechefeventsapp.adapters.pastEventAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


public class HomeFragment extends Fragment {
    ArrayList<Event> eventList;
    ArrayList<Event> futureEvent;
    ArrayList<Event> pastEvent;
    ViewPager viewPager;

    public static final String TAG = "Codechef";

    RecyclerView recyclerView;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.pastRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventList=new ArrayList<>();
        futureEvent=new ArrayList<>();
        pastEvent=new ArrayList<>();
        eventList.add(new Event("Coding", "", "JUL", "8", R.drawable.laptop));
        eventList.add(new Event("Coding", "", "JUL", "25", R.drawable.laptop));
        eventList.add(new Event("Coding", "", "APR", "25", R.drawable.laptop));
        eventList.add(new Event("Coding", "", "JUL", "25", R.drawable.laptop));
        eventList.add(new Event("Coding", "", "APR", "25", R.drawable.laptop));
        eventList.add(new Event("Coding","Codechef","JUL","1",R.drawable.laptop));
        eventList.add(new Event("Coding","Codechef","APR","27",R.drawable.laptop));
        eventList.add(new Event("Coding","Codechef","APR","27",R.drawable.laptop));
        eventList.add(new Event("Coding","Codechef","JUL","27",R.drawable.laptop));
        eventList.add(new Event("Coding","Codechef","APR","27",R.drawable.laptop));
        eventList.add(new Event("Coding","Codechef","APR","27",R.drawable.laptop));
        eventList.add(new Event("Coding","Codechef","JUL","27",R.drawable.laptop));


        Calendar c = Calendar.getInstance();
        TimeZone time_zone = TimeZone.getTimeZone("IST");
        c.setTimeZone(time_zone);
        int day = c.get(Calendar.DATE);
        int month = c.get(Calendar.MONTH);
        month++;
        day++;
        for(int i=0;i<eventList.size();i++){
            Event item=eventList.get(i);
            String s= item.getEventDate();
            int sDay=Integer.parseInt(s);
            int sMonth = 0;
            if(item.getEventMonth()=="JAN"){sMonth=1; }
            else if(item.getEventMonth()=="FEB"){ sMonth=2; }
            else if(item.getEventMonth()=="MAR"){ sMonth=3; }
            else if(item.getEventMonth()=="APR"){ sMonth=4; }
            else if(item.getEventMonth()=="MAY"){ sMonth=5; }
            else if(item.getEventMonth()=="JUN"){ sMonth=6; }
            else if(item.getEventMonth()=="JUL"){ sMonth=7; }
            else if(item.getEventMonth()=="AUG"){ sMonth=8; }
            else if(item.getEventMonth()=="SEPT"){sMonth=9; }
            else if(item.getEventMonth()=="OCT"){sMonth=10; }
            else if(item.getEventMonth()=="NOV"){sMonth=11; }
            else if(item.getEventMonth()=="DEC"){sMonth=12; }
            if(sMonth>month){
                futureEvent.add(item);
            }
            else if(sMonth==month && sDay>=day){
                futureEvent.add(item);
            }
            else{
                pastEvent.add(item);
            }
        }

        recyclerView.setAdapter(new pastEventAdapter(pastEvent));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = getView().findViewById(R.id.viewPager);
        viewPager.setAdapter(new EventAdapter(futureEvent, getContext()));
//        viewPager.setPadding(130, 10, 130, 0);
    }
}