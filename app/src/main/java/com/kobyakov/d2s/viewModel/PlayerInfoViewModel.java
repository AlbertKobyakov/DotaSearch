package com.kobyakov.d2s.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kobyakov.d2s.model.FavoritePlayer;
import com.kobyakov.d2s.model.PlayerOverviewCombine;
import com.kobyakov.d2s.repository.PlayerInfoRepository;

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

    public void repeatRequestGetPlayerFullInfo() {
        repository.sendRequestForDataPlayerFullInfo();
    }
}