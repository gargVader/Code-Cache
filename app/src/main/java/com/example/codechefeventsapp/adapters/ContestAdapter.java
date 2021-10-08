package com.example.codechefeventsapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codechefeventsapp.R;
import com.example.codechefeventsapp.data.Utils;
import com.example.codechefeventsapp.data.models.Contest;

import java.util.List;

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.ContestViewHolder> {

    List<Contest> contestList;
    OnItemClickListener listener;
    Context context;

    public ContestAdapter(List<Contest> contestList, Context context) {
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
        holder.contestStartTime.setText(Utils.getContestStartTime(contest.getContestStartTime()));
        holder.contestDuration.setText(Utils.getContestDuration(contest.getContestDuration()));
        setContestIcon(contest.getContestSite(), holder);
        if (Utils.isContestLive(contest.getContestStartTime(), contest.getContestEndTime())){
            Log.d("Codechef", "onBindViewHolder: isLive=true");
            holder.contestLiveCard.setVisibility(View.VISIBLE);
        }else{
            Log.d("Codechef", "onBindViewHolder: isLive=false");
            holder.contestLiveCard.setVisibility(View.GONE);
        }
    }

    void setContestIcon(String contestSite, ContestAdapter.ContestViewHolder holder) {
        if (contestSite == null) return;
        int drawable;
        switch (contestSite) {
            case "CodeForces":
                drawable = R.drawable.codeforces_icon;
                break;
            case "CodeChef":
                drawable = R.drawable.cc_icon_white;
                break;
            case "AtCoder":
                drawable = R.drawable.atcoder_icon_white;
                break;
            case "LeetCode":
                drawable = R.drawable.leetcode_icon;
                break;
            case "HackerEarth":
                drawable = R.drawable.hackerearth_icon_white;
                break;
            case "HackerRank":
                drawable = R.drawable.hackerrank_icon;
                break;
            case "Kick Start":
                drawable = R.drawable.kickstart_icon;
                break;
            case "TopCoder":
                drawable = R.drawable.topcoder_icon;
                break;
            default:
                drawable = R.drawable.ic_baseline_code_24;
                break;
        }
        holder.contestIcon.setImageResource(drawable);
    }


    @Override
    public int getItemCount() {
        return contestList.size();
    }

    protected class ContestViewHolder extends RecyclerView.ViewHolder {

        public TextView contestName, contestUrl, contestStartTime, contestEndTime, contestDuration, contestSite, contestIn24Hours, contestStatus;
        public ImageView contestIcon;
        public CardView contestLiveCard;

        public ContestViewHolder(View itemView) {
            super(itemView);
            contestIcon = itemView.findViewById(R.id.site_icon);
            contestName = itemView.findViewById(R.id.contest_Name);
            contestStartTime = itemView.findViewById(R.id.start);
            contestDuration = itemView.findViewById(R.id.duration);
            contestLiveCard = itemView.findViewById(R.id.contestLiveCard);

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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
