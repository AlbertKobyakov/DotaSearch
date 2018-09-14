package com.example.albert.dotasearch.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Leaderboard {
    @SerializedName("rank")
    @Expose
    @PrimaryKey
    private long rank;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("team_id")
    @Expose
    private long teamId;
    @SerializedName("team_tag")
    @Expose
    private String teamTag;
    @SerializedName("sponsor")
    @Expose
    private String sponsor;
    @SerializedName("country")
    @Expose
    private String country;

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getTeamTag() {
        return teamTag;
    }

    public void setTeamTag(String teamTag) {
        this.teamTag = teamTag;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Leaderboard{" +
                "rank=" + rank +
                ", name='" + name + '\'' +
                ", teamId=" + teamId +
                ", teamTag='" + teamTag + '\'' +
                ", sponsor='" + sponsor + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}