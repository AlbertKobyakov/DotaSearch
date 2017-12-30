package com.example.albert.dotasearch.util;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Leaderboard;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.retrofit.DotaClient;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.example.albert.dotasearch.activity.StartActivity.db;

public class UtilDota {

    private final static int CACHE_SIZE_BYTES = 1024 * 1024 * 2;

    private Context context;

    public UtilDota(Context context) {
        this.context = context;
    }

    public UtilDota() {
    }

    final String GENERAL_VALUE = "general_value";

    public DotaClient initRetrofit(String generalUrl){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(generalUrl)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        DotaClient client = retrofit.create(DotaClient.class);
        return client;
    }

    public DotaClient initRetrofit(String generalUrl, Context context){
        Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE_BYTES);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(generalUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        DotaClient client = retrofit.create(DotaClient.class);
        return client;
    }

    public static DotaClient initRetrofitRx(){
        return new Retrofit.Builder()
                .baseUrl("https://api.opendota.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(DotaClient.class);
    }

    public static DotaClient initRetrofitRxDota2Ru(){
        return new Retrofit.Builder()
                .baseUrl("http://www.dota2.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(DotaClient.class);
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

    public static void storeProPlayersInDB(List<ProPlayer> proPlayers){
        if(db.proPlayerDao().getAll().size() == 0){
            db.proPlayerDao().insertAll(proPlayers);
            Log.e("insertProPlayer", "Success. proPlayers save to Db " + Thread.currentThread().getName() + " " + proPlayers.size());
        } else {
            db.proPlayerDao().updateAll(proPlayers);
            Log.e("updateProPlayer", "Success. proPlayers save to Db " + Thread.currentThread().getName() + " " + proPlayers.size());
        }
    }

    public static void storeHeroesInDB(List<Hero> heroes){
        if(db.heroDao().getAll().size() == 0){
            db.heroDao().insertAll(heroes);
            Log.e("insertHero", "Success. heroes save to Db " + Thread.currentThread().getName() + " " + heroes.size());
        } else {
            db.heroDao().updateAll(heroes);
            Log.e("updateHero", "Success. heroes save to Db " + Thread.currentThread().getName() + " " + heroes.size());
        }
    }

    public static void storeLeaderboardInDB(List<Leaderboard> leaderboards){
        if(db.leaderboardDao().getAll().size() == 0){
            db.leaderboardDao().insertAll(leaderboards);
            Log.e("insertLeaderboard", "Success. Leaderboard save to Db " + Thread.currentThread().getName() + " " + leaderboards.size());
        } else {
            db.leaderboardDao().updateAll(leaderboards);
            Log.e("updateLeaderboard", "Success. Leaderboard save to Db " + Thread.currentThread().getName() + " " + leaderboards.size());
        }
    }

    public List<ProPlayer> setProPlayerDefaultPersonalNameTeamNameAndAvatar(List<ProPlayer> proPlayers){
        for(ProPlayer proPlayer : proPlayers){
            if(proPlayer.getPersonaname() == null || proPlayer.getPersonaname().trim().length() == 0){
                proPlayer.setPersonaname(context.getString(R.string.unknown));
            }
            if(proPlayer.getTeamName() == null || proPlayer.getTeamName().trim().length() == 0){
                proPlayer.setTeamName(context.getString(R.string.unknown));
            }
            if(proPlayer.getAvatarmedium() == null){
                proPlayer.setAvatarmedium("https://steamcdn-a.akamaihd.net/steamcommunity/public" +
                        "/images/avatars/fe/fef49e7fa7e1997310d705b2a6158ff8dc1cdfeb_medium.jpg");
            }
        }
        return proPlayers;
    }
}
