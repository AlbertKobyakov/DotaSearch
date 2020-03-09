package com.kobyakov.d2s.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kobyakov.d2s.App;
import com.kobyakov.d2s.Table;
import com.kobyakov.d2s.database.AppDatabase;
import com.kobyakov.d2s.model.Team;
import com.kobyakov.d2s.model.UpdateInfoDB;
import com.kobyakov.d2s.util.UtilDota;

import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProTeamRepository {
    private AppDatabase db;
    private LiveData<List<Team>> teams;
    private static final String TAG = "ProTeamRepository";
    private static final long MILLIS_IN_DAY = 86400000;
    private MutableLiveData<Integer> statusCode;

    public ProTeamRepository() {
        db = App.get().getDB();
        teams = db.teamDao().getAllTeamLiveData();
        statusCode = new MutableLiveData<>();
    }

    public LiveData<List<Team>> getTeams() {
        return teams;
    }

    public void checkValidateTeamsData() {
        Disposable disposable = db.updateInfoDBDao().getInfoUpdateByIdRx(Table.TEAM.ordinal())
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

    public void getTeamsWithRetrofitAndStoreToDB(boolean isInsert) {
        Log.d(TAG, "NETWORK REQUEST");
        Disposable disposable = UtilDota.initRetrofitRx()
                .getAllProTeamResponse()
                .flatMap(
                        allTeam -> {
                            if (isInsert) {
                                Log.d(TAG, "insert");
                                db.teamDao().insertAll(allTeam.body());
                                db.updateInfoDBDao().insert(new UpdateInfoDB(Table.TEAM.ordinal(), Table.TEAM.name(), (System.currentTimeMillis() + MILLIS_IN_DAY)));
                            } else {
                                Log.d(TAG, "update");
                                db.teamDao().updateAll(allTeam.body());
                                db.updateInfoDBDao().update(new UpdateInfoDB(Table.TEAM.ordinal(), Table.TEAM.name(), (System.currentTimeMillis() + MILLIS_IN_DAY)));
                            }
                            return Single.just(allTeam);
                        }
                )
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        teamsResponse -> {
                            Log.d(TAG, Objects.requireNonNull(teamsResponse.body()).size() + " all teamsize");
                            if (teamsResponse.code() != 200) {
                                statusCode.postValue(teamsResponse.code());
                            }
                        },
                        err -> {
                            Log.e(TAG, err.getLocalizedMessage());

                            if (db.teamDao().getAllTeam().size() == 0) {
                                statusCode.postValue(-200);
                            }
                        }
                );
    }

    public MutableLiveData<Integer> getStatusCode() {
        return statusCode;
    }
}
