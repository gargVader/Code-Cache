package com.example.codechefeventsapp.data.models;

/**
 * Model Class for an Event
 */
public class Event {

    String eventTitle;
    String eventLocation;
    String eventTimeStamp;
    int eventImage;

    public Event(String eventTitle, String eventLocation, String eventTimeStamp, int eventImage) {
        this.eventTitle = eventTitle;
        this.eventLocation = eventLocation;
        this.eventTimeStamp = eventTimeStamp;
        this.eventImage = eventImage;
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


}
