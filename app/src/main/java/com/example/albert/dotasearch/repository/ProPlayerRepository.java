package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.Table;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.model.UpdateInfoDB;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProPlayerRepository {
    private AppDatabase db;
    private LiveData<List<ProPlayer>> proPlayers;
    private static final String TAG = "ProPlayerRepository";

    private static final int TABLE_ID_FOR_UPDATE = Table.PROPLAYER.ordinal();
    private static final String TABLE_NAME_FOR_UPDATE = Table.PROPLAYER.name();
    private static final long MILLIS_IN_WEEK = 604800000;

    public ProPlayerRepository() {
        Log.d(TAG, "ProPlayerRepository");
        db = App.get().getDB();
        proPlayers = db.proPlayerDao().getAllRx();
    }

    public LiveData<List<ProPlayer>> getProPlayersWithDB() {
        return proPlayers;
    }

    public void checkValidateProPlayersData() {
        Disposable disposable = db.updateInfoDBDao().getInfoUpdateByIdRx(TABLE_ID_FOR_UPDATE)
                .defaultIfEmpty(new UpdateInfoDB())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        updateInfoDB -> {
                            if (updateInfoDB.getCurrentTimeMillis() < System.currentTimeMillis()) {
                                if (updateInfoDB.getNameTable() == null) {
                                    getTeamsWithRetrofitAndStoreToDB(true);
                                } else {
                                    getTeamsWithRetrofitAndStoreToDB(false);
                                }
                                Log.d(TAG, updateInfoDB.toString() + " yyyyyyyy");
                            } else {
                                Log.d(TAG, "update not need " + updateInfoDB.toString());
                            }
                        },
                        error -> Log.e(TAG, error.getLocalizedMessage())
                );
    }

    private void getTeamsWithRetrofitAndStoreToDB(boolean isInsert) {
        Log.d(TAG, "NETWORK REQUEST");
        Disposable disposable = UtilDota.initRetrofitRx()
                .getAllProPlayerRx()
                .flatMap(
                        allTeam -> {
                            if (isInsert) {
                                Log.d(TAG, "insert");
                                db.proPlayerDao().insertAll(allTeam);
                                db.updateInfoDBDao().insert(new UpdateInfoDB(TABLE_ID_FOR_UPDATE, TABLE_NAME_FOR_UPDATE, (System.currentTimeMillis() + MILLIS_IN_WEEK)));
                            } else {
                                Log.d(TAG, "update");
                                db.proPlayerDao().updateAll(allTeam);
                                db.updateInfoDBDao().update(new UpdateInfoDB(TABLE_ID_FOR_UPDATE, TABLE_NAME_FOR_UPDATE, (System.currentTimeMillis() + MILLIS_IN_WEEK)));
                            }
                            return Single.just(allTeam);
                        }
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        teamsResponse -> Log.d(TAG, teamsResponse.size() + " all teamsize"),
                        err -> Log.e(TAG, err.getLocalizedMessage())
                );
    }
}
