package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.model.PlayerInfo;
import com.example.albert.dotasearch.model.PlayerOverviewCombine;
import com.example.albert.dotasearch.model.WinLose;
import com.example.albert.dotasearch.util.UtilDota;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlayerInfoRepository {

    public static final String TAG = "PlayerInfoRepository";

    private long accountId;
    private AppDatabase db;
    private MutableLiveData<PlayerOverviewCombine> playerFullInfo;
    private MutableLiveData<Boolean> isFavoritePlayer;

    public PlayerInfoRepository(long accountId) {
        this.accountId = accountId;
        db = App.get().getDB();
    }

    public LiveData<PlayerOverviewCombine> getLiveDataPlayerFullInfo() {
        if (playerFullInfo == null) {
            playerFullInfo = new MutableLiveData<>();

            System.out.println("NETWORK");
            Single<PlayerInfo> playerInfo = UtilDota.initRetrofitRx()
                    .getPlayerInfoById(accountId)
                    .doOnError(Throwable::printStackTrace)
                    .subscribeOn(Schedulers.io());

            Single<WinLose> winLose = UtilDota.initRetrofitRx()
                    .getPlayerWinLoseById(accountId)
                    .doOnError(Throwable::printStackTrace)
                    .subscribeOn(Schedulers.io());

            Disposable disposable = Single.zip(
                    playerInfo,
                    winLose,
                    (playerInfo1, winLose1) -> new PlayerOverviewCombine(playerInfo1, winLose1, accountId))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            playerOverviewCombine -> playerFullInfo.setValue(playerOverviewCombine),
                            error -> System.out.println(error.getLocalizedMessage())
                    );
        }
        return playerFullInfo;
    }

    public void insertPlayerToFavoriteList(FavoritePlayer favoritePlayer) {
        Disposable disposable = Single.fromCallable(() -> {
            db.favoritePlayerDao().insertPlayer(favoritePlayer);
            return Single.just(favoritePlayer.getAccountId());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        accountId -> isFavoritePlayer.setValue(true),
                        Throwable::printStackTrace
                );

        isPlayerFavoriteById(favoritePlayer.getAccountId());
    }

    public void deletePlayerWithFavoriteList(long accountId) {
        Disposable disposable = Single.fromCallable(() -> {
            db.favoritePlayerDao().deleteById(accountId);
            return Single.just(accountId);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        id -> isFavoritePlayer.setValue(false),
                        Throwable::printStackTrace
                );

        isPlayerFavoriteById(accountId);
    }

    public LiveData<Boolean> isPlayerFavoriteById(long accountId) {
        if (isFavoritePlayer == null) {
            isFavoritePlayer = new MutableLiveData<>();
        }

        Disposable disposable = db.favoritePlayerDao().getFavoritePlayerByIdRx(accountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        favoritePlayer -> isFavoritePlayer.setValue(true),
                        err -> isFavoritePlayer.setValue(false)
                );

        return isFavoritePlayer;

    }
}