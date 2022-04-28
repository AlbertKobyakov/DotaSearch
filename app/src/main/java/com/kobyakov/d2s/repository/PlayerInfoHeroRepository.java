package com.kobyakov.d2s.repository;

import android.util.Log;
import android.util.SparseArray;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.kobyakov.d2s.App;
import com.kobyakov.d2s.dao.HeroDao;
import com.kobyakov.d2s.database.AppDatabase;
import com.kobyakov.d2s.model.Hero;
import com.kobyakov.d2s.model.PlayerHero;
import com.kobyakov.d2s.util.UtilDota;

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
                            if (err.getLocalizedMessage().contains("timeout")) {
                                statusCode.setValue(-300);
                            } else {
                                statusCode.setValue(-200);
                            }
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
            sortedHeroes.put(heroes.get(i).getId(), heroes.get(i));
        }
        return sortedHeroes;
    }

    public MutableLiveData<Integer> getStatusCode() {
        return statusCode;
    }
}
