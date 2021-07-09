package com.example.codechefeventsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.data.models.Event;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class pastEventAdapter extends RecyclerView.Adapter<pastEventAdapter.pastEventViewHolder>{
    ArrayList<Event> dataholder;

    public pastEventAdapter(ArrayList<Event> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @NotNull
    @Override
    public pastEventViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.past_event_item,parent,false);
        return new pastEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull pastEventViewHolder holder, int position) {
        holder.eventTitle.setText(dataholder.get(position).getEventTitle());
        holder.eventLocation.setText(dataholder.get(position).getEventLocation());
        holder.eventDate.setText(dataholder.get(position).getEventDate());
        holder.eventMonth.setText(dataholder.get(position).getEventMonth());
        holder.eventImage.setImageResource(dataholder.get(position).getEventImage());

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class pastEventViewHolder extends RecyclerView.ViewHolder{
        TextView eventTitle;
        TextView eventLocation;
        TextView eventMonth;
        TextView eventDate;
        ImageView eventImage;
        public pastEventViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            eventTitle=itemView.findViewById(R.id.pastEventName);
            eventLocation=itemView.findViewById(R.id.pastEventLocation);
            eventMonth=itemView.findViewById(R.id.eventMonth);
            eventDate=itemView.findViewById(R.id.eventDay);
            eventImage=itemView.findViewById(R.id.pastEventImage);
        }
    }
}
