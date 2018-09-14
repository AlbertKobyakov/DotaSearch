package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.FavoritePlayer;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FavoritePlayersRepository {
    private LiveData<List<FavoritePlayer>> allFavoritePlayer;
    private AppDatabase db;
    public static final String TAG = "FavoriteRepository";

    public FavoritePlayersRepository() {
        db = App.get().getDB();
        allFavoritePlayer = db.favoritePlayerDao().getAllRx();
    }

    public LiveData<List<FavoritePlayer>> getAllFavoritePlayer() {
        return allFavoritePlayer;
    }

    public void deletePlayerWithFavoriteList(long accountId) {
        Disposable disposable = Single.fromCallable(() -> {
            db.favoritePlayerDao().deleteById(accountId);
            return Single.just(accountId);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        id -> Log.d(TAG, "Player " + id + " deleted"),
                        Throwable::printStackTrace
                );
    }
}
