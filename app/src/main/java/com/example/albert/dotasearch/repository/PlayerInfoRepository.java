package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.dao.HeroDao;
import com.example.albert.dotasearch.dao.MatchShortInfoDao;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlayerInfoRepository {

    private long accountId;
    private MatchShortInfoDao matchShortInfoDao;
    private LiveData<List<MatchShortInfo>> allMatches;
    private HeroDao heroDao;
    private AppDatabase db;

    public PlayerInfoRepository(long accountId) {
        this.accountId = accountId;
        db = App.get().getDB();
        matchShortInfoDao = db.matchShortInfoDao();
        heroDao = db.heroDao();
        allMatches = getAllMatches();
    }

    public void insertAllMatchesPlayerToRoom(){
        Disposable dis = UtilDota.initRetrofitRx()
                .getMatchesPlayerRx(accountId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        matchShortInfos -> {
                            db.matchShortInfoDao().deleteAllMatches();
                            db.matchShortInfoDao().insertAll(matchShortInfos);
                        },
                        Throwable::printStackTrace
                );
    }

    public LiveData<List<MatchShortInfo>> getAllMatches(){

        try {
            return new getMatchesAsyncTask(matchShortInfoDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Hero> getAllHeroes(){
        try {
            return new getHeroesAsyncTask(heroDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class getMatchesAsyncTask extends AsyncTask<Void, Void, LiveData<List<MatchShortInfo>>>{
        MatchShortInfoDao daoAsync;

        getMatchesAsyncTask(MatchShortInfoDao dao) {
            this.daoAsync = dao;
        }

        @Override
        protected LiveData<List<MatchShortInfo>> doInBackground(Void... voids) {
            return daoAsync.getAllMatch();
        }
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
