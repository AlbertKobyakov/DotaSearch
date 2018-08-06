package com.example.albert.dotasearch.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.albert.dotasearch.database.converter.HeaderPlayerInfoConverter;

@Entity
public class HeaderPlayerContainsWinLoseAndPlayerInfo {

    @NonNull
    @TypeConverters({HeaderPlayerInfoConverter.class})
    public PlayerInfo playerInfo;
    @TypeConverters({HeaderPlayerInfoConverter.class})
    public WinLose winLose;
    @PrimaryKey
    public long playerId;

    public HeaderPlayerContainsWinLoseAndPlayerInfo(PlayerInfo playerInfo, WinLose winLose, long playerId){
        this.playerInfo = playerInfo;
        this.winLose = winLose;
        this.playerId = playerId;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public WinLose getWinLose() {
        return winLose;
    }

    @Override
    public String toString() {
        return "HeaderPlayerContainsWinLoseAndPlayerInfo{" +
                "playerInfo=" + playerInfo +
                ", winLose=" + winLose +
                ", playerId=" + playerId +
                '}';
    }
}
