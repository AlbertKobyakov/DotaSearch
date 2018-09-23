package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.Table;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.ItemsInfoWithSteam;
import com.example.albert.dotasearch.model.UpdateInfoDB;
import com.example.albert.dotasearch.util.UtilDota;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class StartActivityRepository {
    private static final String TAG = "StartActivityRepository";
    private static final String KEY = "C35559BD1BEA3B0DC2F958ADF8B7E484";
    private static final int HERO_TABLE_ID_FOR_UPDATE = Table.HERO.ordinal();
    private static final int ITEM_TABLE_ID_FOR_UPDATE = Table.ITEM.ordinal();
    private static final String HERO_TABLE_NAME_FOR_UPDATE = Table.HERO.name();
    private static final String ITEM_TABLE_NAME_FOR_UPDATE = Table.ITEM.name();
    private static final long MILLIS_IN_WEEK = 604800000;

    private AppDatabase db;
    private MutableLiveData<Boolean> isDataSuccess;

    public StartActivityRepository() {
        db = App.get().getDB();
        isDataSuccess = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getIsDataSuccess() {
        return isDataSuccess;
    }

    public void dataInitialization() {
        Disposable disposable = hasInternetConnection().map(
                isConnection -> {
                    if (isConnection) {
                        checkDataInDB();
                        return true;
                    } else {
                        isDataSuccess.postValue(false);
                        return null;
                    }
                }
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        isComplete -> Log.d(TAG, isComplete + ""),
                        Throwable::printStackTrace
                );
    }

    private void checkDataInDB() {
        Disposable disposable = db.updateInfoDBDao().getInfoUpdateByIdRx(HERO_TABLE_ID_FOR_UPDATE)
                .defaultIfEmpty(new UpdateInfoDB())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        updateInfoDB -> {
                            if (updateInfoDB.getCurrentTimeMillis() == 0) {
                                //first init if == 0
                                getHeroesAndItemsWithRetrofitAndStoreToDB(true);
                            } else if (updateInfoDB.getCurrentTimeMillis() < System.currentTimeMillis()) {
                                getHeroesAndItemsWithRetrofitAndStoreToDB(false);
                                isDataSuccess.setValue(true);
                                Log.d(TAG, updateInfoDB.toString() + " update");
                            } else {
                                isDataSuccess.setValue(true);
                                Log.d(TAG, "update not need " + updateInfoDB.toString());
                            }
                        },
                        error -> {
                            Log.e(TAG, error.getLocalizedMessage());
                            error.printStackTrace();
                        }
                );
    }

    private void getHeroesAndItemsWithRetrofitAndStoreToDB(boolean isLoadMainActivity) {
        Single<List<Hero>> heroApi = UtilDota.initRetrofitRx()
                .getAllHeroesRx();

        Single<ItemsInfoWithSteam> itemsSteamApi = UtilDota.initRetrofitRxSteame()
                .getItemInfoSteamRx(KEY);

        Disposable disposable = Single.zip(heroApi, itemsSteamApi, new BiFunction<List<Hero>, ItemsInfoWithSteam, Boolean>() {
            @Override
            public Boolean apply(List<Hero> heroes, ItemsInfoWithSteam itemsInfoWithSteam) {

                List<Item> items = itemsInfoWithSteam.getResult().getItems();

                if (heroes.size() > 0) {
                    storeHeroesInDB(heroes);
                }

                storeItemsSteamInDB(itemsInfoWithSteam);

                return heroes.size() > 0 && items.size() > 0;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onComplete -> {
                            String result = "success";
                            if (onComplete && isLoadMainActivity) {
                                isDataSuccess.setValue(onComplete);
                            }

                            Log.d(TAG, result);
                        },
                        error -> {
                            isDataSuccess.setValue(false);
                            Log.d(TAG, error.getLocalizedMessage());
                            error.printStackTrace();
                        }
                );
    }

    private void setUrlItem(List<Item> items) {
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

    private void storeItemsSteamInDB(ItemsInfoWithSteam itemsInfoWithSteam) {
        long status = itemsInfoWithSteam.getResult().getStatus();
        if (status == 200) {
            Log.d(TAG, "successes, status = " + status);

            List<Item> items = itemsInfoWithSteam.getResult().getItems();

            setMissingItems(items);
            setUrlItem(items);

            if (db.itemDao().getAll().size() == 0) {
                db.itemDao().insertAll(items);
                db.updateInfoDBDao().insert(new UpdateInfoDB(ITEM_TABLE_ID_FOR_UPDATE, ITEM_TABLE_NAME_FOR_UPDATE, (System.currentTimeMillis() + MILLIS_IN_WEEK)));
                Log.d(TAG, "Success. items insert to Db");
            } else {
                db.itemDao().updateAll(items);
                db.updateInfoDBDao().update(new UpdateInfoDB(ITEM_TABLE_ID_FOR_UPDATE, ITEM_TABLE_NAME_FOR_UPDATE, (System.currentTimeMillis() + MILLIS_IN_WEEK)));
                Log.d(TAG, "Success. items update to Db");
            }

        } else {
            Log.e(TAG, "error storeItemsSteamInDB, status = " + status);
        }
    }

    private String createUrlItem(String nameItem) {
        nameItem = nameItem.replace("item_", "");
        nameItem = "https://api.opendota.com/apps/dota2/images/items/" + nameItem + "_lg.png";
        //Log.d(TAG, "String after replace = " + nameItem);
        return nameItem;
    }

    private void setHeroFullIconAndImageUrl(List<Hero> heroes) {
        for (Hero hero : heroes) {
            hero.setImg("https://api.opendota.com" + hero.getImg());
            hero.setIcon("https://api.opendota.com" + hero.getIcon());
        }
    }

    private void storeHeroesInDB(List<Hero> heroes) {
        setHeroFullIconAndImageUrl(heroes);

        if (db.heroDao().getAll().size() == 0) {
            db.heroDao().insertAll(heroes);
            db.updateInfoDBDao().insert(new UpdateInfoDB(HERO_TABLE_ID_FOR_UPDATE, HERO_TABLE_NAME_FOR_UPDATE, (System.currentTimeMillis() + MILLIS_IN_WEEK)));
            Log.d(TAG, "Success. heroes insert to Db " + Thread.currentThread().getName() + " " + heroes.size());
        } else {
            db.heroDao().updateAll(heroes);
            db.updateInfoDBDao().update(new UpdateInfoDB(HERO_TABLE_ID_FOR_UPDATE, HERO_TABLE_NAME_FOR_UPDATE, (System.currentTimeMillis() + MILLIS_IN_WEEK)));
            Log.d(TAG, "Success. heroes update to Db " + Thread.currentThread().getName() + " " + heroes.size());
        }
    }

    private Single<Boolean> hasInternetConnection() {
        return Single.fromCallable(() -> {
            try {
                // Connect to Google DNS to checkValidateTeamsData for connection
                int timeoutMs = 1500;
                Socket socket = new Socket();
                InetSocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);

                socket.connect(socketAddress, timeoutMs);
                socket.close();

                return true;
            } catch (IOException io) {
                return false;
            }
        });
    }
}