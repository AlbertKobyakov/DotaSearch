package com.example.albert.dotasearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    private List<Leaderboard> leaderboard = null;

    public long getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(long timePosted) {
        this.timePosted = timePosted;
    }

    public TimeRefreshLeaderBoard withTimePosted(long timePosted) {
        this.timePosted = timePosted;
        return this;
    }

    public long getNextScheduledPostTime() {
        return nextScheduledPostTime;
    }

    public void setNextScheduledPostTime(long nextScheduledPostTime) {
        this.nextScheduledPostTime = nextScheduledPostTime;
    }

    public TimeRefreshLeaderBoard withNextScheduledPostTime(long nextScheduledPostTime) {
        this.nextScheduledPostTime = nextScheduledPostTime;
        return this;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public TimeRefreshLeaderBoard withServerTime(long serverTime) {
        this.serverTime = serverTime;
        return this;
    }

    public List<Leaderboard> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<Leaderboard> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public TimeRefreshLeaderBoard withLeaderboard(List<Leaderboard> leaderboard) {
        this.leaderboard = leaderboard;
        return this;
    }
}
