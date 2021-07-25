package com.example.codechefeventsapp.FireStoreStorage;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.data.models.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.codechefeventsapp.activities.MainActivity.TAG;

/**
 * Class for all Firebase Operations
 */
public class FirebaseStorage {

    public static final String EVENT_COLLECTION_ID = "EventCollection";

    private static FirebaseStorage instance;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventCollection = db.collection(EVENT_COLLECTION_ID);
    FirebaseListener firebaseListener;

    private FirebaseStorage() {

    }

    public static FirebaseStorage getInstance() {
        if (instance == null) {
            instance = new FirebaseStorage();
        }
        return instance;
    }

    //Read events and return all events
    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        eventCollection.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Event event = documentSnapshot.toObject(Event.class);
                            event.setEventImage(R.drawable.laptop);
                            Log.d(TAG, "onSuccess: " + event.toString());
                            eventList.add(event);
                        }
                        firebaseListener.onSuccessGet();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                });
        return eventList;
    }

//    public void addEvent(Event event) {
//        eventCollection.add(event);
//    }
    //Checking Same Object or not
//    public boolean checkPresence(Event eventdb, Event event) {
//        if (eventdb.getEventTitle().equals(event.getEventTitle()) && eventdb.getEventTimeStamp().equals(event.getEventTimeStamp())
//                && eventdb.getEventLocation().equals(event.getEventLocation()) && eventdb.getEventImage() == event.getEventImage()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    //Update event
//    public void updateEvent(Event oldEvent, Event newEvent) {
//
//        eventCollection.get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        String docPath = "";
//                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                            Event event = documentSnapshot.toObject(Event.class);
//                            if (checkPresence(event, oldEvent)) {
//                                docPath += documentSnapshot.getId();
//                                break;
//                            }
//                        }
//                        //Creating map
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("eventTitle", newEvent.getEventTitle());
//                        map.put("eventLocation", newEvent.getEventLocation());
//                        map.put("eventTimeStamp", newEvent.getEventTimeStamp());
//                        map.put("eventImage", newEvent.getEventImage());
//
//                        eventCollection.document(docPath).update(map);
//                    }
//                });
//    }
//
//    //Delete event
//    public void deleteEvents(Event eventDel) {
//        eventCollection.get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        String docPath = "";
//                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
//                            Event event = documentSnapshot.toObject(Event.class);
//                            if (checkPresence(event, eventDel)) {
//                                docPath += documentSnapshot.getId();
//                                break;
//                            }
//                        }
//                        eventCollection.document(docPath).delete();
//                    }
//                });
//    }

    public interface FirebaseListener {
        void onSuccessGet();
    }

}
