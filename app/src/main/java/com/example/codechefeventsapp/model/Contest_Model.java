package com.example.codechefeventsapp.model;

public class Contest_Model {

    String name,url,start,end,duration,in24hour,status;
    int icon;

    public Contest_Model(String name, String url, String start, String end, String duration, String in24hour, String status, int icon) {
        this.name = name;
        this.url = url;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.in24hour = in24hour;
        this.status = status;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getIn24hour() {
        return in24hour;
    }

    public void setIn24hour(String in24hour) {
        this.in24hour = in24hour;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
