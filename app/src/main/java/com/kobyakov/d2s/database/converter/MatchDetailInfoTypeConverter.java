package com.kobyakov.d2s.database.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kobyakov.d2s.model.League;
import com.kobyakov.d2s.model.MatchFullInfo;
import com.kobyakov.d2s.model.Objective;
import com.kobyakov.d2s.model.PicksBan;
import com.kobyakov.d2s.model.Player;

import java.lang.reflect.Type;
import java.util.List;

public class MatchDetailInfoTypeConverter {
    @TypeConverter
    public List<PicksBan> stringtToPicksBan(String value) {
        Type type = new TypeToken<List<PicksBan>>() {
        }.getType();
        return value == null ? null : new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String picksBanToString(List<PicksBan> value) {
        Type type = new TypeToken<List<PicksBan>>() {
        }.getType();
        return value == null ? null : new Gson().toJson(value, type);
    }

    @TypeConverter
    public static List<Objective> stringtToObjective(String value) {
        Type type = new TypeToken<List<Objective>>() {
        }.getType();
        return value == null ? null : new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String objectiveToString(List<Objective> value) {
        Type type = new TypeToken<List<Objective>>() {
        }.getType();
        return value == null ? null : new Gson().toJson(value, type);
    }

    @TypeConverter
    public List<Player> stringtToPlayer(String value) {
        Type type = new TypeToken<List<Player>>() {
        }.getType();
        return value == null ? null : new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String playerBanToString(List<Player> value) {
        Type type = new TypeToken<List<Player>>() {
        }.getType();
        return value == null ? null : new Gson().toJson(value, type);
    }

    @TypeConverter
    public List<Long> stringToLong(String value) {
        Type type = new TypeToken<List<Long>>() {
        }.getType();
        return value == null ? null : new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String longToString(List<Long> value) {
        Type type = new TypeToken<List<Long>>() {
        }.getType();
        return value == null ? null : new Gson().toJson(value, type);
    }

    @TypeConverter
    public MatchFullInfo.DireTeam stringToDireTeam(String value) {
        Type type = new TypeToken<MatchFullInfo.DireTeam>() {
        }.getType();
        return value == null ? null : new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String direTeamToString(MatchFullInfo.DireTeam value) {
        Type type = new TypeToken<MatchFullInfo.DireTeam>() {
        }.getType();
        return value == null ? null : new Gson().toJson(value, type);
    }

    @TypeConverter
    public MatchFullInfo.RadiantTeam stringToRadiantTeam(String value) {
        Type type = new TypeToken<MatchFullInfo.RadiantTeam>() {
        }.getType();
        return value == null ? null : new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String radiantTeamToString(MatchFullInfo.RadiantTeam value) {
        Type type = new TypeToken<MatchFullInfo.RadiantTeam>() {
        }.getType();
        return value == null ? null : new Gson().toJson(value, type);
    }

    @TypeConverter
    public League stringToLeague(String value) {
        Type type = new TypeToken<League>() {
        }.getType();
        return value == null ? null : new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String leagueTeamToString(League value) {
        Type type = new TypeToken<League>() {
        }.getType();
        return value == null ? null : new Gson().toJson(value, type);
    }
}