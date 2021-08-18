package com.example.codechefeventsapp.data;

import com.example.codechefeventsapp.data.models.Event;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Utils {

    public static String getDate(String eventTimeStamp) {
        Date date = new Date(Long.parseLong(eventTimeStamp) * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        return formatter.format(date);
    }

    public static String getDay(String eventTimeStamp) {
        Date date = new Date(Long.parseLong(eventTimeStamp) * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        return formatter.format(date);
    }

    public static String getMonth(String eventTimeStamp) {
        Date date = new Date(Long.parseLong(eventTimeStamp) * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("MMM");
        return formatter.format(date).toUpperCase();
    }

    public static boolean isPastEvent(Event event) {
        Date currentDate = new Date(System.currentTimeMillis());
        Date eventDate = new Date(Long.parseLong(event.getEventTimeStamp()) * 1000);
        return eventDate.before(currentDate);
    }

    public static class Constants{
        public static String userEmail = "xyz@gmail.com";
        //public static GoogleSignInAccount account;
    }

    static class SortByTimeStamp implements Comparator<Event>{
        @Override
        public int compare(Event e1, Event e2) {
            return e2.getEventTimeStamp().compareTo(e1.getEventTimeStamp());
        }
    }

    public static void sort(List<Event> eventList){
        Collections.sort(eventList, new SortByTimeStamp());
    }


}
