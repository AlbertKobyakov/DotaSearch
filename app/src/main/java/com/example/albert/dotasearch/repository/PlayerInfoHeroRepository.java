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
import retrofit2.Response;

public class PlayerInfoHeroRepository {

    public static final String TAG = "PlayerInfoHeroRep";

    private long accountId;
    private HeroDao heroDao;
    private AppDatabase db;
    private MutableLiveData<List<PlayerHero>> playerHeroes;
    private MutableLiveData<Integer> statusCode;

    public PlayerInfoHeroRepository(long accountId) {
        this.accountId = accountId;
        db = App.get().getDB();
        heroDao = db.heroDao();
        playerHeroes = new MutableLiveData<>();
        statusCode = new MutableLiveData<>();
        sendRequest();
    }

    public void sendRequest() {
        Log.d(TAG, "NETWORK REQUEST");
        Single<List<Hero>> listHero = heroDao.getAllRx().subscribeOn(Schedulers.io());

        Single<Response<List<PlayerHero>>> listPlayerHero = UtilDota.initRetrofitRx()
                .getPlayerHeroesResponse(accountId)
                .subscribeOn(Schedulers.io());

        Disposable disposable = Single.zip(listHero, listPlayerHero, (listHeroResponse, listPlayerHeroResponse) -> {
            SparseArray<Hero> heroSparseArray = sortHeroes(listHeroResponse);
            if (listPlayerHeroResponse.body() != null) {
                for (PlayerHero playerHero : listPlayerHeroResponse.body()) {
                    playerHero.setHeroName(heroSparseArray.get(Integer.parseInt(playerHero.getHeroId())).getLocalizedName());
                    playerHero.setHeroImg(heroSparseArray.get(Integer.parseInt(playerHero.getHeroId())).getImg());
                }
            }

            return listPlayerHeroResponse;
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            playerHeroes.setValue(response.body());
                            if (response.code() != 200) {
                                statusCode.setValue(response.code());
                            }
                        },
                        err -> {
                            Log.e(TAG, err.getLocalizedMessage());
                            statusCode.setValue(-200);
                        }
                );
    }

    public LiveData<List<PlayerHero>> getPlayerHeroes() {
        if (playerHeroes == null) {
            playerHeroes = new MutableLiveData<>();
        }

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

    public MutableLiveData<Integer> getStatusCode() {
        return statusCode;
    }
}
