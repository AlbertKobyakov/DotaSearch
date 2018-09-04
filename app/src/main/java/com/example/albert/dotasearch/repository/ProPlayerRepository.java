package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.ProPlayer;

import java.util.List;

public class ProPlayerRepository {
    private AppDatabase db;
    private LiveData<List<ProPlayer>> proPlayers;
    private static final String TAG = "ProPlayerRepository";

    public ProPlayerRepository() {
        db = App.get().getDB();
        proPlayers = db.proPlayerDao().getAllRx();
    }

    public LiveData<List<ProPlayer>> getProPlayersWithDB() {
        return proPlayers;
    }

   /* public void setProPlayersToDB(){
        Disposable disposable = UtilDota.initRetrofitRx()
                .getAllProPlayerRx()
                .flatMap(proPlayers1 -> {
                    UtilDota.storeProPlayersInDB(proPlayers1);
                    return Single.just(proPlayers1);
                })
                .subscribe(
                        proPlayers1 -> Log.d(TAG, "ProPlayers success save to DB"),
                        err -> err.printStackTrace()
                );
    }*/
}
