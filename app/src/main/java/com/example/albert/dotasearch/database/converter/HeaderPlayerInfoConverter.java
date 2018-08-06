package com.example.albert.dotasearch.database.converter;

import android.arch.persistence.room.TypeConverter;

import com.example.albert.dotasearch.model.PlayerInfo;
import com.example.albert.dotasearch.model.WinLose;
import com.google.gson.Gson;

public class HeaderPlayerInfoConverter {
    @TypeConverter
    public static PlayerInfo stringtToPlayerInfo(String value) {
        return value == null ? null : new Gson().fromJson(value, PlayerInfo.class);
    }

    @TypeConverter
    public static String playerInfoToString(PlayerInfo value) {
        return value == null ? null : new Gson().toJson(value);
    }

    @TypeConverter
    public static WinLose stringtToWinLose(String value) {
        return value == null ? null : new Gson().fromJson(value, WinLose.class);
    }

    @TypeConverter
    public static String winLoseToString(WinLose value) {
        return value == null ? null : new Gson().toJson(value);
    }
}
