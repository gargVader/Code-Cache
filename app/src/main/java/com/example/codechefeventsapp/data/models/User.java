package com.example.codechefeventsapp.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("profile_url")
    private String profileUrl;
    @SerializedName("codechef_handle")
    private String codeChefHandle;
    @SerializedName("codeforces_handle")
    private String codeforcesHandle;
    @SerializedName("hacker_earth_handle")
    private String hackerEarthHandle;
    @SerializedName("followings")
    private int followings;
    @SerializedName("followers")
    private int followers;
    @SerializedName("institute_name")
    private String instituteName;

    public User(String email, String fullName, String userName) {
        this.email = email;
        this.fullName = fullName;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getCodeChefHandle() {
        return codeChefHandle;
    }

    public void setCodeChefHandle(String codeChefHandle) {
        this.codeChefHandle = codeChefHandle;
    }

    public String getCodeforcesHandle() {
        return codeforcesHandle;
    }

    public void setCodeforcesHandle(String codeforcesHandle) {
        this.codeforcesHandle = codeforcesHandle;
    }

    public String getHackerEarthHandle() {
        return hackerEarthHandle;
    }

    public void setHackerEarthHandle(String hackerEarthHandle) {
        this.hackerEarthHandle = hackerEarthHandle;
    }

    public int getFollowings() {
        return followings;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }
}
