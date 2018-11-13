package com.kobyakov.d2s.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.kobyakov.d2s.App;
import com.kobyakov.d2s.Table;
import com.kobyakov.d2s.database.AppDatabase;
import com.kobyakov.d2s.model.ProPlayer;
import com.kobyakov.d2s.model.UpdateInfoDB;
import com.kobyakov.d2s.util.UtilDota;

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

    private MutableLiveData<Integer> statusCode;

    public ProPlayerRepository() {
        Log.d(TAG, "ProPlayerRepository");
        db = App.get().getDB();
        proPlayers = db.proPlayerDao().getAllRx();
        statusCode = new MutableLiveData<>();
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
                                    getAllProPlayersAndStoreToDB(true);
                                } else {
                                    getAllProPlayersAndStoreToDB(false);
                                }
                                Log.d(TAG, updateInfoDB.toString() + " yyyyyyyy");
                            } else {
                                Log.d(TAG, "update not need " + updateInfoDB.toString());
                            }
                        },
                        error -> Log.e(TAG, error.getLocalizedMessage())
                );
    }

    private void getAllProPlayersAndStoreToDB(boolean isInsert) {
        Log.d(TAG, "NETWORK REQUEST");
        Disposable disposable = UtilDota.initRetrofitRx()
                .getAllProPlayerResponse()
                .flatMap(
                        response -> {
                            Log.d(TAG, response.code() + " response code Pro");
                            if(response.code() == 200){
                                if (isInsert) {
                                    Log.d(TAG, "insert");
                                    db.proPlayerDao().insertAll(response.body());
                                    db.updateInfoDBDao().insert(new UpdateInfoDB(TABLE_ID_FOR_UPDATE, TABLE_NAME_FOR_UPDATE, (System.currentTimeMillis() + MILLIS_IN_WEEK)));
                                } else {
                                    Log.d(TAG, "update");
                                    db.proPlayerDao().updateAll(response.body());
                                    db.updateInfoDBDao().update(new UpdateInfoDB(TABLE_ID_FOR_UPDATE, TABLE_NAME_FOR_UPDATE, (System.currentTimeMillis() + MILLIS_IN_WEEK)));
                                }
                            } else {
                                statusCode.postValue(response.code());
                            }

                            return Single.just(response);
                        }
                )
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        proPlayers -> {
                            Log.d(TAG, "Response code for ProPLayer: " + proPlayers.code());
                            statusCode.postValue(proPlayers.code());
                        },
                        err -> {
                            Log.e(TAG, err.getLocalizedMessage());

                            if(db.proPlayerDao().getAll().size() == 0){
                                statusCode.postValue(-200);
                            }
                        }
                );
    }

    public MutableLiveData<Integer> getStatusCode() {
        return statusCode;
    }
}
