package com.example.albert.dotasearch.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.ItemsInfoWithSteam;
import com.example.albert.dotasearch.model.Leaderboard;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.retrofit.DotaClient;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.example.albert.dotasearch.activity.StartActivity.db;

public class UtilDota {

    private final static int CACHE_SIZE_BYTES = 1024 * 1024 * 2;
    private final static String TAG = "UtilDota";

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

    public static DotaClient initRetrofitRxSteame(){
        return new Retrofit.Builder()
                .baseUrl("https://api.steampowered.com")
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
            Log.e(TAG, "Success. proPlayers insert to Db " + Thread.currentThread().getName() + " " + proPlayers.size());
        } else {
            db.proPlayerDao().updateAll(proPlayers);
            Log.e(TAG, "Success. proPlayers update to Db " + Thread.currentThread().getName() + " " + proPlayers.size());
        }
    }


    public static String createUrlItem(String nameItem){
        nameItem = nameItem.replace("item_", "");
        nameItem = "https://api.opendota.com/apps/dota2/images/items/" + nameItem + "_lg.png";
        Log.d(TAG, "String after replace = " + nameItem);
        return nameItem;
    }

    public static void setUrlItem(List<Item> items){
        for (int i = 0; i < items.size(); i++){
            Item item = items.get(i);
            String itemName = item.getName();
            items.get(i).setItemUrl(createUrlItem(itemName));
        }
    }

    public static void storeItemsSteamInDB(ItemsInfoWithSteam itemsInfoWithSteam){
        long status = itemsInfoWithSteam.getResult().getStatus();
        if(status == 200){
            Log.e(TAG, "successes, status = " + status);

            List<Item> items = itemsInfoWithSteam.getResult().getItems();

            //add item with id = 0
            items.add(new Item(0, "empty", "item_empty"));

            setUrlItem(items);

            if(db.itemDao().getAll().size() == 0){
                db.itemDao().insertAll(items);
                Log.e(TAG, "Success. items insert to Db");
            } else {
                db.itemDao().updateAll(items);
                Log.e(TAG, "Success. items update to Db");
            }

        } else {
            Log.e(TAG, "error storeItemsSteamInDB, status = " + status);
        }
    }

    public static void storeHeroesInDB(List<Hero> heroes){
        if(db.heroDao().getAll().size() == 0){
            db.heroDao().insertAll(heroes);
            Log.e(TAG, "Success. heroes insert to Db " + Thread.currentThread().getName() + " " + heroes.size());
        } else {
            db.heroDao().updateAll(heroes);
            Log.e(TAG, "Success. heroes update to Db " + Thread.currentThread().getName() + " " + heroes.size());
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

    public static String getGameModeById(int id){
        SparseArray<String> gameModes = new SparseArray<>();

        gameModes.append(0, "None");
        gameModes.append(1, "All Pick");
        gameModes.append(2, "Captain's Mode");
        gameModes.append(3, "Random Draft");
        gameModes.append(4, "Single Draft");
        gameModes.append(5, "All Random");
        gameModes.append(6, "Intro");
        gameModes.append(7, "Diretide");
        gameModes.append(8, "Reverse Captain's Mode");
        gameModes.append(9, "The Greeviling");
        gameModes.append(10, "Tutorial");
        gameModes.append(11, "Mid Only");
        gameModes.append(12, "Least Played");
        gameModes.append(13, "New Player Pool");
        gameModes.append(14, "Compendium Matchmaking");
        gameModes.append(15, "Co-op vs Bots");
        gameModes.append(16, "Captains Draft");
        gameModes.append(18, "Ability Draft");
        gameModes.append(20, "All Random Deathmatch");
        gameModes.append(21, "1v1 Mid Only");
        gameModes.append(22, "All Pick");

        return gameModes.get(id);
    }

    public static String getLobbyTypeById(int id){
        SparseArray<String> lobbyTypes = new SparseArray<>();

        lobbyTypes.append(-1, "Invalid");
        lobbyTypes.append(0, "Normal");
        lobbyTypes.append(1, "Practise");
        lobbyTypes.append(2, "Tournament");
        lobbyTypes.append(3, "Tutorial");
        lobbyTypes.append(4, "Co-op with bots");
        lobbyTypes.append(5, "Team match");
        lobbyTypes.append(6, "Solo Queue");
        lobbyTypes.append(7, "Ranked");
        lobbyTypes.append(8, "1v1 Mid");

        return lobbyTypes.get(id);
    }
}
