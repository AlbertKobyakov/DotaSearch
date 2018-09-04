package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Team;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProTeamRepository {
    private AppDatabase db;
    private MutableLiveData<List<Team>> teams;
    private static final String TAG = "ProTeamRepository";

    public ProTeamRepository() {
        db = App.get().getDB();
    }

    public LiveData<List<Team>> getProTeam() {
        Log.d(TAG, "NETWORK REQUEST");
        if(teams == null){
            teams = new MutableLiveData<>();
        }

        Disposable disposable = UtilDota.initRetrofitRx()
                .getAllProTeam()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        teamsResponse -> teams.setValue(teamsResponse),
                        err -> Log.e(TAG, err.getLocalizedMessage())
                );

        return teams;
    }
}
