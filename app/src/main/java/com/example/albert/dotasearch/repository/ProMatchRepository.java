package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.ProMatch;
import com.example.albert.dotasearch.model.Team;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProMatchRepository {
    private AppDatabase db;
    private MutableLiveData<List<ProMatch>> proMatches;
    private static final String TAG = "ProMatchRepository";

    public ProMatchRepository() {
        db = App.get().getDB();
    }

    public LiveData<List<ProMatch>> getProMatches() {
        Log.d(TAG, "NETWORK REQUEST");
        if(proMatches == null){
            proMatches = new MutableLiveData<>();
        }

        Disposable disposable = UtilDota.initRetrofitRx()
                .getAllProMatch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        teamsResponse -> proMatches.setValue(teamsResponse),
                        err -> Log.e(TAG, err.getLocalizedMessage())
                );

        return proMatches;
    }
}
