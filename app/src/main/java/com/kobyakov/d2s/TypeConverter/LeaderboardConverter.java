package com.kobyakov.d2s.TypeConverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kobyakov.d2s.model.Leaderboard;

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