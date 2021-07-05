package com.example.codechefeventsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.model.Contest_Model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ContestAdapter extends  RecyclerView.Adapter<ContestAdapter.contestViewholder>{
    ArrayList<Contest_Model> dataholder;

    public ContestAdapter(ArrayList<Contest_Model> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @NotNull
    @Override
    public contestViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_item_1,parent,false);
        return  new contestViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull contestViewholder holder, int position) {
        holder.icon.setImageResource(dataholder.get(position).getIcon());
        holder.name.setText(dataholder.get(position).getName());
        holder.url.setText(dataholder.get(position).getUrl());
        holder.start.setText(dataholder.get(position).getStart());
        holder.end.setText(dataholder.get(position).getEnd());
        holder.duration.setText(dataholder.get(position).getDuration());
        holder.in24hour.setText(dataholder.get(position).getIn24hour());
        holder.status.setText(dataholder.get(position).getStatus());


    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class contestViewholder extends RecyclerView.ViewHolder{

        TextView name,url,start,end,duration,in24hour,status;
        ImageView icon;

        public contestViewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.contest_Name);
            url=itemView.findViewById(R.id.contest_Url);
            start=itemView.findViewById(R.id.start);
            end=itemView.findViewById(R.id.end);
            duration=itemView.findViewById(R.id.duration);
            in24hour=itemView.findViewById(R.id.in24hour);
            status=itemView.findViewById(R.id.status);
            icon=itemView.findViewById(R.id.icon);

        }
    }
}
