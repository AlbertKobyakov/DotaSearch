package com.kobyakov.d2s.repository;

import android.util.Log;
import android.util.SparseArray;

import androidx.lifecycle.MutableLiveData;

import com.kobyakov.d2s.App;
import com.kobyakov.d2s.dao.HeroDao;
import com.kobyakov.d2s.dao.ItemDao;
import com.kobyakov.d2s.dao.MatchFullInfoDao;
import com.kobyakov.d2s.database.AppDatabase;
import com.kobyakov.d2s.model.Hero;
import com.kobyakov.d2s.model.Item;
import com.kobyakov.d2s.model.ItemsWithMatchDetail;
import com.kobyakov.d2s.model.MatchFullInfo;
import com.kobyakov.d2s.model.Player;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MatchDetailOverViewRepository {

    public static final String TAG = "PlayerInfoHeroRep";

    private ItemDao itemDao;
    private MatchFullInfoDao matchFullInfoDao;
    private HeroDao heroDao;
    private AppDatabase db;
    private long matchId;

    private MutableLiveData<ItemsWithMatchDetail> itemsWithMatchDetailMutableLiveData;

    public MatchDetailOverViewRepository(long matchId) {
        this.matchId = matchId;
        db = App.get().getDB();
        itemDao = db.itemDao();
        matchFullInfoDao = db.matchFullInfoDao();
        heroDao = db.heroDao();
        //getMatchDetailAndItems();
    }

    public MutableLiveData<ItemsWithMatchDetail> getItemsWithMatchDetailMutableLiveData() {
        if (itemsWithMatchDetailMutableLiveData == null) {
            itemsWithMatchDetailMutableLiveData = new MutableLiveData<>();
        }

        Log.d(TAG, "DB REQUEST");
        Single<List<Item>> itemsSingle = itemDao.getAllRx();
        Single<MatchFullInfo> matchFullInfoSingle = matchFullInfoDao.getMatchByIdRx(matchId);

        Disposable disposable = Single.zip(itemsSingle, matchFullInfoSingle, (items, match) -> {
            SparseArray<Hero> heroesSparse = new SparseArray<>();
            SparseArray<Item> itemsSparse = new SparseArray<>();
            if (match.getPlayers() != null && match.getPlayers().size() == 10) {

                List<Integer> heroIds = new ArrayList<>();
                List<Long> itemIds = new ArrayList<>();

                for (Player player : match.getPlayers()) {
                    heroIds.add(player.getHeroId());

                    itemIds.add(player.getItem0());
                    itemIds.add(player.getItem1());
                    itemIds.add(player.getItem2());
                    itemIds.add(player.getItem3());
                    itemIds.add(player.getItem4());
                    itemIds.add(player.getItem5());
                }

                //List<Hero> heroList = heroDao.heroFindByIds(heroIds);
                List<Hero> heroList = heroDao.getAll();

                for (Hero hero : heroList) {
                    heroesSparse.put(hero.getId(), hero);
                }
                List<Item> itemList = itemDao.itemFindByIds(itemIds);
                for (Item item : itemList) {
                    itemsSparse.put((int) item.getId(), item);
                }
                Log.d(TAG, itemList.toString());
            }


            return new ItemsWithMatchDetail(match, itemsSparse, heroesSparse);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        itemsWithMatchDetailSingle -> itemsWithMatchDetailMutableLiveData.postValue(itemsWithMatchDetailSingle),
                        Throwable::printStackTrace
                );

        return itemsWithMatchDetailMutableLiveData;
    }
}