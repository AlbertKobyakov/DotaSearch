package com.example.albert.dotasearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Objective {
    @SerializedName("time")
    @Expose
    private long time;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("slot")
    @Expose
    private long slot;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("player_slot")
    @Expose
    private long playerSlot;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("team")
    @Expose
    private long team;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSlot() {
        return slot;
    }

    public void setSlot(long slot) {
        this.slot = slot;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(long playerSlot) {
        this.playerSlot = playerSlot;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getTeam() {
        return team;
    }

    public void setTeam(long team) {
        this.team = team;
    }
}
