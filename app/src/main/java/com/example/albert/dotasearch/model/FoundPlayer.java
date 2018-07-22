package com.example.albert.dotasearch.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class FoundPlayer {
    @SerializedName("account_id")
    @Expose
    @PrimaryKey
    private long accountId;
    @SerializedName("avatarfull")
    @Expose
    private String avatarfull;
    @SerializedName("personaname")
    @Expose
    private String personaname;
    @SerializedName("last_match_time")
    @Expose
    private String lastMatchTime;
    @SerializedName("similarity")
    @Expose
    private double similarity;

    public long getAccountId() {
        return accountId;
    }

    public String getAvatarfull() {
        return avatarfull;
    }

    public String getPersonaname() {
        return personaname;
    }

    public String getLastMatchTime() {
        return lastMatchTime;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setAvatarfull(String avatarfull) {
        this.avatarfull = avatarfull;
    }

    public void setPersonaname(String personaname) {
        this.personaname = personaname;
    }

    public void setLastMatchTime(String lastMatchTime) {
        this.lastMatchTime = lastMatchTime;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }
}
