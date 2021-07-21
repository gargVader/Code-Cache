package com.example.codechefeventsapp.FireStoreStorage;

import com.example.codechefeventsapp.data.models.Event;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    //Firestore
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference eventBookRef= db.collection("Event Book");

    //Add events
    public void addEvent(Event event){
        eventBookRef.add(event);
    }

    //Read events and return all events
    public ArrayList<Event> readEvents(){
        ArrayList<Event> events=new ArrayList<>();
        eventBookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            Event event=documentSnapshot.toObject(Event.class);
                            events.add(event);
                        }
                    }
                });
        return events;
    }

    //Checking Same Object or not
    public boolean checkPresence(Event eventdb,Event event){
        if(eventdb.getEventTitle().equals(event.getEventTitle()) && eventdb.getEventTimeStamp().equals(event.getEventTimeStamp())
            && eventdb.getEventLocation().equals(event.getEventLocation()) && eventdb.getEventImage()==event.getEventImage()){
            return true;
        }
        else {return false;}
    }

    //Update event
    public void updateEvent(Event oldEvent,Event newEvent){

        eventBookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String docPath="";
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            Event event=documentSnapshot.toObject(Event.class);
                            if(checkPresence(event,oldEvent)){
                                docPath+=documentSnapshot.getId();
                                break;
                            }
                        }
                        //Creating map
                        Map<String,Object> map=new HashMap<>();
                        map.put("eventTitle",newEvent.getEventTitle());
                        map.put("eventLocation",newEvent.getEventLocation());
                        map.put("eventTimeStamp",newEvent.getEventTimeStamp());
                        map.put("eventImage",newEvent.getEventImage());

                        eventBookRef.document(docPath).update(map);
                    }
                });
    }

    //Delete event
    public void deleteEvents(Event eventDel){
        eventBookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String docPath="";
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                            Event event=documentSnapshot.toObject(Event.class);
                            if(checkPresence(event,eventDel)){
                                docPath+=documentSnapshot.getId();
                                break;
                            }
                        }
                        eventBookRef.document(docPath).delete();
                    }
                });
    }
}
