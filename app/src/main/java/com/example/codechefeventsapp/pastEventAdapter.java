package com.example.codechefeventsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class pastEventAdapter extends RecyclerView.Adapter<pastEventAdapter.pastEventViewHolder>{
    ArrayList<pastEventData> dataholder;

    public pastEventAdapter(ArrayList<pastEventData> dataholder) {
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
        holder.eventName.setText(dataholder.get(position).getEventName());
        holder.start.setText(dataholder.get(position).getStart());
        holder.end.setText(dataholder.get(position).getEnd());
        holder.duration.setText(dataholder.get(position).getDuration());
        holder.type.setText(dataholder.get(position).getType());

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class pastEventViewHolder extends RecyclerView.ViewHolder{
        TextView eventName;
        TextView start;
        TextView end;
        TextView duration;
        TextView type;
        public pastEventViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            eventName=itemView.findViewById(R.id.pastEventName);
            start=itemView.findViewById(R.id.pastEventStartTime);
            end=itemView.findViewById(R.id.pastEventEndTime);
            duration=itemView.findViewById(R.id.eventDuration);
            type=itemView.findViewById(R.id.eventType);
        }
    }
}
