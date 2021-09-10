package com.example.codechefeventsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.data.Utils;
import com.example.codechefeventsapp.data.models.Contest;

import java.text.ParseException;
import java.util.List;

import static com.example.codechefeventsapp.activities.MainActivity.TAG;

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.ContestViewHolder> {

    List<Contest> contestList;
    OnItemClickListener listener;
    Context context;
    public ContestAdapter(List<Contest> contestList, Context context) {
//        Log.d(TAG, "ContestAdapter: ");
        this.contestList = contestList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_item, parent, false);
        return new ContestViewHolder(view);
    }

    public void setContestList(List<Contest> contestList) {
        this.contestList = contestList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ContestAdapter.ContestViewHolder holder, int position) {
        Contest contest = contestList.get(position);
        holder.contestName.setText(contest.getContestName());
       // holder.contestUrl.setText(contest.getContestUrl());
        try {
            holder.contestStartTime.setText(Utils.getFormattedDate(contest.getContestStartTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // holder.contestEndTime.setText(contest.getContestEndTime());
        holder.contestDuration.setText(String.valueOf(contest.getContestDuration()));
       // holder.contestSite.setText(contest.getContestSite());
       // holder.contestIn24Hours.setText(contest.getContestIn24Hours());
        //holder.contestStatus.setText(contest.getContestStatus());
        if(contest.getContestSite() != null && contest.getContestSite().equals("CodeChef")){
            holder.contestIcon.setImageResource(R.drawable.codechef_icon);
        }
        else if(contest.getContestSite() != null && contest.getContestSite().equals("CodeForces")){
            holder.contestIcon.setImageResource(R.drawable.codeforces_icon);
        }
        else if(contest.getContestSite() != null && contest.getContestSite().equals("AtCoder")){
            holder.contestIcon.setImageResource(R.drawable.atcoder_icon);
        }
        else if(contest.getContestSite() != null && contest.getContestSite().equals("HackerEarth")){
            holder.contestIcon.setImageResource(R.drawable.hackerearth_icon);
        }
        else if(contest.getContestSite() != null && contest.getContestSite().equals("HackerRank")){
            holder.contestIcon.setImageResource(R.drawable.hackerrank_icon);
        }
        else {
            holder.contestIcon.setImageResource(R.drawable.codeforces_icon);
        }
    }

    @Override
    public int getItemCount() {
        return contestList.size();
    }

    protected class ContestViewHolder extends RecyclerView.ViewHolder {

        public TextView contestName, contestUrl, contestStartTime, contestEndTime, contestDuration, contestSite, contestIn24Hours, contestStatus;
        public de.hdodenhof.circleimageview.CircleImageView contestIcon;

        public ContestViewHolder(View itemView) {
            super(itemView);
            contestIcon = itemView.findViewById(R.id.site_icon);
            contestName = itemView.findViewById(R.id.contest_Name);
            contestStartTime = itemView.findViewById(R.id.start);
            //contestEndTime = itemView.findViewById(R.id.end);
            contestDuration = itemView.findViewById(R.id.duration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick(contestList.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Contest contest);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
