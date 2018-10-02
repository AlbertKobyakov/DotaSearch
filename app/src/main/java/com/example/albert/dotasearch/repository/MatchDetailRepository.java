package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.dao.MatchFullInfoDao;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.util.UtilDota;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MatchDetailRepository {
    private long matchId;
    MatchFullInfoDao matchFullInfoDao;
    private AppDatabase db;
    private MutableLiveData<Integer> statusCodeLive;

    private static final String TAG = "MatchDetailRepository";

    public MatchDetailRepository(long matchId) {
        this.matchId = matchId;
        db = App.get().getDB();
        matchFullInfoDao = db.matchFullInfoDao();
        statusCodeLive = new MutableLiveData<>();
        sendRequest();
    }

    public MutableLiveData<Integer> getStatusCode() {
        return statusCodeLive;
    }

    public void sendRequest() {
        Log.d(TAG, "NETWORK Request");

        Disposable disposable = UtilDota.initRetrofitRx()
                .getMatchFullInfoRxResponse(matchId)
                .map(matchFullInfoResponse -> {
                    if (matchFullInfoResponse.code() == 200) {
                        matchFullInfoDao.insert(matchFullInfoResponse.body());
                    }
                    return matchFullInfoResponse.code();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        statusCode -> {
                            statusCodeLive.postValue(statusCode);
                        }, err -> {
                            Log.d(TAG, err.getLocalizedMessage());
                            statusCodeLive.postValue(-200);
                        }
                );
    }
}