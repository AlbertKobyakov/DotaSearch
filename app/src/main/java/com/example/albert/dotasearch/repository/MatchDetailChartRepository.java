package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.ChartData;
import com.example.albert.dotasearch.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MatchDetailChartRepository {
    private AppDatabase db;
    private static final String TAG = "MatchDetailChartRep";
    private MutableLiveData<List<ChartData>> dataForDrawChart = new MutableLiveData<>();
    private long matchId;

    public MatchDetailChartRepository(long matchId) {
        this.matchId = matchId;
        db = App.get().getDB();
    }

    public MutableLiveData<List<ChartData>> getDataForDrawChart(String typeChart) {
        Log.d(TAG, "db data");
        Disposable disposable = db.matchFullInfoDao().getMatchByIdRx(matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        matchFullInfo -> {
                            List<ChartData> listData = new ArrayList<>();
                            if (typeChart.equals("gold") && matchFullInfo.getRadiantGoldAdv() != null) {
                                for (Long value : matchFullInfo.getRadiantGoldAdv()) {
                                    listData.add(new ChartData("", value, false));
                                }
                            } else if (typeChart.equals("exp") && matchFullInfo.getRadiantXpAdv() != null) {
                                for (Long value : matchFullInfo.getRadiantXpAdv()) {
                                    listData.add(new ChartData("", value, false));
                                }
                            } else if (typeChart.equals("gpm") && matchFullInfo.getPlayers() != null) {
                                for (Player player : matchFullInfo.getPlayers()) {
                                    String playerName = getRealPlayerName(player);
                                    ChartData chartData = new ChartData(playerName, player.getGoldPerMin(), player.isIsRadiant());
                                    listData.add(chartData);
                                }

                                Collections.sort(listData, (o1, o2) -> {
                                    Long value1 = o1.getValue();
                                    Long value2 = o2.getValue();
                                    return value1.compareTo(value2);
                                });
                            } else if (typeChart.equals("xpm") && matchFullInfo.getPlayers() != null) {
                                for (Player player : matchFullInfo.getPlayers()) {
                                    String playerName = getRealPlayerName(player);
                                    ChartData chartData = new ChartData(playerName, player.getXpPerMin(), player.isIsRadiant());
                                    listData.add(chartData);
                                }

                                Collections.sort(listData, (o1, o2) -> {
                                    Long value1 = o1.getValue();
                                    Long value2 = o2.getValue();
                                    return value1.compareTo(value2);
                                });
                            } else if (typeChart.equals("total damage") && matchFullInfo.getPlayers() != null) {
                                for (Player player : matchFullInfo.getPlayers()) {
                                    listData.add(new ChartData(getRealPlayerName(player), player.getHeroDamage(), player.isIsRadiant()));
                                }

                                Collections.sort(listData, (o1, o2) -> {
                                    Long value1 = o1.getValue();
                                    Long value2 = o2.getValue();
                                    return value1.compareTo(value2);
                                });
                            } else if (typeChart.equals("total gold") && matchFullInfo.getPlayers() != null) {
                                for (Player player : matchFullInfo.getPlayers()) {
                                    listData.add(new ChartData(getRealPlayerName(player), player.getTotalGold(), player.isIsRadiant()));
                                }

                                Collections.sort(listData, (o1, o2) -> {
                                    Long value1 = o1.getValue();
                                    Long value2 = o2.getValue();
                                    return value1.compareTo(value2);
                                });
                            }

                            if (listData.size() > 0) {
                                dataForDrawChart.postValue(listData);
                            } else {
                                dataForDrawChart.postValue(null);
                            }
                        }
                );
        return dataForDrawChart;
    }

    private String getRealPlayerName(Player player) {
        String name = "";
        if (player != null) {
            if (player.getName() != null) {
                name = player.getName();
            } else {
                name = player.getPersonaname();
            }
        }

        return name;
    }
}