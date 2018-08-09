package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.dao.HeroDao;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.model.PlayerInfo;
import com.example.albert.dotasearch.model.PlayerOverviewCombine;
import com.example.albert.dotasearch.model.WinLose;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class PlayerInfoRepository {

    private long accountId;
    private HeroDao heroDao;
    private AppDatabase db;
    private MutableLiveData<PlayerOverviewCombine> playerFullInfo = new MutableLiveData<>();

    public PlayerInfoRepository(long accountId) {
        this.accountId = accountId;
        db = App.get().getDB();
        heroDao = db.heroDao();
    }

    public LiveData<PlayerOverviewCombine> getLiveDataPlayerFullInfo(){
        Single<PlayerInfo> playerInfo = UtilDota.initRetrofitRx()
                .getPlayerInfoById(accountId)
                .subscribeOn(Schedulers.io());

        Single<WinLose> winLose = UtilDota.initRetrofitRx()
                .getPlayerWinLoseById(accountId)
                .subscribeOn(Schedulers.io());

        Single<List<MatchShortInfo>> allMatches = UtilDota.initRetrofitRx()
                .getMatchesPlayerRx(accountId)
                .subscribeOn(Schedulers.io());

        Disposable disposable = Single.zip(
                playerInfo,
                winLose,
                allMatches,
                (playerInfo1, winLose1, allMatches1) -> new PlayerOverviewCombine(playerInfo1, winLose1, allMatches1, accountId))
                .subscribe(
                        playerOverviewCombine -> {
                            playerFullInfo.postValue(playerOverviewCombine);
                        },
                        err -> System.out.println(err.getLocalizedMessage())
                );

        return playerFullInfo;
    }

    public List<Hero> getAllHeroes(){
        Log.e("TabPlayerOverview12", "DB GET HEROES");
        try {
            return new getHeroesAsyncTask(heroDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class getHeroesAsyncTask extends AsyncTask<Void, Void, List<Hero>>{
        HeroDao daoAsync;

        getHeroesAsyncTask(HeroDao dao) {
            this.daoAsync = dao;
        }

        @Override
        protected List<Hero> doInBackground(Void... voids) {
            return daoAsync.getAll();
        }
    }
}
