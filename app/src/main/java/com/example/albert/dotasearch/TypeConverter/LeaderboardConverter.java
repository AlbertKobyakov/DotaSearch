package com.example.albert.dotasearch.TypeConverter;

import android.arch.persistence.room.TypeConverter;

import com.example.albert.dotasearch.model.Leaderboard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardConverter {
    @TypeConverter
    public String fromLeaderboards(List<Leaderboard> leaderboardList) {
        return new Gson().toJson(leaderboardList);
    }

    @TypeConverter
    public List<Leaderboard> toLeaderboards(String data) {
        Type listType = new TypeToken<ArrayList<Leaderboard>>() {}.getType();
        return new Gson().fromJson(data, listType);
    }
}