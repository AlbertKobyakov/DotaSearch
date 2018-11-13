package com.kobyakov.d2s.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pros {
    @SerializedName("account_id")
    @Expose
    private long accountId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("fantasy_role")
    @Expose
    private long fantasyRole;
    @SerializedName("team_id")
    @Expose
    private long teamId;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("team_tag")
    @Expose
    private String teamTag;
    @SerializedName("is_locked")
    @Expose
    private boolean isLocked;
    @SerializedName("is_pro")
    @Expose
    private boolean isPro;
    @SerializedName("locked_until")
    @Expose
    private long lockedUntil;
    @SerializedName("steamid")
    @Expose
    private String steamid;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("avatarmedium")
    @Expose
    private String avatarmedium;
    @SerializedName("avatarfull")
    @Expose
    private String avatarfull;
    @SerializedName("profileurl")
    @Expose
    private String profileurl;
    @SerializedName("personaname")
    @Expose
    private String personaname;
    @SerializedName("last_login")
    @Expose
    private Object lastLogin;
    @SerializedName("full_history_time")
    @Expose
    private Object fullHistoryTime;
    @SerializedName("cheese")
    @Expose
    private long cheese;
    @SerializedName("fh_unavailable")
    @Expose
    private Object fhUnavailable;
    @SerializedName("loccountrycode")
    @Expose
    private String loccountrycode;
    @SerializedName("last_match_time")
    @Expose
    private String lastMatchTime;
    @SerializedName("last_played")
    @Expose
    private long lastPlayed;
    @SerializedName("win")
    @Expose
    private long win;
    @SerializedName("games")
    @Expose
    private long games;
    @SerializedName("with_win")
    @Expose
    private long withWin;
    @SerializedName("with_games")
    @Expose
    private long withGames;
    @SerializedName("against_win")
    @Expose
    private long againstWin;
    @SerializedName("against_games")
    @Expose
    private long againstGames;
    @SerializedName("with_gpm_sum")
    @Expose
    private Object withGpmSum;
    @SerializedName("with_xpm_sum")
    @Expose
    private Object withXpmSum;

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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public long getFantasyRole() {
        return fantasyRole;
    }

    public void setFantasyRole(long fantasyRole) {
        this.fantasyRole = fantasyRole;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamTag() {
        return teamTag;
    }

    public void setTeamTag(String teamTag) {
        this.teamTag = teamTag;
    }

    public boolean isIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean isIsPro() {
        return isPro;
    }

    public void setIsPro(boolean isPro) {
        this.isPro = isPro;
    }

    public long getLockedUntil() {
        return lockedUntil;
    }

    public void setLockedUntil(long lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

    public String getSteamid() {
        return steamid;
    }

    public void setSteamid(String steamid) {
        this.steamid = steamid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarmedium() {
        return avatarmedium;
    }

    public void setAvatarmedium(String avatarmedium) {
        this.avatarmedium = avatarmedium;
    }

    public String getAvatarfull() {
        return avatarfull;
    }

    public void setAvatarfull(String avatarfull) {
        this.avatarfull = avatarfull;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getPersonaname() {
        return personaname;
    }

    public void setPersonaname(String personaname) {
        this.personaname = personaname;
    }

    public Object getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Object lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Object getFullHistoryTime() {
        return fullHistoryTime;
    }

    public void setFullHistoryTime(Object fullHistoryTime) {
        this.fullHistoryTime = fullHistoryTime;
    }

    public long getCheese() {
        return cheese;
    }

    public void setCheese(long cheese) {
        this.cheese = cheese;
    }

    public Object getFhUnavailable() {
        return fhUnavailable;
    }

    public void setFhUnavailable(Object fhUnavailable) {
        this.fhUnavailable = fhUnavailable;
    }

    public String getLoccountrycode() {
        return loccountrycode;
    }

    public void setLoccountrycode(String loccountrycode) {
        this.loccountrycode = loccountrycode;
    }

    public String getLastMatchTime() {
        return lastMatchTime;
    }

    public void setLastMatchTime(String lastMatchTime) {
        this.lastMatchTime = lastMatchTime;
    }

    public long getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(long lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public long getWin() {
        return win;
    }

    public void setWin(long win) {
        this.win = win;
    }

    public long getGames() {
        return games;
    }

    public void setGames(long games) {
        this.games = games;
    }

    public long getWithWin() {
        return withWin;
    }

    public void setWithWin(long withWin) {
        this.withWin = withWin;
    }

    public long getWithGames() {
        return withGames;
    }

    public void setWithGames(long withGames) {
        this.withGames = withGames;
    }

    public long getAgainstWin() {
        return againstWin;
    }

    public void setAgainstWin(long againstWin) {
        this.againstWin = againstWin;
    }

    public long getAgainstGames() {
        return againstGames;
    }

    public void setAgainstGames(long againstGames) {
        this.againstGames = againstGames;
    }

    public Object getWithGpmSum() {
        return withGpmSum;
    }

    public void setWithGpmSum(Object withGpmSum) {
        this.withGpmSum = withGpmSum;
    }

    public Object getWithXpmSum() {
        return withXpmSum;
    }

    public void setWithXpmSum(Object withXpmSum) {
        this.withXpmSum = withXpmSum;
    }
}
