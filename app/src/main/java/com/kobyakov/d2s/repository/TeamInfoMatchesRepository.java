package com.kobyakov.d2s.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kobyakov.d2s.model.TeamMatch;
import com.kobyakov.d2s.util.UtilDota;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class TeamInfoMatchesRepository {
    private static final String TAG = "TeamInfoMatchesRep";
    private MutableLiveData<List<TeamMatch>> matches;
    private MutableLiveData<Integer> statusCode;
    private long teamId;

    public TeamInfoMatchesRepository(long teamId) {
        this.teamId = teamId;
        statusCode = new MutableLiveData<>();
        matches = new MutableLiveData<>();
        sendRequest();
    }

    public LiveData<List<TeamMatch>> getMatches() {
        if (matches == null) {
            matches = new MutableLiveData<>();
        }
        return matches;
    }

    public void sendRequest() {
        Log.d(TAG, "NETWORK REQUEST");

        Single<Response<List<TeamMatch>>> listTeamMatches = UtilDota.initRetrofitRx()
                .getMatchesTeamResponse(teamId);

        Disposable disposable = listTeamMatches.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            matches.setValue(response.body());
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