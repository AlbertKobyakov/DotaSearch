package com.example.albert.dotasearch.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Team {
    @PrimaryKey
    @SerializedName("team_id")
    @Expose
    private long teamId;
    @SerializedName("rating")
    @Expose
    private double rating;
    @SerializedName("wins")
    @Expose
    private long wins;
    @SerializedName("losses")
    @Expose
    private long losses;
    @SerializedName("last_match_time")
    @Expose
    private long lastMatchTime;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("logo_url")
    @Expose
    private String logoUrl;

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public long getLosses() {
        return losses;
    }

    public void setLosses(long losses) {
        this.losses = losses;
    }

    public long getLastMatchTime() {
        return lastMatchTime;
    }

    public void setLastMatchTime(long lastMatchTime) {
        this.lastMatchTime = lastMatchTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
