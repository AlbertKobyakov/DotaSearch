package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.activity.MatchDetailActivity;
import com.example.albert.dotasearch.dao.HeroDao;
import com.example.albert.dotasearch.dao.ItemDao;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.MatchDetailWithItems;
import com.example.albert.dotasearch.model.MatchFullInfo;
import com.example.albert.dotasearch.model.PlayerOverviewCombine;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class MatchDetailRepository {
    private long matchId;
    private ItemDao itemDao;
    private AppDatabase db;
    private MutableLiveData<MatchDetailWithItems> matchDetailWithItemsMutableLiveData = new MutableLiveData<>();

    public MatchDetailRepository(long matchId) {
        this.matchId = matchId;
        db = App.get().getDB();
        itemDao = db.itemDao();
    }

    public MutableLiveData<MatchDetailWithItems> getMatchDetailWithItemsMutableLiveData() {
        System.out.println("11111111 ZIP NETWORK");

        Observable<MatchFullInfo> matchFullInfoObservable = UtilDota.initRetrofitRx()
                .getMatchFullInfoRx(matchId)
                .subscribeOn(Schedulers.io());

        Observable<List<Item>> itemsObservable = itemDao.getAllRx()
                .toObservable()
                .subscribeOn(Schedulers.io());

        Disposable disposable = Observable.zip(matchFullInfoObservable, itemsObservable, new BiFunction<MatchFullInfo, List<Item>, MatchDetailWithItems>() {
            @Override
            public MatchDetailWithItems apply(MatchFullInfo matchFullInfo, List<Item> items) throws Exception {
                return new MatchDetailWithItems(matchFullInfo, items, matchId);
            }
        }).subscribe(
                matchDetailWithItems1 -> {
                    matchDetailWithItemsMutableLiveData.postValue(matchDetailWithItems1);
                }, err -> System.out.println(err.getLocalizedMessage())
        );

        return matchDetailWithItemsMutableLiveData;
    }
}
