package com.example.admin.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.example.admin.adapters.EventAdapter;
import com.example.admin.data.Utils;
import com.example.admin.data.models.Event;
import com.example.admin.view_models.EventViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class EventsFragment extends Fragment implements EventAdapter.OnEventListener{

    EventViewModel eventViewModel;
    EventAdapter eventAdapter;
    List<Event> eventList;

    public EventsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEvent();

        getView().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_eventsFragment_to_addEventFragment);

            }
        });
    }
    private void initViewModel() {
        eventViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(EventViewModel.class);

        eventViewModel.getEventsFromFirebaseAndStore();
        eventViewModel.getAllContests().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> eventList) {
                Log.d(TAG, "onChanged: ");
                List<Event> upcomingEventList = new ArrayList<>();
                List<Event> pastEventList = new ArrayList<>();
                for (Event event : eventList) {
                    if (Utils.isPastEvent(event)) upcomingEventList.add(event);
                    else pastEventList.add(event);
                }
                eventAdapter.setEventList(eventList);
            }
        });
    }
    private void initEvent() {
        eventList=new ArrayList<>();
        eventList.add(new Event("Coding","XYZ","1628372389",R.drawable.laptop));
        eventList.add(new Event("Coding","XYZ","1628372389",R.drawable.laptop));
        eventList.add(new Event("Coding","XYZ","1628372389",R.drawable.laptop));
        eventList.add(new Event("Coding","XYZ","1628372389",R.drawable.laptop));
        eventList.add(new Event("Coding","XYZ","1628372389",R.drawable.laptop));

        eventAdapter = new EventAdapter(eventList,this);

        RecyclerView recyclerView = getView().findViewById(R.id.eventRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(eventAdapter);
    }
    @Override
    public void onEventClick(int position) {

        String eventTitle= eventList.get(position).getEventTitle();
        String eventLocation= eventList.get(position).getEventLocation();
        String eventTimeStamp=eventList.get(position).getEventTimeStamp();
        int eventImage=eventList.get(position).getEventImage();

        Bundle bundle=new Bundle();
        bundle.putString("Title",eventTitle);
        bundle.putString("Location",eventLocation);
        bundle.putString("TimeStamp",eventTimeStamp);
        bundle.putInt("Image",eventImage);
        EditEventFragment editEventFragment=new EditEventFragment();
        editEventFragment.setArguments(bundle);

        Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_eventsFragment_to_editEventFragment);
    }
    public interface FragmentCommunication {
        void respond(int position,String name,String job);
    }
}