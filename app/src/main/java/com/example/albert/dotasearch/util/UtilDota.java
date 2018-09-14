package com.example.albert.dotasearch.util;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.ItemsInfoWithSteam;
import com.example.albert.dotasearch.model.LobbyType;
import com.example.albert.dotasearch.retrofit.DotaClient;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UtilDota {
    private final static String TAG = "UtilDota";

    private static AppDatabase db = App.get().getDB();

    public static DotaClient initRetrofitRx() {
        return new Retrofit.Builder()
                .baseUrl("https://api.opendota.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(DotaClient.class);
    }

    public static DotaClient initRetrofitRxSteame() {
        return new Retrofit.Builder()
                .baseUrl("https://api.steampowered.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(DotaClient.class);
    }

    public static DotaClient initRetrofitRxDota2Ru() {
        return new Retrofit.Builder()
                .baseUrl("http://www.dota2.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(DotaClient.class);
    }

    private static String createUrlItem(String nameItem) {
        nameItem = nameItem.replace("item_", "");
        nameItem = "https://api.opendota.com/apps/dota2/images/items/" + nameItem + "_lg.png";
        Log.d(TAG, "String after replace = " + nameItem);
        return nameItem;
    }

    private static void setUrlItem(List<Item> items) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            String itemName = item.getName();
            items.get(i).setItemUrl(createUrlItem(itemName));
        }
    }

    private static void setMissingItems(List<Item> items) {
        items.add(new Item(0, "item_empty"));
        items.add(new Item(195, "item_recipe_diffusal_blade_2"));
        items.add(new Item(196, "item_diffusal_blade_2"));
        items.add(new Item(84, "item_flying_courier"));
    }

    public static void storeItemsSteamInDB(ItemsInfoWithSteam itemsInfoWithSteam) {
        long status = itemsInfoWithSteam.getResult().getStatus();
        if (status == 200) {
            Log.e(TAG, "successes, status = " + status);

            List<Item> items = itemsInfoWithSteam.getResult().getItems();

            setMissingItems(items);
            setUrlItem(items);

            if (db.itemDao().getAll().size() == 0) {
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

    private static void setHeroFullIconAndImageUrl(List<Hero> heroes) {
        for (Hero hero : heroes) {
            hero.setImg("https://api.opendota.com" + hero.getImg());
            hero.setIcon("https://api.opendota.com" + hero.getIcon());
        }
    }

    public static void storeHeroesInDB(List<Hero> heroes) {
        setHeroFullIconAndImageUrl(heroes);

        if (db.heroDao().getAll().size() == 0) {
            db.heroDao().insertAll(heroes);
            Log.e(TAG, "Success. heroes insert to Db " + Thread.currentThread().getName() + " " + heroes.size());
        } else {
            db.heroDao().updateAll(heroes);
            Log.e(TAG, "Success. heroes update to Db " + Thread.currentThread().getName() + " " + heroes.size());
        }
    }

    public static String getGameModeById(long id) {
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

        return gameModes.get((int) id);
    }

    public static String getLobbyTypeById(long id) {
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

        return lobbyTypes.get((int) id);
    }

    public static List<LobbyType> getAllLobbyTypes() {
        List<LobbyType> lobbyTypes = new ArrayList<>();

        lobbyTypes.add(new LobbyType(-1, "Invalid"));
        lobbyTypes.add(new LobbyType(0, "Normal"));
        lobbyTypes.add(new LobbyType(1, "Practise"));
        lobbyTypes.add(new LobbyType(2, "Tournament"));
        lobbyTypes.add(new LobbyType(3, "Tutorial"));
        lobbyTypes.add(new LobbyType(4, "Co-op with bots"));
        lobbyTypes.add(new LobbyType(5, "Team match"));
        lobbyTypes.add(new LobbyType(6, "Solo Queue"));
        lobbyTypes.add(new LobbyType(7, "Ranked"));
        lobbyTypes.add(new LobbyType(8, "1v1 Mid"));
        lobbyTypes.add(new LobbyType(9, "Captains Mode"));

        Log.d(TAG, "getAllLobbyTypes to be call");

        return lobbyTypes;
    }

    public static String getRankTier(Integer id) {

        SparseArray<String> rankTiers = new SparseArray<>();

        rankTiers.append(10, "Herald[0]");
        rankTiers.append(11, "Herald[1]");
        rankTiers.append(12, "Herald[2]");
        rankTiers.append(13, "Herald[3]");
        rankTiers.append(14, "Herald[4]");
        rankTiers.append(15, "Herald[5]");
        rankTiers.append(16, "Herald[6]");
        rankTiers.append(20, "Guardian[0]");
        rankTiers.append(21, "Guardian[1]");
        rankTiers.append(22, "Guardian[2]");
        rankTiers.append(23, "Guardian[3]");
        rankTiers.append(24, "Guardian[4]");
        rankTiers.append(25, "Guardian[5]");
        rankTiers.append(26, "Guardian[6]");
        rankTiers.append(30, "Crusader[0]");
        rankTiers.append(31, "Crusader[1]");
        rankTiers.append(32, "Crusader[2]");
        rankTiers.append(33, "Crusader[3]");
        rankTiers.append(34, "Crusader[4]");
        rankTiers.append(35, "Crusader[5]");
        rankTiers.append(36, "Crusader[6]");
        rankTiers.append(40, "Archon[0]");
        rankTiers.append(41, "Archon[1]");
        rankTiers.append(42, "Archon[2]");
        rankTiers.append(43, "Archon[3]");
        rankTiers.append(44, "Archon[4]");
        rankTiers.append(45, "Archon[5]");
        rankTiers.append(46, "Archon[6]");
        rankTiers.append(50, "Legend[0]");
        rankTiers.append(51, "Legend[1]");
        rankTiers.append(52, "Legend[2]");
        rankTiers.append(53, "Legend[3]");
        rankTiers.append(54, "Legend[4]");
        rankTiers.append(55, "Legend[5]");
        rankTiers.append(56, "Legend[6]");
        rankTiers.append(61, "Ancient[1]");
        rankTiers.append(62, "Ancient[2]");
        rankTiers.append(63, "Ancient[3]");
        rankTiers.append(64, "Ancient[4]");
        rankTiers.append(65, "Ancient[5]");
        rankTiers.append(66, "Ancient[6]");
        rankTiers.append(70, "Divine[0]");
        rankTiers.append(71, "Divine[1]");
        rankTiers.append(72, "Divine[2]");
        rankTiers.append(73, "Divine[3]");
        rankTiers.append(74, "Divine[4]");
        rankTiers.append(75, "Divine[5]");
        rankTiers.append(76, "Divine[6]");
        rankTiers.append(80, "Immortal[0]");

        return rankTiers.get(id);
    }

    public static String formatLongNumberToFloatWitK(Long number) {
        String result;
        if (number != 0) {
            float numberToFloat = (float) number / 1000;
            result = new DecimalFormat("###.#").format(numberToFloat) + "k";
        } else {
            result = "-";
        }
        return result;
    }

    public static void setImageView(String url, int defaultImage, ImageView imageView, Context context) {
        RequestOptions centerCrop = new RequestOptions()
                .centerCrop();

        Glide.with(context)
                .load(url)
                .error(Glide.with(context).load(defaultImage))
                .apply(centerCrop)
                .into(imageView);
    }
}