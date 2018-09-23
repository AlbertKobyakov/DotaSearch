package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.util.SparseArray;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.dao.HeroDao;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.PlayerHero;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlayerInfoHeroRepository {

    public static final String TAG = "PlayerInfoRepository";

    private long accountId;
    private HeroDao heroDao;
    private AppDatabase db;
    private MutableLiveData<List<PlayerHero>> playerHeroes;

    public PlayerInfoHeroRepository(long accountId) {
        this.accountId = accountId;
        db = App.get().getDB();
        heroDao = db.heroDao();
    }

    public LiveData<List<PlayerHero>> getPlayerHeroes() {
        if (playerHeroes == null) {
            playerHeroes = new MutableLiveData<>();
        }

        Single<List<Hero>> listHero = heroDao.getAllRx().subscribeOn(Schedulers.io());

        Single<List<PlayerHero>> listPlayerHero = UtilDota.initRetrofitRx()
                .getPlayerHeroes(accountId)
                .subscribeOn(Schedulers.io());

        Disposable disposable = Single.zip(listHero, listPlayerHero, (listHeroResponse, listPlayerHeroResponse) -> {
            SparseArray<Hero> heroSparseArray = sortHeroes(listHeroResponse);
            for (PlayerHero playerHero : listPlayerHeroResponse) {
                playerHero.setHeroName(heroSparseArray.get(Integer.parseInt(playerHero.getHeroId())).getLocalizedName());
                playerHero.setHeroImg(heroSparseArray.get(Integer.parseInt(playerHero.getHeroId())).getImg());
            }

            return listPlayerHeroResponse;
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        playerHeroesResponse -> playerHeroes.setValue(playerHeroesResponse),
                        err -> Log.e(TAG, err.getLocalizedMessage())
                );

        return playerHeroes;
    }

    private SparseArray<Hero> sortHeroes(List<Hero> heroes) {
        SparseArray<Hero> sortedHeroes = new SparseArray<>();
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println("id = " + heroes.get(i).getId() + " HERO = " + heroes.get(i));
            sortedHeroes.put(heroes.get(i).getId(), heroes.get(i));
        }
        return sortedHeroes;
    }
}
