package com.kobyakov.d2s.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.kobyakov.d2s.model.TeamPlayer;
import com.kobyakov.d2s.util.UtilDota;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TeamInfoPlayerRepository {

    public static final String TAG = "PlayerInfoProsRep";

    private long accountId;
    private MutableLiveData<List<TeamPlayer>> teamPlayers;
    private MutableLiveData<Integer> statusCode;

    public TeamInfoPlayerRepository(long accountId) {
        this.accountId = accountId;
        statusCode = new MutableLiveData<>();
        teamPlayers = new MutableLiveData<>();
        sendRequest();
        Log.d(TAG, "new constructor");
    }

    public LiveData<List<TeamPlayer>> getTeamPlayers() {
        return teamPlayers;
    }

    public void sendRequest() {
        Log.d(TAG, "NETWORK REQUEST");
        Disposable disposable = UtilDota.initRetrofitRx()
                .getTeamPlayerResponse(accountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            List<TeamPlayer> teamPlayers = response.body();
                            this.teamPlayers.setValue(teamPlayers);
                            if (response.code() != 200) {
                                statusCode.setValue(response.code());
                            }
                        },
                        err -> {
                            Log.e(TAG, err.getLocalizedMessage());
                            if (err.getLocalizedMessage().contains("timeout")) {
                                statusCode.setValue(-300);
                            } else {
                                statusCode.setValue(-200);
                            }
                        }
                );
    }

    public MutableLiveData<Integer> getStatusCode() {
        return statusCode;
    }
}