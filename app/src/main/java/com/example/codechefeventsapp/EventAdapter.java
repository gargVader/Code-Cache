package com.example.codechefeventsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class EventAdapter extends PagerAdapter {

    private List<Event> eventList;
    private LayoutInflater layoutInflater;
    private Context context;

    ImageView eventImage;
    TextView eventTitle, eventDay, eventMonth;

    public EventAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.event_item_1, container, false);
        getView(view);
        setView(position);
        container.addView(view, 0);

        if(position==0){
             LinearLayout linearLayout = view.findViewById(R.id.linerLayout);
        }
        return view;
    }

    private void getView(View view){
        eventImage = view.findViewById(R.id.eventImage);
        eventTitle = view.findViewById(R.id.eventTitle);
        eventDay = view.findViewById(R.id.eventDay);
        eventMonth = view.findViewById(R.id.eventMonth);
    }

    private void setView(int position){
        Event event = eventList.get(position);
        eventImage.setImageResource(event.getEventImage());
        eventTitle.setText(event.getEventTitle());
        eventDay.setText("25");
        eventMonth.setText("Mar");
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
