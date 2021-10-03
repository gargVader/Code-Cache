package com.example.codechefeventsapp.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.codechefeventsapp.data.MyTypeConverter;
import com.google.firebase.firestore.Exclude;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Set;

@Entity(tableName = "event_table")
public class Event implements Serializable {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String id;

    @SerializedName("eventTitle")
    String eventTitle;
    @SerializedName("eventLocation")
    String eventLocation;
    @SerializedName("eventStartTimeStamp")
    String eventStartTimeStamp;
    @SerializedName("eventEndTimeStamp")
    String eventEndTimeStamp;
    @SerializedName("eventJoinLink")
    String eventJoinLink;
    @SerializedName("eventRecLink")
    String eventRecLink;
    @SerializedName("eventShortDescription")
    String eventShortDescription;
    @SerializedName("eventLongDescription")
    String eventLongDescription;
    @SerializedName("eventImageUrl")
    String eventImageUrl;
    @TypeConverters(MyTypeConverter.class)
    @SerializedName("eventAttendees")
    Set<User> eventAttendees;

    public Event(String eventTitle, String eventLocation, String eventTimeStamp, String eventImageUrl) {
        this.id = id;
        this.eventTitle = eventTitle;
        this.eventLocation = eventLocation;
        this.eventStartTimeStamp = eventTimeStamp;
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

    public String getEventStartTimeStamp() {
        return eventStartTimeStamp;
    }

    public void setEventStartTimeStamp(String eventStartTimeStamp) {
        this.eventStartTimeStamp = eventStartTimeStamp;
    }

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    public String getEventEndTimeStamp() {
        return eventEndTimeStamp;
    }

    public void setEventEndTimeStamp(String eventEndTimeStamp) {
        this.eventEndTimeStamp = eventEndTimeStamp;
    }

    public String getEventJoinLink() {
        return eventJoinLink;
    }

    public void setEventJoinLink(String eventJoinLink) {
        this.eventJoinLink = eventJoinLink;
    }

    public String getEventRecLink() {
        return eventRecLink;
    }

    public void setEventRecLink(String eventRecLink) {
        this.eventRecLink = eventRecLink;
    }

    public String getEventShortDescription() {
        return eventShortDescription;
    }

    public void setEventShortDescription(String eventShortDescription) {
        this.eventShortDescription = eventShortDescription;
    }

    public String getEventLongDescription() {
        return eventLongDescription;
    }

    public void setEventLongDescription(String eventLongDescription) {
        this.eventLongDescription = eventLongDescription;
    }

    public Set<User> getEventAttendees() {
        return eventAttendees;
    }

    public void setEventAttendees(Set<User> eventAttendees) {
        this.eventAttendees = eventAttendees;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventStartTimeStamp='" + eventStartTimeStamp + '\'' +
                ", eventEndTimeStamp='" + eventEndTimeStamp + '\'' +
                ", eventJoinLink='" + eventJoinLink + '\'' +
                ", eventRecLink='" + eventRecLink + '\'' +
                ", eventShortDescription='" + eventShortDescription + '\'' +
                ", eventLongDescription='" + eventLongDescription + '\'' +
                ", eventImageUrl='" + eventImageUrl + '\'' +
                ", eventAttendees=" + eventAttendees +
                '}';
    }
}
