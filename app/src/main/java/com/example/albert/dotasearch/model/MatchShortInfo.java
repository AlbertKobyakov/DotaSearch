package com.example.albert.dotasearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatchShortInfo {

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

    /*private String heroName;
    private String iconUrl;
    private String imgUrl;*/

    /*public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String icon_url) {
        this.iconUrl = icon_url;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }*/

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public MatchShortInfo withMatchId(long matchId) {
        this.matchId = matchId;
        return this;
    }

    public long getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(long playerSlot) {
        this.playerSlot = playerSlot;
    }

    public MatchShortInfo withPlayerSlot(long playerSlot) {
        this.playerSlot = playerSlot;
        return this;
    }

    public boolean isRadiantWin() {
        return radiantWin;
    }

    public void setRadiantWin(boolean radiantWin) {
        this.radiantWin = radiantWin;
    }

    public MatchShortInfo withRadiantWin(boolean radiantWin) {
        this.radiantWin = radiantWin;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public MatchShortInfo withDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public long getGameMode() {
        return gameMode;
    }

    public void setGameMode(long gameMode) {
        this.gameMode = gameMode;
    }

    public MatchShortInfo withGameMode(long gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    public long getLobbyType() {
        return lobbyType;
    }

    public void setLobbyType(long lobbyType) {
        this.lobbyType = lobbyType;
    }

    public MatchShortInfo withLobbyType(long lobbyType) {
        this.lobbyType = lobbyType;
        return this;
    }

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

    public MatchShortInfo withHeroId(long heroId) {
        this.heroId = heroId;
        return this;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public MatchShortInfo withStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public MatchShortInfo withVersion(long version) {
        this.version = version;
        return this;
    }

    public long getKills() {
        return kills;
    }

    public void setKills(long kills) {
        this.kills = kills;
    }

    public MatchShortInfo withKills(long kills) {
        this.kills = kills;
        return this;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public MatchShortInfo withDeaths(long deaths) {
        this.deaths = deaths;
        return this;
    }

    public long getAssists() {
        return assists;
    }

    public void setAssists(long assists) {
        this.assists = assists;
    }

    public MatchShortInfo withAssists(long assists) {
        this.assists = assists;
        return this;
    }

    public Object getSkill() {
        return skill;
    }

    public void setSkill(Object skill) {
        this.skill = skill;
    }

    public MatchShortInfo withSkill(Object skill) {
        this.skill = skill;
        return this;
    }

    public long getLeaverStatus() {
        return leaverStatus;
    }

    public void setLeaverStatus(long leaverStatus) {
        this.leaverStatus = leaverStatus;
    }

    public MatchShortInfo withLeaverStatus(long leaverStatus) {
        this.leaverStatus = leaverStatus;
        return this;
    }

    public long getPartySize() {
        return partySize;
    }

    public void setPartySize(long partySize) {
        this.partySize = partySize;
    }

    public MatchShortInfo withPartySize(long partySize) {
        this.partySize = partySize;
        return this;
    }

    @Override
    public int hashCode() {
        return matchId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        MatchShortInfo matchShortInfo = (MatchShortInfo)obj;
        return getMatchId() == matchShortInfo.getMatchId();
    }

    @Override
    public String toString() {
        return "MatchShortInfo{" +
                "matchId=" + matchId +
                '}';
    }
}
