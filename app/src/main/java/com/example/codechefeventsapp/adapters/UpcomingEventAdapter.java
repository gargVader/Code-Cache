package com.example.codechefeventsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.activities.EventDetailsActivity;
import com.example.codechefeventsapp.data.Utils;
import com.example.codechefeventsapp.data.models.Event;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UpcomingEventAdapter extends RecyclerView.Adapter<UpcomingEventAdapter.UpcomingEventViewHolder> {

    List<Event> eventList;
    OnItemClickListener listener;
    Context context;

    public UpcomingEventAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
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
        if (event.getEventImageUrl() != null) {
            Utils.loadImage(context, event.getEventImageUrl(), holder.eventImage, holder.progressBar);
        } else {
            holder.eventImage.setImageResource(R.drawable.event_poster);
        }
        holder.eventTitle.setText(event.getEventTitle());
        holder.eventDay.setText(Utils.getDay(event.getEventStartTimeStamp()));
        holder.eventMonth.setText(Utils.getMonth(event.getEventStartTimeStamp()));
        holder.eventTime.setText(Utils.getTimeWithDay(event.getEventStartTimeStamp()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class UpcomingEventViewHolder extends RecyclerView.ViewHolder {
        ImageView eventImage;
        TextView eventTitle, eventDay, eventMonth, eventTime;
        ProgressBar progressBar;

        public UpcomingEventViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.eventImage);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventDay = itemView.findViewById(R.id.eventDay);
            eventMonth = itemView.findViewById(R.id.eventMonth);
            eventTime = itemView.findViewById(R.id.eventTime);
            progressBar = itemView.findViewById(R.id.progress_load_photo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Event event = eventList.get(position);
                    Intent intent = new Intent(itemView.getContext(), EventDetailsActivity.class);
                    intent.putExtra("EVENT", event);
//                    ActivityOptions options = ActivityOptions
//                            .makeSceneTransitionAnimation((Activity) itemView.getContext(), eventImage, "eventImage");
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    public void setEventList(List<Event> eventList) {
//        Log.d(TAG, "setEventList: upcoming");
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
