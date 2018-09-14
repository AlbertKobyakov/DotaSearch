package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.FoundPlayer;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchRepository {
    private AppDatabase db;
    private LiveData<List<FoundPlayer>> foundPlayers;
    private MutableLiveData<Boolean> isInternet;
    private static final String TAG = "SearchRepository";

    public SearchRepository() {
        db = App.get().getDB();
        foundPlayers = db.foundPlayerDao().getAllLiveData();
        this.isInternet = new MutableLiveData<>();
    }

    public LiveData<List<FoundPlayer>> getFoundPlayers() {
        return foundPlayers;
    }

    public void searchResult(final String query) {
        Log.d(TAG, "NETWORK");
        Disposable dis = UtilDota.initRetrofitRx().getFoundPlayersRx(query)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        response -> {
                            db.foundPlayerDao().deleteAllFoundPlayer();
                            db.foundPlayerDao().insertAll(response);
                        },
                        err -> Log.e(TAG, err.getLocalizedMessage())
                );
    }

    public MutableLiveData<Boolean> getIsInternet() {
        return isInternet;
    }

    /*public void hasInternetConnection() {
        Disposable disposable = Single.fromCallable(() -> {
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
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        connection -> isInternet.postValue(connection),
                        Throwable::printStackTrace
                );
    }*/
}
