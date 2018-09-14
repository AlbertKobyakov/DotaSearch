package com.example.albert.dotasearch.model;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(primaryKeys = {"matchId", "recordTitle"})
public class Record {
    @NonNull
    private String recordTitle = "undefined";
    @NonNull
    @SerializedName("match_id")
    @Expose
    private String matchId = "0";
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("hero_id")
    @Expose
    private String heroId;
    @SerializedName("score")
    @Expose
    private String score;
    private String heroName;
    private String heroImgUrl;

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroImgUrl() {
        return heroImgUrl;
    }

    public void setHeroImgUrl(String heroImgUrl) {
        this.heroImgUrl = heroImgUrl;
    }

    @NonNull
    public String getRecordTitle() {
        return recordTitle;
    }

    public void setRecordTitle(@NonNull String recordTitle) {
        this.recordTitle = recordTitle;
    }

    @NonNull
    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(@NonNull String matchId) {
        this.matchId = matchId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getHeroId() {
        return heroId;
    }

    public void setHeroId(String heroId) {
        this.heroId = heroId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
