package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.Table;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Team;
import com.example.albert.dotasearch.model.UpdateInfoDB;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProTeamRepository {
    private AppDatabase db;
    private LiveData<List<Team>> teams;
    private static final String TAG = "ProTeamRepository";
    private static final long MILLIS_IN_DAY = 86400000;

    public ProTeamRepository() {
        db = App.get().getDB();
        teams = db.teamDao().getAllTeamLiveData();
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
                .getAllProTeam()
                .flatMap(
                        allTeam -> {
                            if (isInsert) {
                                Log.d(TAG, "insert");
                                db.teamDao().insertAll(allTeam);
                                db.updateInfoDBDao().insert(new UpdateInfoDB(Table.TEAM.ordinal(), Table.TEAM.name(), (System.currentTimeMillis() + MILLIS_IN_DAY)));
                            } else {
                                Log.d(TAG, "update");
                                db.teamDao().updateAll(allTeam);
                                db.updateInfoDBDao().update(new UpdateInfoDB(Table.TEAM.ordinal(), Table.TEAM.name(), (System.currentTimeMillis() + MILLIS_IN_DAY)));
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
