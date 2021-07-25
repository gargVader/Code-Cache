package com.example.codechefeventsapp.data.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.data.dao.EventDao;
import com.example.codechefeventsapp.data.database.EventDatabase;
import com.example.codechefeventsapp.data.models.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.example.codechefeventsapp.activities.MainActivity.TAG;

public class EventRepository {

    private EventDao eventDao;
    private LiveData<List<Event>> allEvent;
    Application application;

    public EventRepository(Application application) {
        this.application = application;
        eventDao = EventDatabase.getInstance(application).getEventDao();
        allEvent = eventDao.getAllEvents();
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

    public LiveData<List<Event>> getAllEvent() {
        return allEvent;
    }

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

    public static final String EVENT_COLLECTION_ID = "EventCollection";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventCollection = db.collection(EVENT_COLLECTION_ID);

    public void makeFirebaseCallAndStore() {
        Log.d(TAG, "makeFirebaseCallAndStore: ");
        List<Event> eventList = new ArrayList<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                eventDao.deleteAll();
                eventDao.insertAll(eventList);
                Log.d(TAG, "run: " + eventList.toString());
            }
        });

        Task<QuerySnapshot> task = eventCollection.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Event event = documentSnapshot.toObject(Event.class);
                            event.setId(documentSnapshot.getId());
                            event.setEventImage(R.drawable.laptop);
                            eventList.add(event);
                        }
                        thread.start();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });
        // may pass (Activity) application.getApplicationContext()
        eventCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.d(TAG, "onEvent: Snapshot listener has detected changes");
                if (error != null) {
                    Log.d(TAG, "onEvent: " + error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()) {
                    DocumentSnapshot document = dc.getDocument();
                    Event event = document.toObject(Event.class);
                    event.setId(document.getId());
                    event.setEventImage(R.drawable.laptop);
                    Log.d(TAG, "onEvent: "+event.toString());
                    switch (dc.getType()) {
                        case ADDED:
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    eventDao.insert(event);
                                }
                            }).start();
                            break;

                        case MODIFIED:
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    eventDao.update(event);
                                }
                            }).start();
                            break;

                        case REMOVED:
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    eventDao.delete(event);
                                }
                            }).start();
                            break;

                    }

                }
            }
        });
    }

    // TODO (Girish):
    //  EventFragment work ->
    //  1) Implement SwipeRefreshLayout
    //  2) Implement separate class for Firebase
    //  3) Change ViewPagerLayout to RecyclerView
    //  4) Update design of upcoming events
    //  5) Make eventDetailsFragment
    //  6) Implement photos for Firebase
    //  7) Implement event notifications
    //  ContestFragment work ->
    //  1) Add filters in contestFragment
    //  2) Change design of contest_item
    //  Admin App work ->
    //  1) Connect firebase

}