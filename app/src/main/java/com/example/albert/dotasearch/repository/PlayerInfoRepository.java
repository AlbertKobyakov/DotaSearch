package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.dao.HeroDao;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.model.PlayerInfo;
import com.example.albert.dotasearch.model.PlayerOverviewCombine;
import com.example.albert.dotasearch.model.WinLose;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class PlayerInfoRepository {

    public static final String TAG = "PlayerInfoRepository";

    private long accountId;
    private HeroDao heroDao;
    private AppDatabase db;
    private MutableLiveData<PlayerOverviewCombine> playerFullInfo;
    private MutableLiveData<List<FavoritePlayer>> favoritePlayers;
    private MutableLiveData<Boolean> isFavoritePlayer;

    public PlayerInfoRepository(long accountId) {
        this.accountId = accountId;
        db = App.get().getDB();
        heroDao = db.heroDao();
    }

    public LiveData<PlayerOverviewCombine> getLiveDataPlayerFullInfo(){
        if(playerFullInfo == null){
            playerFullInfo = new MutableLiveData<>();

            System.out.println("NETWORK");
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
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            playerOverviewCombine -> playerFullInfo.setValue(playerOverviewCombine),
                            error -> System.out.println(error.getLocalizedMessage())
                    );
        }

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

    public LiveData<PlayerOverviewCombine> getPlayerFullInfo() {
        return playerFullInfo;
    }

    public void insertPlayerToFavoriteList(FavoritePlayer favoritePlayer) {

        Disposable disposable = Single.fromCallable(() -> {
            db.favoritePlayerDao().insertPlayer(favoritePlayer);
            return Single.just(favoritePlayer.getAccountId());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        accountId -> Log.d(TAG, "Player " + accountId + " inserted to favorite table"),
                        Throwable::printStackTrace
                );

        isPlayerFavoriteById(favoritePlayer.getAccountId());

    }

    public void deletePlayerWithFavoriteList(long accountId){
        Disposable disposable = Single.fromCallable(() -> {
            db.favoritePlayerDao().deleteById(accountId);
            return Single.just(accountId);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        id -> Log.d(TAG, "Player " + id + " deleted"),
                        Throwable::printStackTrace
                );

        isPlayerFavoriteById(accountId);
    }

    public LiveData<Boolean> isPlayerFavoriteById(long accountId){
        if(isFavoritePlayer == null){
            isFavoritePlayer = new MutableLiveData<>();
        }

        Disposable disposable = db.favoritePlayerDao().getFavoritePlayerByIdRx(accountId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        favoritePlayer -> isFavoritePlayer.setValue(true),
                        err -> isFavoritePlayer.setValue(false)
                );

        return isFavoritePlayer;

    }

    /*public LiveData<List<FavoritePlayer>> getAllFavoritePlayers(){
        if(favoritePlayers == null){
            favoritePlayers = new MutableLiveData<>();
        }

        Disposable disposable = db.favoritePlayerDao().getAllRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        favoritePlayersTemp -> favoritePlayers.setValue(favoritePlayersTemp),
                        err -> Log.e(TAG, err.getLocalizedMessage())
                );

        return favoritePlayers;

    }*/

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
