package com.example.codechefeventsapp.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.codechefeventsapp.data.models.Event;
import com.example.codechefeventsapp.data.repositories.EventRepository;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private final LiveData<List<Event>> allEvent;
    private final EventRepository eventRepository;

    public EventViewModel(@NonNull Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        allEvent = eventRepository.getAllEvent();
    }

    public void insert(Event event) {
        eventRepository.insert(event);
    }

    public void update(Event event) {
        eventRepository.update(event);
    }

    public void delete(Event event) {
        eventRepository.delete(event);
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvent;
    }

    public void getEventsFromFirebaseAndStore() {
        eventRepository.makeFirebaseCallAndStore();
    }
}
