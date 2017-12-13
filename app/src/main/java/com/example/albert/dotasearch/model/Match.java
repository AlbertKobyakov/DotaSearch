package com.example.albert.dotasearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Match {

    @SerializedName("match_id")
    @Expose
    private Long matchId;
    @SerializedName("player_slot")
    @Expose
    private long playerSlot;
    @SerializedName("radiant_win")
    @Expose
    private boolean radiantWin;
    @SerializedName("duration")
    @Expose
    private long duration;
    @SerializedName("game_mode")
    @Expose
    private long gameMode;
    @SerializedName("lobby_type")
    @Expose
    private long lobbyType;
    @SerializedName("hero_id")
    @Expose
    private long heroId;
    @SerializedName("start_time")
    @Expose
    private long startTime;
    @SerializedName("version")
    @Expose
    private long version;
    @SerializedName("kills")
    @Expose
    private long kills;
    @SerializedName("deaths")
    @Expose
    private long deaths;
    @SerializedName("assists")
    @Expose
    private long assists;
    @SerializedName("skill")
    @Expose
    private Object skill;
    @SerializedName("leaver_status")
    @Expose
    private long leaverStatus;
    @SerializedName("party_size")
    @Expose
    private long partySize;
    private boolean win;

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public Match withMatchId(long matchId) {
        this.matchId = matchId;
        return this;
    }

    public long getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(long playerSlot) {
        this.playerSlot = playerSlot;
    }

    public Match withPlayerSlot(long playerSlot) {
        this.playerSlot = playerSlot;
        return this;
    }

    public boolean isRadiantWin() {
        return radiantWin;
    }

    public void setRadiantWin(boolean radiantWin) {
        this.radiantWin = radiantWin;
    }

    public Match withRadiantWin(boolean radiantWin) {
        this.radiantWin = radiantWin;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Match withDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public long getGameMode() {
        return gameMode;
    }

    public void setGameMode(long gameMode) {
        this.gameMode = gameMode;
    }

    public Match withGameMode(long gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    public long getLobbyType() {
        return lobbyType;
    }

    public void setLobbyType(long lobbyType) {
        this.lobbyType = lobbyType;
    }

    public Match withLobbyType(long lobbyType) {
        this.lobbyType = lobbyType;
        return this;
    }

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

    public Match withHeroId(long heroId) {
        this.heroId = heroId;
        return this;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Match withStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Match withVersion(long version) {
        this.version = version;
        return this;
    }

    public long getKills() {
        return kills;
    }

    public void setKills(long kills) {
        this.kills = kills;
    }

    public Match withKills(long kills) {
        this.kills = kills;
        return this;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public Match withDeaths(long deaths) {
        this.deaths = deaths;
        return this;
    }

    public long getAssists() {
        return assists;
    }

    public void setAssists(long assists) {
        this.assists = assists;
    }

    public Match withAssists(long assists) {
        this.assists = assists;
        return this;
    }

    public Object getSkill() {
        return skill;
    }

    public void setSkill(Object skill) {
        this.skill = skill;
    }

    public Match withSkill(Object skill) {
        this.skill = skill;
        return this;
    }

    public long getLeaverStatus() {
        return leaverStatus;
    }

    public void setLeaverStatus(long leaverStatus) {
        this.leaverStatus = leaverStatus;
    }

    public Match withLeaverStatus(long leaverStatus) {
        this.leaverStatus = leaverStatus;
        return this;
    }

    public long getPartySize() {
        return partySize;
    }

    public void setPartySize(long partySize) {
        this.partySize = partySize;
    }

    public Match withPartySize(long partySize) {
        this.partySize = partySize;
        return this;
    }

    @Override
    public int hashCode() {
        return matchId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Match match = (Match)obj;
        return getMatchId() == match.getMatchId();
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchId=" + matchId +
                '}';
    }
}
