package com.example.albert.dotasearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProMatch {

    @SerializedName("match_id")
    @Expose
    private long matchId;
    @SerializedName("duration")
    @Expose
    private long duration;
    @SerializedName("start_time")
    @Expose
    private long startTime;
    @SerializedName("radiant_team_id")
    @Expose
    private long radiantTeamId;
    @SerializedName("radiant_name")
    @Expose
    private String radiantName;
    @SerializedName("dire_team_id")
    @Expose
    private long direTeamId;
    @SerializedName("dire_name")
    @Expose
    private String direName;
    @SerializedName("leagueid")
    @Expose
    private long leagueid;
    @SerializedName("league_name")
    @Expose
    private String leagueName;
    @SerializedName("series_id")
    @Expose
    private long seriesId;
    @SerializedName("series_type")
    @Expose
    private long seriesType;
    @SerializedName("radiant_score")
    @Expose
    private long radiantScore;
    @SerializedName("dire_score")
    @Expose
    private long direScore;
    @SerializedName("radiant_win")
    @Expose
    private boolean radiantWin;

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getRadiantTeamId() {
        return radiantTeamId;
    }

    public void setRadiantTeamId(long radiantTeamId) {
        this.radiantTeamId = radiantTeamId;
    }

    public String getRadiantName() {
        return radiantName;
    }

    public void setRadiantName(String radiantName) {
        this.radiantName = radiantName;
    }

    public long getDireTeamId() {
        return direTeamId;
    }

    public void setDireTeamId(long direTeamId) {
        this.direTeamId = direTeamId;
    }

    public String getDireName() {
        return direName;
    }

    public void setDireName(String direName) {
        this.direName = direName;
    }

    public long getLeagueid() {
        return leagueid;
    }

    public void setLeagueid(long leagueid) {
        this.leagueid = leagueid;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(long seriesId) {
        this.seriesId = seriesId;
    }

    public long getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(long seriesType) {
        this.seriesType = seriesType;
    }

    public long getRadiantScore() {
        return radiantScore;
    }

    public void setRadiantScore(long radiantScore) {
        this.radiantScore = radiantScore;
    }

    public long getDireScore() {
        return direScore;
    }

    public void setDireScore(long direScore) {
        this.direScore = direScore;
    }

    public boolean isRadiantWin() {
        return radiantWin;
    }

    public void setRadiantWin(boolean radiantWin) {
        this.radiantWin = radiantWin;
    }

}
