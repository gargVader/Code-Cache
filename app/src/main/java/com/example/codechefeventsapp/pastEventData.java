package com.example.codechefeventsapp;

public class pastEventData {
     String eventName,start,end,duration,type;

    public pastEventData(String eventName, String start, String end, String duration, String type) {
        this.eventName = eventName;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.type = type;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
