package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.model.FoundPlayer;
import com.example.albert.dotasearch.util.UtilDota;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPlayersRepository {
    private LiveData<List<FavoritePlayer>> allFavoritePlayer;
    private AppDatabase db;
    public static final String TAG = "SearchPlayersRepository";
    private MutableLiveData<Integer> responseStatusCode;
    private static final int NO_INTERNET_CODE = -100;
    private static final int ERROR_RX = -200;

    public SearchPlayersRepository() {
        db = App.get().getDB();
        allFavoritePlayer = db.favoritePlayerDao().getAllRx();
        responseStatusCode = new MutableLiveData<>();
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
        Disposable disposable = hasInternetConnection().flatMap(internet -> {
            if (internet) {

                Log.d(TAG, "NETWORK");
                Disposable dis = UtilDota.initRetrofitRx().getFoundPlayersRxResponse(query)
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                response -> {
                                    List<FoundPlayer> foundPlayers = response.body();
                                    db.foundPlayerDao().deleteAllFoundPlayer();
                                    db.foundPlayerDao().insertAll(foundPlayers);
                                    responseStatusCode.postValue(response.code());
                                },
                                err -> {
                                    err.printStackTrace();
                                    responseStatusCode.postValue(ERROR_RX);
                                });
            } else {
                responseStatusCode.postValue(NO_INTERNET_CODE);
            }

            return Single.just(internet);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        internet -> Log.d(TAG, "internet is " + internet),
                        Throwable::printStackTrace
                );
    }

    public MutableLiveData<Integer> getResponseStatusCode() {
        return responseStatusCode;
    }

    public Single<Boolean> hasInternetConnection() {
        return Single.fromCallable(() -> {
            try {
                // Connect to Google DNS to checkValidateTeamsData for connection
                int timeoutMs = 1500;
                Socket socket = new Socket();
                InetSocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);

                socket.connect(socketAddress, timeoutMs);
                socket.close();

                return true;
            } catch (IOException io) {
                return false;
            }
        });
    }
}