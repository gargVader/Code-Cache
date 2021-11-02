package com.example.codechefeventsapp.data.models.cf;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.codechefeventsapp.data.converters.CfProblemConverter;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "cf_user_submissions_table")
public class CfSubmission {

    @PrimaryKey(autoGenerate = false)
    long id;
    @SerializedName("contestId")
    int contestId;
    @SerializedName("creationTimeSeconds")
    long creationTimeSeconds;
    @SerializedName("relativeTimeSeconds")
    int relativeTimeSeconds;
    @TypeConverters(CfProblemConverter.class)
    @SerializedName("problem")
    CfProblem problem;
    @SerializedName("programmingLanguage")
    String programmingLanguage;
    @SerializedName("verdict")
    String verdict;
    @SerializedName("testset")
    String testset;
    @SerializedName("passedTestCount")
    int passedTestCount;
    @SerializedName("timeConsumedMillis")
    long timeConsumedMillis;
    @SerializedName("memoryConsumedBytes")
    long memoryConsumedBytes;

    public CfSubmission(long id, int contestId, long creationTimeSeconds, int relativeTimeSeconds, CfProblem problem, String programmingLanguage, String verdict, String testset, int passedTestCount, long timeConsumedMillis, long memoryConsumedBytes) {
        this.id = id;
        this.contestId = contestId;
        this.creationTimeSeconds = creationTimeSeconds;
        this.relativeTimeSeconds = relativeTimeSeconds;
        this.problem = problem;
        this.programmingLanguage = programmingLanguage;
        this.verdict = verdict;
        this.testset = testset;
        this.passedTestCount = passedTestCount;
        this.timeConsumedMillis = timeConsumedMillis;
        this.memoryConsumedBytes = memoryConsumedBytes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public long getCreationTimeSeconds() {
        return creationTimeSeconds;
    }

    public void setCreationTimeSeconds(long creationTimeSeconds) {
        this.creationTimeSeconds = creationTimeSeconds;
    }

    public int getRelativeTimeSeconds() {
        return relativeTimeSeconds;
    }

    public void setRelativeTimeSeconds(int relativeTimeSeconds) {
        this.relativeTimeSeconds = relativeTimeSeconds;
    }

    public CfProblem getProblem() {
        return problem;
    }

    public void setProblem(CfProblem problem) {
        this.problem = problem;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public String getTestset() {
        return testset;
    }

    public void setTestset(String testset) {
        this.testset = testset;
    }

    public int getPassedTestCount() {
        return passedTestCount;
    }

    public void setPassedTestCount(int passedTestCount) {
        this.passedTestCount = passedTestCount;
    }

    public long getTimeConsumedMillis() {
        return timeConsumedMillis;
    }

    public void setTimeConsumedMillis(long timeConsumedMillis) {
        this.timeConsumedMillis = timeConsumedMillis;
    }

    public long getMemoryConsumedBytes() {
        return memoryConsumedBytes;
    }

    public void setMemoryConsumedBytes(long memoryConsumedBytes) {
        this.memoryConsumedBytes = memoryConsumedBytes;
    }
}
