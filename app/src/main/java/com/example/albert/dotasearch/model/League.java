package com.example.albert.dotasearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class League {
    @SerializedName("leagueid")
    @Expose
    private long leagueid;
    @SerializedName("tier")
    @Expose
    private String tier;
    @SerializedName("name")
    @Expose
    private String name;

    public long getLeagueid() {
        return leagueid;
    }

    public void setLeagueid(long leagueid) {
        this.leagueid = leagueid;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}