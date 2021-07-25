package com.example.codechefeventsapp.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.activities.EventDetailsActivity;
import com.example.codechefeventsapp.data.Utils;
import com.example.codechefeventsapp.data.models.Event;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.example.codechefeventsapp.activities.MainActivity.TAG;

public class UpcomingEventAdapter extends RecyclerView.Adapter<UpcomingEventAdapter.UpcomingEventViewHolder> {

    List<Event> eventList;
    OnItemClickListener listener;

    public UpcomingEventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @NotNull
    @Override
    public UpcomingEventViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_upcoming, parent, false);
        return new UpcomingEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UpcomingEventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.eventImage.setImageResource(R.drawable.laptop);
        holder.eventTitle.setText(event.getEventTitle());
        holder.eventDay.setText(Utils.getDate(event.getEventTimeStamp()));
        holder.eventMonth.setText(Utils.getMonth(event.getEventTimeStamp()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class UpcomingEventViewHolder extends RecyclerView.ViewHolder {
        ImageView eventImage;
        TextView eventTitle, eventDay, eventMonth;

        public UpcomingEventViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.eventImage);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventDay = itemView.findViewById(R.id.eventDay);
            eventMonth = itemView.findViewById(R.id.eventMonth);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Event event = eventList.get(position);
                    Intent intent = new Intent(itemView.getContext(), EventDetailsActivity.class);
//                    ActivityOptions options = ActivityOptions
//                            .makeSceneTransitionAnimation((Activity) itemView.getContext(), eventImage, "eventImage");
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    public void setEventList(List<Event> eventList) {
        Log.d(TAG, "setEventList: upcoming");
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Event event);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
