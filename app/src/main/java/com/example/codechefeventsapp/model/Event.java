package com.example.codechefeventsapp.model;

/**
 * Model Class for an Event
 */
public class Event {

    String eventTitle;
    String eventLocation;
    String eventMonth;
    String eventDate;
    int eventImage;

    public Event(String eventTitle, String eventLocation, String eventMonth, String eventDate, int eventImage) {
        this.eventTitle = eventTitle;
        this.eventLocation = eventLocation;
        this.eventMonth = eventMonth;
        this.eventDate = eventDate;
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

    public String getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(String eventMonth) {
        this.eventMonth = eventMonth;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public int getEventImage() {
        return eventImage;
    }

    public void setEventImage(int eventImage) {
        this.eventImage = eventImage;
    }
}
