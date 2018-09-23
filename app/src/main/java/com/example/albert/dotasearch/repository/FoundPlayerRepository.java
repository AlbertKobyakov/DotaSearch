package com.example.albert.dotasearch.repository;

import android.arch.lifecycle.LiveData;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.FoundPlayer;

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