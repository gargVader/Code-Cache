package com.example.codechefeventsapp.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "event_table")
public class Event {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;

    @SerializedName("eventTitle")
    String eventTitle;
    @SerializedName("eventLocation")
    String eventLocation;
    @SerializedName("eventTimeStamp")
    String eventTimeStamp;
    @SerializedName("eventImage")
    int eventImage;

    public Event(String eventTitle, String eventLocation, String eventTimeStamp, int eventImage) {
        this.eventTitle = eventTitle;
        this.eventLocation = eventLocation;
        this.eventTimeStamp = eventTimeStamp;
        this.eventImage = eventImage;
    }

    public Event(){

    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventTimeStamp() {
        return eventTimeStamp;
    }

    public void setEventTimeStamp(String eventTimeStamp) {
        this.eventTimeStamp = eventTimeStamp;
    }

    public int getEventImage() {
        return eventImage;
    }

    public void setEventImage(int eventImage) {
        this.eventImage = eventImage;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventTimeStamp='" + eventTimeStamp + '\'' +
                ", eventImage=" + eventImage +
                '}';
    }
}
