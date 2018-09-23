package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPlayersRepository {
    private LiveData<List<FavoritePlayer>> allFavoritePlayer;
    private AppDatabase db;
    public static final String TAG = "FavoriteRepository";
    private MutableLiveData<Boolean> isRequestSearchSuccessful;

    public SearchPlayersRepository() {
        db = App.get().getDB();
        allFavoritePlayer = db.favoritePlayerDao().getAllRx();
        isRequestSearchSuccessful = new MutableLiveData<>();
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

    public void searchResult(final String query) {
        Log.d(TAG, "NETWORK");
        Disposable dis = UtilDota.initRetrofitRx().getFoundPlayersRx(query)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            db.foundPlayerDao().deleteAllFoundPlayer();
                            db.foundPlayerDao().insertAll(response);
                            isRequestSearchSuccessful.postValue(true);
                        },
                        err -> Log.e(TAG, err.getLocalizedMessage())
                );
    }

    public MutableLiveData<Boolean> getIsRequestSearchSuccessful() {
        return isRequestSearchSuccessful;
    }
}