package com.example.albert.dotasearch.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class FavoritePlayer {
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

    public FavoritePlayer(long accountId, String avatarfull, String personaname) {
        this.accountId = accountId;
        this.avatarfull = avatarfull;
        this.personaname = personaname;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getAvatarfull() {
        return avatarfull;
    }

    public String getPersonaname() {
        return personaname;
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

    @Override
    public String toString() {
        return "FavoritePlayer{" +
                "accountId=" + accountId +
                ", avatarfull='" + avatarfull + '\'' +
                ", personaname='" + personaname + '\'' +
                '}';
    }
}
