package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.model.PlayerOverviewCombine;
import com.example.albert.dotasearch.repository.PlayerInfoRepository;

public class PlayerInfoViewModel extends ViewModel {
    private static final String TAG = "PlayerInfoViewModel";

    private PlayerInfoRepository repository;
    private LiveData<PlayerOverviewCombine> playerFullInfo;
    private LiveData<Boolean> isFavoritePlayer;
    private long accountId;

    public LiveData<PlayerOverviewCombine> getPlayerFullInfo() {
        return playerFullInfo;
    }

    public PlayerInfoViewModel(long accountId) {
        this.accountId = accountId;
        repository = new PlayerInfoRepository(accountId);
        playerFullInfo = repository.getLiveDataPlayerFullInfo();
        isFavoritePlayer = repository.isPlayerFavoriteById(accountId);

        Log.d(TAG, "constructor");
    }

    public LiveData<Boolean> getIsFavoritePlayer() {
        return isFavoritePlayer;
    }

    public long getAccountId() {
        return accountId;
    }

    public void insertPlayerToFavoriteList(FavoritePlayer favoritePlayer) {
        repository.insertPlayerToFavoriteList(favoritePlayer);
    }

    public void deletePlayerWithFavoriteList(long accountId) {
        repository.deletePlayerWithFavoriteList(accountId);
    }
}