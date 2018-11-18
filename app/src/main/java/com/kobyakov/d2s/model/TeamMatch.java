package com.kobyakov.d2s.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamMatch {
    @SerializedName("match_id")
    @Expose
    private long matchId;
    @SerializedName("radiant_win")
    @Expose
    private boolean radiantWin;
    @SerializedName("radiant")
    @Expose
    private boolean radiant;
    @SerializedName("duration")
    @Expose
    private long duration;
    @SerializedName("start_time")
    @Expose
    private long startTime;
    @SerializedName("leagueid")
    @Expose
    private long leagueid;
    @SerializedName("league_name")
    @Expose
    private String leagueName;
    @SerializedName("cluster")
    @Expose
    private long cluster;
    @SerializedName("opposing_team_id")
    @Expose
    private long opposingTeamId;
    @SerializedName("opposing_team_name")
    @Expose
    private String opposingTeamName;
    @SerializedName("opposing_team_logo")
    @Expose
    private String opposingTeamLogo;

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public boolean isRadiantWin() {
        return radiantWin;
    }

    public void setRadiantWin(boolean radiantWin) {
        this.radiantWin = radiantWin;
    }

    public boolean isRadiant() {
        return radiant;
    }

    public void setRadiant(boolean radiant) {
        this.radiant = radiant;
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

    public long getCluster() {
        return cluster;
    }

    public void setCluster(long cluster) {
        this.cluster = cluster;
    }

    public long getOpposingTeamId() {
        return opposingTeamId;
    }

    public void setOpposingTeamId(long opposingTeamId) {
        this.opposingTeamId = opposingTeamId;
    }

    public String getOpposingTeamName() {
        return opposingTeamName;
    }

    public void setOpposingTeamName(String opposingTeamName) {
        this.opposingTeamName = opposingTeamName;
    }

    public String getOpposingTeamLogo() {
        return opposingTeamLogo;
    }

    public void setOpposingTeamLogo(String opposingTeamLogo) {
        this.opposingTeamLogo = opposingTeamLogo;
    }
}
