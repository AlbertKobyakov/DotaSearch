package com.kobyakov.d2s.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class MatchShortInfo {
    @PrimaryKey
    @SerializedName("match_id")
    @Expose
    private long matchId;
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
    private long skill;
    @SerializedName("leaver_status")
    @Expose
    private long leaverStatus;
    @SerializedName("party_size")
    @Expose
    private long partySize;

    private String heroImageUrl;
    private String heroName;

    private long playerId;

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroImageUrl() {
        return heroImageUrl;
    }

    public void setHeroImageUrl(String heroImageUrl) {
        this.heroImageUrl = heroImageUrl;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(long playerSlot) {
        this.playerSlot = playerSlot;
    }

    public boolean isRadiantWin() {
        return radiantWin;
    }

    public void setRadiantWin(boolean radiantWin) {
        this.radiantWin = radiantWin;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getGameMode() {
        return gameMode;
    }

    public void setGameMode(long gameMode) {
        this.gameMode = gameMode;
    }

    public long getLobbyType() {
        return lobbyType;
    }

    public void setLobbyType(long lobbyType) {
        this.lobbyType = lobbyType;
    }

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getKills() {
        return kills;
    }

    public void setKills(long kills) {
        this.kills = kills;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public long getAssists() {
        return assists;
    }

    public void setAssists(long assists) {
        this.assists = assists;
    }

    public long getSkill() {
        return skill;
    }

    public void setSkill(long skill) {
        this.skill = skill;
    }

    public long getLeaverStatus() {
        return leaverStatus;
    }

    public void setLeaverStatus(long leaverStatus) {
        this.leaverStatus = leaverStatus;
    }

    public long getPartySize() {
        return partySize;
    }

    public void setPartySize(long partySize) {
        this.partySize = partySize;
    }

    @Override
    public String toString() {
        return "MatchShortInfo{" +
                "matchId=" + matchId +
                ", heroId=" + heroId +
                ", playerId=" + playerId +
                '}';
    }
}
