package com.example.codechefeventsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class EventSliderAdapter extends RecyclerView.Adapter<EventSliderAdapter.EventSliderViewHolder> {

    private List<Event> eventList;
    private ViewPager2 viewPager2;

    public EventSliderAdapter(List<Event> eventList, ViewPager2 viewPager2) {
        this.eventList = eventList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public EventSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventSliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.event_item_1,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull EventSliderAdapter.EventSliderViewHolder holder, int position) {
        holder.setView(eventList.get(position));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventSliderViewHolder extends RecyclerView.ViewHolder {

        ImageView eventImage;
        TextView eventTitle, eventDay, eventMonth;

        EventSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.eventImage);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventDay = itemView.findViewById(R.id.eventDay);
            eventMonth = itemView.findViewById(R.id.eventMonth);
        }

        private void setView(Event event) {
            eventImage.setImageResource(event.getEventImage());
            eventTitle.setText(event.getEventTitle());
            eventDay.setText("25");
            eventMonth.setText("Mar");
        }

    }

}
