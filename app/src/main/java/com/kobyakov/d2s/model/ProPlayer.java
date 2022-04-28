package com.kobyakov.d2s.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "proplayer")
public class ProPlayer {

    @SerializedName("account_id")
    @Expose
    @PrimaryKey
    private long accountId;
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
    @Ignore
    private Object lastLogin;
    @SerializedName("full_history_time")
    @Expose
    @Ignore
    private Object fullHistoryTime;
    @SerializedName("cheese")
    @Expose
    private long cheese;
    @SerializedName("fh_unavailable")
    @Expose
    @Ignore
    private Object fhUnavailable;
    @SerializedName("loccountrycode")
    @Expose
    @Ignore
    private Object loccountrycode;
    @SerializedName("last_match_time")
    @Expose
    private String lastMatchTime;
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

    public long getAccountId() {
        return accountId;
    }

    public String getSteamid() {
        return steamid;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAvatarmedium() {
        return avatarmedium;
    }

    public String getAvatarfull() {
        return avatarfull;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public String getPersonaname() {
        return personaname;
    }

    public Object getLastLogin() {
        return lastLogin;
    }

    public Object getFullHistoryTime() {
        return fullHistoryTime;
    }

    public long getCheese() {
        return cheese;
    }

    public Object getFhUnavailable() {
        return fhUnavailable;
    }

    public Object getLoccountrycode() {
        return loccountrycode;
    }

    public String getLastMatchTime() {
        return lastMatchTime;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public long getFantasyRole() {
        return fantasyRole;
    }

    public long getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamTag() {
        return teamTag;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public boolean isPro() {
        return isPro;
    }

    public long getLockedUntil() {
        return lockedUntil;
    }

    public void setPersonaname(String personaname) {
        this.personaname = personaname;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setAvatarmedium(String avatarmedium) {
        this.avatarmedium = avatarmedium;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setSteamid(String steamid) {
        this.steamid = steamid;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAvatarfull(String avatarfull) {
        this.avatarfull = avatarfull;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public void setLastLogin(Object lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setFullHistoryTime(Object fullHistoryTime) {
        this.fullHistoryTime = fullHistoryTime;
    }

    public void setCheese(long cheese) {
        this.cheese = cheese;
    }

    public void setFhUnavailable(Object fhUnavailable) {
        this.fhUnavailable = fhUnavailable;
    }

    public void setLoccountrycode(Object loccountrycode) {
        this.loccountrycode = loccountrycode;
    }

    public void setLastMatchTime(String lastMatchTime) {
        this.lastMatchTime = lastMatchTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setFantasyRole(long fantasyRole) {
        this.fantasyRole = fantasyRole;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public void setTeamTag(String teamTag) {
        this.teamTag = teamTag;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void setPro(boolean pro) {
        isPro = pro;
    }

    public void setLockedUntil(long lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

    @Override
    public String toString() {
        return "ProPlayer{" +
                "personaname='" + personaname + '\'' +
                '}';
    }
}
