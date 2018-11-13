package com.kobyakov.d2s.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerHero {
    @SerializedName("hero_id")
    @Expose
    private String heroId;
    @SerializedName("last_played")
    @Expose
    private long lastPlayed;
    @SerializedName("games")
    @Expose
    private long games;
    @SerializedName("win")
    @Expose
    private long win;
    @SerializedName("with_games")
    @Expose
    private long withGames;
    @SerializedName("with_win")
    @Expose
    private long withWin;
    @SerializedName("against_games")
    @Expose
    private long againstGames;
    @SerializedName("against_win")
    @Expose
    private long againstWin;
    private String heroName;
    private String heroImg;

    public String getHeroImg() {
        return heroImg;
    }

    public void setHeroImg(String heroImg) {
        this.heroImg = heroImg;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroId() {
        return heroId;
    }

    public void setHeroId(String heroId) {
        this.heroId = heroId;
    }

    public long getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(long lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public long getGames() {
        return games;
    }

    public void setGames(long games) {
        this.games = games;
    }

    public long getWin() {
        return win;
    }

    public void setWin(long win) {
        this.win = win;
    }

    public long getWithGames() {
        return withGames;
    }

    public void setWithGames(long withGames) {
        this.withGames = withGames;
    }

    public long getWithWin() {
        return withWin;
    }

    public void setWithWin(long withWin) {
        this.withWin = withWin;
    }

    public long getAgainstGames() {
        return againstGames;
    }

    public void setAgainstGames(long againstGames) {
        this.againstGames = againstGames;
    }

    public long getAgainstWin() {
        return againstWin;
    }

    public void setAgainstWin(long againstWin) {
        this.againstWin = againstWin;
    }

    @Override
    public String toString() {
        return "PlayerHero{" +
                "heroId='" + heroId + '\'' +
                ", lastPlayed=" + lastPlayed +
                ", games=" + games +
                ", win=" + win +
                ", withGames=" + withGames +
                ", withWin=" + withWin +
                ", againstGames=" + againstGames +
                ", againstWin=" + againstWin +
                '}';
    }
}
