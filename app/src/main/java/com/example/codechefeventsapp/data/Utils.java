package com.example.codechefeventsapp.data;

import com.example.codechefeventsapp.data.models.Event;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getDate(String eventTimeStamp) {
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

}
