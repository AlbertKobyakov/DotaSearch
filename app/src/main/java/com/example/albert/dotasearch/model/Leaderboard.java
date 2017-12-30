package com.example.albert.dotasearch.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Leaderboard {

    @SerializedName("rank")
    @Expose
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
    @SerializedName("solo_mmr")
    @Expose
    @Ignore
    private Object soloMmr;
    @SerializedName("country")
    @Expose
    private String country;
    @PrimaryKey
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public Leaderboard withRank(long rank) {
        this.rank = rank;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Leaderboard withName(String name) {
        this.name = name;
        return this;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public Leaderboard withTeamId(long teamId) {
        this.teamId = teamId;
        return this;
    }

    public String getTeamTag() {
        return teamTag;
    }

    public void setTeamTag(String teamTag) {
        this.teamTag = teamTag;
    }

    public Leaderboard withTeamTag(String teamTag) {
        this.teamTag = teamTag;
        return this;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public Leaderboard withSponsor(String sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public Object getSoloMmr() {
        return soloMmr;
    }

    public void setSoloMmr(Object soloMmr) {
        this.soloMmr = soloMmr;
    }

    public Leaderboard withSoloMmr(Object soloMmr) {
        this.soloMmr = soloMmr;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Leaderboard withCountry(String country) {
        this.country = country;
        return this;
    }
}