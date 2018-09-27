package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.util.SparseArray;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class PlayerInfoMatchesRepository {
    private static final String TAG = "PlayerInfoMatchesRep";
    private AppDatabase db;
    private MutableLiveData<List<MatchShortInfo>> matches;
    private MutableLiveData<Integer> statusCode;
    private long accountId;

    public PlayerInfoMatchesRepository(long accountId) {
        this.accountId = accountId;
        db = App.get().getDB();
        statusCode = new MutableLiveData<>();
        matches = new MutableLiveData<>();
        sendRequest();
    }

    public LiveData<List<MatchShortInfo>> getMatches() {
        if (matches == null) {
            matches = new MutableLiveData<>();
        }
        return matches;
    }

    public void sendRequest() {
        Log.d(TAG, "NETWORK REQUEST");

        Single<Response<List<MatchShortInfo>>> listMatchShortInfo = UtilDota.initRetrofitRx()
                .getMatchesPlayerResponse(accountId);

        Single<List<Hero>> listHeroes = db.heroDao().getAllRx();

        Disposable disposable = Single.zip(listMatchShortInfo, listHeroes, new BiFunction<Response<List<MatchShortInfo>>, List<Hero>, Response<List<MatchShortInfo>>>() {
            @Override
            public Response<List<MatchShortInfo>> apply(Response<List<MatchShortInfo>> matchShortInfos, List<Hero> heroes) {
                SparseArray<Hero> heroMap = sortHeroes(heroes);

                if (matchShortInfos.body() != null) {
                    for (MatchShortInfo matchShortInfo : matchShortInfos.body()) {
                        int heroId = (int) matchShortInfo.getHeroId();

                        String heroImageUrl = "";
                        String heroName = "";

                        if (heroMap.get(heroId) != null) {
                            heroImageUrl = heroMap.get(heroId).getImg();
                            heroName = heroMap.get(heroId).getLocalizedName();
                        }

                        matchShortInfo.setHeroImageUrl(heroImageUrl);
                        matchShortInfo.setHeroName(heroName);
                    }
                }
                return matchShortInfos;
            }
        })
                .subscribeOn(Schedulers.io())
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
                            statusCode.setValue(-200);
                        }
                );
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