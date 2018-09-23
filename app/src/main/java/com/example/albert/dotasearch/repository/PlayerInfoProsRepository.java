package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.albert.dotasearch.model.Pros;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlayerInfoProsRepository {

    public static final String TAG = "PlayerInfoProsRep";

    private long accountId;
    private MutableLiveData<List<Pros>> pros;
    private MutableLiveData<String> errorMessage;

    public PlayerInfoProsRepository(long accountId) {
        this.accountId = accountId;
        errorMessage = new MutableLiveData<>();
        Log.d(TAG, "new constructor");
    }

    public LiveData<List<Pros>> getPros() {
        Log.d(TAG, "NETWORK REQUEST");
        if (pros == null) {
            pros = new MutableLiveData<>();
        }

        Disposable disposable = UtilDota.initRetrofitRx()
                .getPlayerPros(accountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        prosResponse -> pros.setValue(prosResponse),
                        //prosResponse -> errorMessage.setValue("timeout"),
                        err -> {
                            Log.e(TAG, err.getLocalizedMessage());
                            errorMessage.setValue(err.getLocalizedMessage());
                        }
                );

        return pros;
    }

    public void repeatedRequest() {
        Disposable disposable = UtilDota.initRetrofitRx()
                .getPlayerPros(accountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        prosResponse -> pros.setValue(prosResponse),
                        err -> {
                            Log.e(TAG, err.getLocalizedMessage());
                            errorMessage.setValue(err.getLocalizedMessage());
                        }
                );
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}