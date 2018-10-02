package com.example.albert.dotasearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PicksBan {
    @SerializedName("is_pick")
    @Expose
    private boolean isPick;
    @SerializedName("hero_id")
    @Expose
    private long heroId;
    @SerializedName("team")
    @Expose
    private long team;
    @SerializedName("order")
    @Expose
    private long order;
    @SerializedName("ord")
    @Expose
    private long ord;
    @SerializedName("match_id")
    @Expose
    private long matchId;

    public boolean isIsPick() {
        return isPick;
    }

    public void setIsPick(boolean isPick) {
        this.isPick = isPick;
    }

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

    public long getTeam() {
        return team;
    }

    public void setTeam(long team) {
        this.team = team;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public long getOrd() {
        return ord;
    }

    public void setOrd(long ord) {
        this.ord = ord;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }
}
