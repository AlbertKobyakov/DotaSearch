package com.kobyakov.d2s.repository;

import android.arch.lifecycle.LiveData;

import com.kobyakov.d2s.App;
import com.kobyakov.d2s.database.AppDatabase;
import com.kobyakov.d2s.model.FoundPlayer;

import java.util.List;

public class FoundPlayerRepository {
    private AppDatabase db;
    private LiveData<List<FoundPlayer>> foundPlayers;
    private static final String TAG = "FoundPlayerRepository";

    public FoundPlayerRepository() {
        db = App.get().getDB();
        foundPlayers = db.foundPlayerDao().getAllLiveData();
    }

    public LiveData<List<FoundPlayer>> getFoundPlayers() {
        return foundPlayers;
    }
}