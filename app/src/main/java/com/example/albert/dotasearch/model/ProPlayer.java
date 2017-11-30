package com.example.albert.dotasearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProPlayer {

    @SerializedName("account_id")
    @Expose
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
}
