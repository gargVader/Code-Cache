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
    @SerializedName("eventImageUrl")
    String eventImageUrl;

    public Event(String eventTitle, String eventLocation, String eventTimeStamp, String eventImageUrl) {
        this.id = id;
        this.eventTitle = eventTitle;
        this.eventLocation = eventLocation;
        this.eventTimeStamp = eventTimeStamp;
        this.eventImageUrl = eventImageUrl;
    }

    public Event() {

    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventTimeStamp='" + eventTimeStamp + '\'' +
                ", eventImageUrl='" + eventImageUrl + '\'' +
                '}';
    }
}
