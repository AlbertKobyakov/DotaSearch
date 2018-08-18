package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.FoundPlayer;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchRepository {
    private AppDatabase db;
    private LiveData<List<FoundPlayer>> foundPlayers;
    private static final String TAG = "SearchRepository";

    public SearchRepository() {
        db = App.get().getDB();
        foundPlayers = db.foundPlayerDao().getAllLiveData();
    }

    public LiveData<List<FoundPlayer>> getFoundPlayers() {
        return foundPlayers;
    }

    public void searchResult(final String query){
        Disposable dis = UtilDota.initRetrofitRx().getFoundPlayersRx(query)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            db.foundPlayerDao().deleteAllFoundPlayer();
                            db.foundPlayerDao().insertAll(response);
                        },
                        err -> Log.e(TAG, err.getLocalizedMessage())
                );
    }
}
