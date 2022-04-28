package com.kobyakov.d2s.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.kobyakov.d2s.App;
import com.kobyakov.d2s.dao.MatchFullInfoDao;
import com.kobyakov.d2s.database.AppDatabase;
import com.kobyakov.d2s.util.UtilDota;

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