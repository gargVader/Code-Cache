package com.example.codechefeventsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.activities.EventDetailsActivity;
import com.example.codechefeventsapp.data.Utils;
import com.example.codechefeventsapp.data.models.Event;

import org.jetbrains.annotations.NotNull;
import static com.example.codechefeventsapp.activities.MainActivity.TAG;

import java.util.List;

public class PastEventAdapter extends RecyclerView.Adapter<PastEventAdapter.PastEventViewHolder> {
    List<Event> eventList;
    OnItemClickListener listener;
    Context context;

    public PastEventAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public PastEventViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_past2, parent, false);
        return new PastEventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PastEventViewHolder holder, int position) {
        Event event = eventList.get(position);
        if (event.getEventImageUrl() != null) {
            Utils.loadImage(context, event.getEventImageUrl(), holder.eventImage, null);
        } else {
            holder.eventImage.setImageResource(R.drawable.event_poster);
        }
        holder.eventTitle.setText(event.getEventTitle());
        holder.eventDay.setText(Utils.getDay(event.getEventStartTimeStamp()));
        holder.eventMonth.setText(Utils.getMonth(event.getEventStartTimeStamp()));
        if (isUseable(event.getEventShortDescription())) {
            holder.eventDescription.setVisibility(View.VISIBLE);
            holder.eventDescription.setText(event.getEventShortDescription());
        } else if (isUseable(event.getEventLongDescription())) {
            holder.eventDescription.setVisibility(View.VISIBLE);
            holder.eventDescription.setText(event.getEventLongDescription());
        }else{
            holder.eventDescription.setVisibility(View.GONE);
        }
        if (isUseable(event.getEventRecLink())) {
            holder.eventYoutubeLinkLayout.setVisibility(View.VISIBLE);
            holder.eventYoutubeLink.setClickable(true);
            holder.eventYoutubeLink.setMovementMethod(LinkMovementMethod.getInstance());
            String text = "<a href='"+event.getEventRecLink()+"'>Watch Session </a>";
            holder.eventYoutubeLink.setText(Html.fromHtml(text));
        } else {
            holder.eventYoutubeLinkLayout.setVisibility(View.GONE);
        }
    }

    boolean isUseable(String string) {
        return string != null && !(string.trim().isEmpty());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class PastEventViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle, eventMonth, eventDay, eventDescription, eventYoutubeLink;
        LinearLayout eventYoutubeLinkLayout;
        ImageView eventImage;
        //ImageView eventImage;

        public PastEventViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.eventTitle);
            eventMonth = itemView.findViewById(R.id.eventMonth);
            eventDay = itemView.findViewById(R.id.eventDay);
            eventDescription = itemView.findViewById(R.id.eventDescription);
            eventYoutubeLinkLayout = itemView.findViewById(R.id.eventYoutubeLinkLayout);
            eventYoutubeLink = itemView.findViewById(R.id.eventYoutubeLink);
            eventImage = itemView.findViewById(R.id.eventImage);

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
