package com.kobyakov.d2s.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kobyakov.d2s.model.Pros;
import com.kobyakov.d2s.util.UtilDota;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlayerInfoProsRepository {

    public static final String TAG = "PlayerInfoProsRep";

    private long accountId;
    private MutableLiveData<List<Pros>> pros;
    private MutableLiveData<Integer> statusCode;

    public PlayerInfoProsRepository(long accountId) {
        this.accountId = accountId;
        statusCode = new MutableLiveData<>();
        pros = new MutableLiveData<>();
        sendRequest();
        Log.d(TAG, "new constructor");
    }

    public LiveData<List<Pros>> getPros() {
        return pros;
    }

    public void sendRequest() {
        Log.d(TAG, "NETWORK REQUEST");
        Disposable disposable = UtilDota.initRetrofitRx()
                .getPlayerProsResponse(accountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            List<Pros> prosList = response.body();
                            pros.setValue(prosList);
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