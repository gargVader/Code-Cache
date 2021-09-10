package com.example.codechefeventsapp.adapters;

import static com.example.codechefeventsapp.activities.MainActivity.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.activities.EventDetailsActivity;
import com.example.codechefeventsapp.data.Utils;
import com.example.codechefeventsapp.data.models.Event;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PastEventAdapter extends RecyclerView.Adapter<PastEventAdapter.PastEventViewHolder> {
    List<Event> eventList;
    OnItemClickListener listener;

    public PastEventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public PastEventViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_past, parent, false);
        return new PastEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PastEventViewHolder holder, int position) {
        holder.eventTitle.setText(eventList.get(position).getEventTitle());
        holder.eventDate.setText(Utils.getDate(eventList.get(position).getEventTimeStamp()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class PastEventViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle;
        TextView eventDate;
        //ImageView eventImage;

        public PastEventViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.pastEventName);
            eventDate = itemView.findViewById(R.id.eventDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Event event = eventList.get(position);
                    Intent intent = new Intent(itemView.getContext(), EventDetailsActivity.class);
                    intent.putExtra("EVENT", event);
                    itemView.getContext().startActivity(intent);

                }
            });
        }
    }

    public void setEventList(List<Event> eventList) {
//        Log.d(TAG, "setEventList: past");
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
