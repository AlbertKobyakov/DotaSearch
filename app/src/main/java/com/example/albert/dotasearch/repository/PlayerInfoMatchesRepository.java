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

public class PlayerInfoMatchesRepository {
    private static final String TAG = PlayerInfoMatchesRepository.class.getSimpleName();
    private AppDatabase db;
    private MutableLiveData<List<MatchShortInfo>> matches;

    public PlayerInfoMatchesRepository() {
        db = App.get().getDB();
    }

    public LiveData<List<MatchShortInfo>> getMatches(long accountId) {
        if (matches == null) {
            matches = new MutableLiveData<>();
        }

        Log.d(TAG, "Network Request");

        Single<List<MatchShortInfo>> listMatchShortInfo = UtilDota.initRetrofitRx()
                .getMatchesPlayerRx(accountId)
                .doOnError(Throwable::printStackTrace);

        Single<List<Hero>> listHeroes = db.heroDao().getAllRx().doOnError(Throwable::printStackTrace);

        Disposable disposable = Single.zip(listMatchShortInfo, listHeroes, new BiFunction<List<MatchShortInfo>, List<Hero>, List<MatchShortInfo>>() {
            @Override
            public List<MatchShortInfo> apply(List<MatchShortInfo> matchShortInfos, List<Hero> heroes) {
                SparseArray<Hero> heroMap = sortHeroes(heroes);

                for (MatchShortInfo matchShortInfo : matchShortInfos) {
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
                return matchShortInfos;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        fullPlayerMatchesWithHeroUrl -> matches.setValue(fullPlayerMatchesWithHeroUrl),
                        err -> {
                            err.printStackTrace();
                            Log.e(TAG, err.getLocalizedMessage());
                        }
                );

        return matches;
    }

    private SparseArray<Hero> sortHeroes(List<Hero> heroes) {
        SparseArray<Hero> sortedHeroes = new SparseArray<>();
        for (int i = 0; i < heroes.size(); i++) {
            sortedHeroes.put(heroes.get(i).getId(), heroes.get(i));
        }
        return sortedHeroes;
    }
}