package com.example.albert.dotasearch.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.albert.dotasearch.model.Leaderboard;
import com.example.albert.dotasearch.retrofit.DotaClient;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class UtilDota {

    final String GENERAL_VALUE = "general_value";

    public DotaClient initRetrofit(String generalUrl){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(generalUrl)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        DotaClient client = retrofit.create(DotaClient.class);
        return client;
    }

    public void saveValue(String name, int value, Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(GENERAL_VALUE, MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putInt(name, value);
        ed.apply();
        Toast.makeText(activity, "Text saved", Toast.LENGTH_SHORT).show();
    }

    public int loadValue(String name, Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(GENERAL_VALUE, MODE_PRIVATE);
        int value = sharedPreferences.getInt(name, 0);

        Toast.makeText(activity, "Load Value", Toast.LENGTH_SHORT).show();
        return value;
    }

    public static List<Leaderboard> setPositionLeaderboard(List<Leaderboard> leaderboards){
        int position = 1;

        for (Leaderboard leaderboard : leaderboards){
            leaderboard.setPosition(position++);
        }

        return leaderboards;
    }
}
