package com.kobyakov.d2s.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kobyakov.d2s.App;
import com.kobyakov.d2s.database.AppDatabase;
import com.kobyakov.d2s.model.TimeRefreshLeaderBoard;
import com.kobyakov.d2s.util.UtilDota;

import java.util.Objects;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LeaderboardRepository {
    private AppDatabase db;
    private static final String TAG = "LeaderboardRepository";
    private LiveData<TimeRefreshLeaderBoard> timeRefreshLeaderBoardLiveData;
    private MutableLiveData<Integer> statusCode;

    public LeaderboardRepository(String division) {
        db = App.get().getDB();
        timeRefreshLeaderBoardLiveData = db.leaderBoardDivisionDao().getConcreteDivisionLive(division);
        Log.d(TAG, "LeaderboardRepository create");
        statusCode = new MutableLiveData<>();
    }

    public LiveData<TimeRefreshLeaderBoard> getTimeRefreshLeaderBoardLiveData() {
        return timeRefreshLeaderBoardLiveData;
    }

    public MutableLiveData<Integer> getStatusCode() {
        return statusCode;
    }

    private void leaderboardDataAcquisitionAndRecordingToDB(String division) {
        Log.d(TAG, "NETWORK REQUEST");
        Disposable d1 = UtilDota.initRetrofitRxDota2Ru().getLeaderBorderRx(division, 0)
                .flatMap(timeRefreshLeaderBoard -> {
                    Objects.requireNonNull(timeRefreshLeaderBoard.body()).setDivision(division);
                    timeRefreshLeaderBoard.body().setLeaderboard(timeRefreshLeaderBoard.body().getLeaderboard().subList(0, 100));

                    if (db.leaderBoardDivisionDao().getConcreteDivision(division) != null) {
                        db.leaderBoardDivisionDao().updateAll(timeRefreshLeaderBoard.body());

                        Log.d(TAG, "updateAll " + timeRefreshLeaderBoard.body().getDivision());
                    } else {
                        db.leaderBoardDivisionDao().insertAll(timeRefreshLeaderBoard.body());
                        Log.d(TAG, "insertAll " + timeRefreshLeaderBoard.body().getDivision());
                    }
                    return Single.just(timeRefreshLeaderBoard.code());
                })
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            statusCode.postValue(result);
                        },
                        err -> {
                            Log.e(TAG, err.getLocalizedMessage() + 999999999);

                            if(db.leaderBoardDivisionDao().getConcreteDivision(division) == null){
                                statusCode.postValue(-200);
                            }
                        }
                );
    }

    public void checkValidLeaderBoardData(String division) {
        Disposable d3 = db.leaderBoardDivisionDao().getConcreteDivisionRx(division)
                .defaultIfEmpty(new TimeRefreshLeaderBoard())
                .flatMap(timeRefreshLeaderBoard -> {
                    String result;
                    if (timeRefreshLeaderBoard != null) {
                        if ((timeRefreshLeaderBoard.getNextScheduledPostTime() * 1000) < System.currentTimeMillis()) {
                            leaderboardDataAcquisitionAndRecordingToDB(division);
                            result = "with Internet";
                        } else {
                            result = "with DB";
                        }
                    } else {
                        leaderboardDataAcquisitionAndRecordingToDB(division);
                        result = "with Internet";
                    }
                    return Maybe.just(result);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> Log.d(TAG, result),
                        Throwable::printStackTrace
                );
    }
}
