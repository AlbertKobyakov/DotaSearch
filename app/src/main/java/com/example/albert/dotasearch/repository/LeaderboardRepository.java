package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.TimeRefreshLeaderBoard;
import com.example.albert.dotasearch.util.UtilDota;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LeaderboardRepository {
    private AppDatabase db;
    private static final String TAG = "LeaderboardRepository";
    private LiveData<TimeRefreshLeaderBoard> timeRefreshLeaderBoardLiveData;

    public LeaderboardRepository(String division) {
        db = App.get().getDB();
        timeRefreshLeaderBoardLiveData = db.leaderBoardDivisionDao().getConcreteDivisionLive(division);
        Log.d(TAG, "LeaderboardRepository create");
    }

    public LiveData<TimeRefreshLeaderBoard> getTimeRefreshLeaderBoardLiveData() {
        return timeRefreshLeaderBoardLiveData;
    }

    private void leaderboardDataAcquisitionAndRecordingToDB(String division) {
        Disposable d1 = UtilDota.initRetrofitRxDota2Ru().getLeaderBorderRx(division)
                .flatMap(timeRefreshLeaderBoard -> {
                    timeRefreshLeaderBoard.setDivision(division);
                    timeRefreshLeaderBoard.setLeaderboard(timeRefreshLeaderBoard.getLeaderboard().subList(0, 100));
                    String result;
                    if (db.leaderBoardDivisionDao().getConcreteDivision(division) != null) {
                        db.leaderBoardDivisionDao().updateAll(timeRefreshLeaderBoard);
                        result = "updateAll";
                        Log.d(TAG, "updateAll " + timeRefreshLeaderBoard.getDivision());
                    } else {
                        db.leaderBoardDivisionDao().insertAll(timeRefreshLeaderBoard);
                        Log.d(TAG, "insertAll " + timeRefreshLeaderBoard.getDivision());
                        result = "insertAll";
                    }
                    return Single.just(result);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> Log.d(TAG, "recordDataAcquisitionAndRecordingToDB: " + result),
                        Throwable::printStackTrace
                );
    }

    public void checkValidLeaderBoardData(String division) {
        Log.d(TAG, division);
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
