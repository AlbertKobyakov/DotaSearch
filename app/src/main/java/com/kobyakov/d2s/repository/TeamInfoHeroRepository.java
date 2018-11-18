package com.kobyakov.d2s.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.util.SparseArray;

import com.kobyakov.d2s.App;
import com.kobyakov.d2s.dao.HeroDao;
import com.kobyakov.d2s.database.AppDatabase;
import com.kobyakov.d2s.model.Hero;
import com.kobyakov.d2s.model.TeamHero;
import com.kobyakov.d2s.util.UtilDota;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class TeamInfoHeroRepository {

    public static final String TAG = "TeamInfoHeroRep";

    private long accountId;
    private HeroDao heroDao;
    private AppDatabase db;
    private MutableLiveData<List<TeamHero>> teamHeroes;
    private MutableLiveData<Integer> statusCode;

    public TeamInfoHeroRepository(long accountId) {
        this.accountId = accountId;
        db = App.get().getDB();
        heroDao = db.heroDao();
        teamHeroes = new MutableLiveData<>();
        statusCode = new MutableLiveData<>();
        sendRequest();
    }

    public void sendRequest() {
        Log.d(TAG, "NETWORK REQUEST");
        Single<List<Hero>> listHero = heroDao.getAllRx().subscribeOn(Schedulers.io());

        Single<Response<List<TeamHero>>> listPlayerHero = UtilDota.initRetrofitRx()
                .getTeamHeroesResponse(accountId)
                .subscribeOn(Schedulers.io());

        Disposable disposable = Single.zip(listHero, listPlayerHero, (listHeroResponse, listTeamHeroResponse) -> {
            SparseArray<Hero> heroSparseArray = sortHeroes(listHeroResponse);
            if (listTeamHeroResponse.body() != null) {
                for (TeamHero teamHero : listTeamHeroResponse.body()) {
                    teamHero.setHeroName(heroSparseArray.get(teamHero.getHeroId()).getLocalizedName());
                    teamHero.setHeroImg(heroSparseArray.get(teamHero.getHeroId()).getImg());
                }
            }

            return listTeamHeroResponse;
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            teamHeroes.setValue(response.body());
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

    public LiveData<List<TeamHero>> getTeamHeroes() {
        if (teamHeroes == null) {
            teamHeroes = new MutableLiveData<>();
        }

        return teamHeroes;
    }

    private SparseArray<Hero> sortHeroes(List<Hero> heroes) {
        SparseArray<Hero> sortedHeroes = new SparseArray<>();
        for (int i = 0; i < heroes.size(); i++) {
            //System.out.println("id = " + heroes.get(i).getId() + " HERO = " + heroes.get(i));
            sortedHeroes.put(heroes.get(i).getId(), heroes.get(i));
        }
        return sortedHeroes;
    }

    public MutableLiveData<Integer> getStatusCode() {
        return statusCode;
    }
}
