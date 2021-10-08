package com.example.codechefeventsapp.data;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.codechefeventsapp.data.models.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class Utils {

    public static String getDate(String eventTimeStamp) {
        Date date = new Date(Long.parseLong(eventTimeStamp) * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        return formatter.format(date);
    }

    public static String getDateFull(String eventTimeStamp) {
        Date date = new Date(Long.parseLong(eventTimeStamp) * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM d yyyy");
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

    public static String getTimeWithDay(String eventTimeStamp) {
        Date date = new Date(Long.parseLong(eventTimeStamp) * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, h:mm a");
        return formatter.format(date);
    }

    public static String getTime(String eventTimeStamp) {
        Date date = new Date(Long.parseLong(eventTimeStamp) * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(date);
    }

    public static String getTimeElapsed(String eventTimeStamp) {
        long start = Long.parseLong(eventTimeStamp) * 1000;
        long end = System.currentTimeMillis();
        long seconds = (end - start) / (1000);
        return getDaysAgo(String.valueOf(seconds)) + " ago";
    }


    // %Y-%m-%dT%H:%M:%S.%LZ
    // 2021-10-02T14:30:00.000Z
    public static String getContestStartTime(String time) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm aaa MMM d, EEEE");
        Date date = null;
        try {
            date = inputFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = outputFormat.format(date);
        return formattedDate;

    }

    public static boolean isContestLive(String startTime, String endTime) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date start = null, end = null;
        try {
            start = inputFormat.parse(startTime);
            end = inputFormat.parse(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date now = new Date();

        return now.after(start) && now.before(end);
    }


    private static String getDaysAgo(String secondsString) {
        Float seconds = Float.parseFloat(secondsString);
        Float minutes = seconds / 60;
        Float hours = minutes / 60;
        Float days = hours / 24;
        Float years = days / 365;

        if (hours < 1) {
            return Math.round(minutes) + " min";
        }
        if (days < 1) {
            return Math.round(Math.floor(hours)) + " hours ";
        }

        if (years < 1) {
            return Math.round(Math.floor(days)) + " days ";
        }

        return Math.round(Math.floor(years)) + " years ";
    }


    public static String getContestDuration(String secondsString) {
        Float seconds = Float.parseFloat(secondsString);
        Float minutes = seconds / 60;
        Float hours = minutes / 60;
        Float days = hours / 24;
        Float years = days / 365;

        if (hours < 1) {
            return Math.round(minutes) + " min";
        }
        if (days < 1) {
            return Math.round(Math.floor(hours)) + " hours " + ((minutes % 60 > 0) ? (Math.round(minutes % 60) + " min") : "");
        }

        if (years < 1) {
            return Math.round(Math.floor(days)) + " days " + ((hours % 24 > 0) ? (Math.round(hours % 24) + " h") : "");
        }

        return Math.round(Math.floor(years)) + " years " + ((days % 365 > 0) ? (Math.round(days % 365) + " days") : "");
    }

    public static boolean isPastEvent(Event event) {
        if (event.getEventStartTimeStamp() == null) {
        }
        Date currentDate = new Date(System.currentTimeMillis());
        Date eventDate = new Date(Long.parseLong(event.getEventStartTimeStamp()) * 1000);
        return eventDate.before(currentDate);
    }

    public static class Constants {
        public static String userEmail = "xyz@gmail.com";
        //public static GoogleSignInAccount account;
    }

    static class SortByTimeStamp implements Comparator<Event> {

        boolean reverse = false;

        public SortByTimeStamp(boolean reverse) {
            this.reverse = reverse;
        }

        public SortByTimeStamp() {
        }

        @Override
        public int compare(Event e1, Event e2) {
            if (reverse) {
                return e2.getEventStartTimeStamp().compareTo(e1.getEventStartTimeStamp());
            } else {
                return e1.getEventStartTimeStamp().compareTo(e2.getEventStartTimeStamp());
            }
        }
    }

    public static void sort(List<Event> eventList) {
        Collections.sort(eventList, new SortByTimeStamp());
    }

    public static void reverseSort(List<Event> eventList) {
        Collections.sort(eventList, new SortByTimeStamp(true));
    }


    public static String getFormattedDate(String dateStr) throws ParseException {
        //String dateStr = "Jul 16, 2013 12:08:59 AM";
        SimpleDateFormat df = new SimpleDateFormat("MMM dd HH:mm:ss a", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = df.parse(dateStr);
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(date);
        return formattedDate;
    }

    public static String[] vibrantLightColorList = {"#ffeead", "#93cfb3", "#fd7a7a", "#faca5f",
            "#1ba798", "#6aa9ae", "#ffbf27", "#d93947"};

    public static String getRandomDrawableColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

    public static void loadImage(Context context, String imageUrl, ImageView imageView, ProgressBar progressBar) {
        // Setting up Glide
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(new ColorDrawable(Color.parseColor(getRandomDrawableColor())));
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context.getApplicationContext())
                .load(imageUrl)
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (progressBar != null) progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (progressBar != null) progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }


}
