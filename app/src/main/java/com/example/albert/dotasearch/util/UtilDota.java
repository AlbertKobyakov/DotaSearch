package com.example.albert.dotasearch.util;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Leaderboard;
import com.example.albert.dotasearch.retrofit.DotaClient;

import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

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
}
