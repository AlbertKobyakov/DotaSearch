package com.kobyakov.d2s.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamPlayer {

    @SerializedName("account_id")
    @Expose
    private long accountId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("games_played")
    @Expose
    private long gamesPlayed;
    @SerializedName("wins")
    @Expose
    private long wins;
    @SerializedName("is_current_team_member")
    @Expose
    private boolean isCurrentTeamMember;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(long gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public long getWins() {
        return wins;
    }

    public void setWins(long wins) {
        this.wins = wins;
    }

    public boolean isIsCurrentTeamMember() {
        return isCurrentTeamMember;
    }

    public void setIsCurrentTeamMember(boolean isCurrentTeamMember) {
        this.isCurrentTeamMember = isCurrentTeamMember;
    }

}
