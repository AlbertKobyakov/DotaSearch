package com.kobyakov.d2s.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.kobyakov.d2s.TypeConverter.LeaderboardConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class TimeRefreshLeaderBoard {
    @SerializedName("time_posted")
    @Expose
    private long timePosted;
    @SerializedName("next_scheduled_post_time")
    @Expose
    private long nextScheduledPostTime;
    @SerializedName("server_time")
    @Expose
    private long serverTime;
    @SerializedName("leaderboard")
    @Expose
    @TypeConverters(LeaderboardConverter.class)
    private List<Leaderboard> leaderboard = null;
    @PrimaryKey
    @NonNull
    private String division = "";

    @NonNull
    public String getDivision() {
        return division;
    }

    public void setDivision(@NonNull String division) {
        this.division = division;
    }

    public long getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(long timePosted) {
        this.timePosted = timePosted;
    }

    public long getNextScheduledPostTime() {
        return nextScheduledPostTime;
    }

    public void setNextScheduledPostTime(long nextScheduledPostTime) {
        this.nextScheduledPostTime = nextScheduledPostTime;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public List<Leaderboard> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<Leaderboard> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
