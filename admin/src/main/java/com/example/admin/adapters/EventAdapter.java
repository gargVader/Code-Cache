package com.example.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.example.admin.data.Utils;
import com.example.admin.data.models.Event;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.eventViewHolder> {
    List<Event> eventList;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public eventViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new eventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull eventViewHolder holder, int position) {
        holder.eventName.setText(eventList.get(position).getEventTitle());
        holder.eventLocation.setText(eventList.get(position).getEventLocation());
        holder.eventDate.setText(Utils.getDate(eventList.get(position).getEventTimeStamp()));
        holder.eventTime.setText(Utils.getMonth(eventList.get(position).getEventTimeStamp()));
        holder.eventImage.setImageResource(eventList.get(position).getEventImage());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class eventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName;
        TextView eventLocation;
        TextView eventTime;
        TextView eventDate;
        ImageView eventImage;

        public eventViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventName);
            eventLocation = itemView.findViewById(R.id.eventLocation);
            eventTime= itemView.findViewById(R.id.eventTime);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventImage = itemView.findViewById(R.id.eventImage);
        }
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
}
