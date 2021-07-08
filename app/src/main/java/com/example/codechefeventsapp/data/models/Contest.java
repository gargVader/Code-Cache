package com.example.codechefeventsapp.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "contest_table")
public class Contest {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("name")
    private String contestName;
    @SerializedName("url")
    private String contestUrl;
    @SerializedName("start_time")
    private String contestStartTime;
    @SerializedName("end_time")
    private String contestEndTime;
    @SerializedName("duration")
    private String contestDuration;
    @SerializedName("site")
    private String contestSite;
    @SerializedName("in_24_hours")
    private String contestIn24Hours;
    @SerializedName("status")
    private String contestStatus;

    public Contest(String contestName, String contestUrl, String contestStartTime, String contestEndTime, String contestDuration, String contestSite, String contestIn24Hours, String contestStatus) {
        this.contestName = contestName;
        this.contestUrl = contestUrl;
        this.contestStartTime = contestStartTime;
        this.contestEndTime = contestEndTime;
        this.contestDuration = contestDuration;
        this.contestSite = contestSite;
        this.contestIn24Hours = contestIn24Hours;
        this.contestStatus = contestStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getContestUrl() {
        return contestUrl;
    }

    public void setContestUrl(String contestUrl) {
        this.contestUrl = contestUrl;
    }

    public String getContestStartTime() {
        return contestStartTime;
    }

    public void setContestStartTime(String contestStartTime) {
        this.contestStartTime = contestStartTime;
    }

    public String getContestEndTime() {
        return contestEndTime;
    }

    public void setContestEndTime(String contestEndTime) {
        this.contestEndTime = contestEndTime;
    }

    public String getContestDuration() {
        return contestDuration;
    }

    public void setContestDuration(String contestDuration) {
        this.contestDuration = contestDuration;
    }

    public String getContestSite() {
        return contestSite;
    }

    public void setContestSite(String contestSite) {
        this.contestSite = contestSite;
    }

    public String getContestIn24Hours() {
        return contestIn24Hours;
    }

    public void setContestIn24Hours(String contestIn24Hours) {
        this.contestIn24Hours = contestIn24Hours;
    }

    public String getContestStatus() {
        return contestStatus;
    }

    public void setContestStatus(String contestStatus) {
        this.contestStatus = contestStatus;
    }

    @Override
    public String toString() {
        return "Contest{" +
                "id=" + id +
                ", contestName='" + contestName + '\'' +
                ", contestUrl='" + contestUrl + '\'' +
                ", contestStartTime='" + contestStartTime + '\'' +
                ", contestEndTime='" + contestEndTime + '\'' +
                ", contestDuration='" + contestDuration + '\'' +
                ", contestSite='" + contestSite + '\'' +
                ", contestIn24Hours='" + contestIn24Hours + '\'' +
                ", contestStatus='" + contestStatus + '\'' +
                '}';
    }
}
