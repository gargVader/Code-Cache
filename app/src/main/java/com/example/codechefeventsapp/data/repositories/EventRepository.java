package com.example.codechefeventsapp.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.codechefeventsapp.FirebaseLayer;
import com.example.codechefeventsapp.data.dao.EventDao;
import com.example.codechefeventsapp.data.database.AppDatabase;
import com.example.codechefeventsapp.data.models.Event;

import java.util.List;

public class EventRepository implements FirebaseLayer.FirebaseListener {

    Application application;
    FirebaseLayer firebaseLayer;
    private final EventDao eventDao;
    private final LiveData<List<Event>> allEvent;

    public EventRepository(Application application) {
        this.application = application;
        eventDao = AppDatabase.getInstance(application).getEventDao();
        allEvent = eventDao.getAllEvents();
        firebaseLayer = FirebaseLayer.getInstance();
        firebaseLayer.setFirebaseListener(this);
    }

    public void insert(Event event) {
        new InsertAsyncTask().execute(event);
    }

    public void update(Event event) {
        new UpdateAsyncTask().execute(event);
    }

    public void delete(Event event) {
        new DeleteAsyncTask().execute(event);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask().execute();
    }

    public void insertAll(List<Event> eventList) {
        new InsertAllAsyncTask().execute(eventList);
    }

    public LiveData<List<Event>> getAllEvent() {
        return allEvent;
    }

    public void makeFirebaseCallAndStore() {
//        Log.d(TAG, "makeFirebaseCallAndStore: ");
        firebaseLayer.getAllEvents();

    }

    /**
     * FirebaseListener Callback methods
     */
    @Override
    public void onGetSuccess(List<Event> eventList) {
        deleteAll();
        insertAll(eventList);
    }

    @Override
    public void onGetFailure() {

    }

    @Override
    public void onDocumentAdded(Event event) {
        insert(event);
    }

    @Override
    public void onDocumentModified(Event event) {
        update(event);
    }

    @Override
    public void onDocumentRemoved(Event event) {
        delete(event);
    }

    /*********************************************************************************************/

    private class InsertAsyncTask extends AsyncTask<Event, Void, Void> {
        @Override
        protected Void doInBackground(Event... events) {
            eventDao.insert(events[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Event, Void, Void> {
        @Override
        protected Void doInBackground(Event... events) {
            eventDao.update(events[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Event, Void, Void> {
        @Override
        protected Void doInBackground(Event... events) {
            eventDao.delete(events[0]);
            return null;
        }
    }

    private class InsertAllAsyncTask extends AsyncTask<List<Event>, Void, Void> {
        @Override
        protected Void doInBackground(List<Event>... lists) {
            eventDao.insertAll(lists[0]);
            return null;
        }
    }

    private class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            eventDao.deleteAll();
            return null;
        }
    }
    /**FirebaseListener Callback methods*/
    /*********************************************************************************************/


}
