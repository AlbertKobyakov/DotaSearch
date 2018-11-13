package com.kobyakov.d2s.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.kobyakov.d2s.model.PlayerHero;
import com.kobyakov.d2s.repository.PlayerInfoHeroRepository;

import java.util.List;

public class PlayerInfoHeroesViewModel extends ViewModel {
    private static final String TAG = "PlayerHeroesViewModel";

    private PlayerInfoHeroRepository repository;
    private long accountId;
    private LiveData<List<PlayerHero>> playerHeroes;
    private LiveData<Integer> statusCode;

    public PlayerInfoHeroesViewModel(long accountId) {
        this.accountId = accountId;
        repository = new PlayerInfoHeroRepository(accountId);
        playerHeroes = repository.getPlayerHeroes();
        statusCode = repository.getStatusCode();

        Log.d(TAG, "constructor");
    }

    public LiveData<List<PlayerHero>> getPlayerHeroes() {
        return playerHeroes;
    }

    public long getAccountId() {
        return accountId;
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public void repeatedRequest() {
        repository.sendRequest();
    }
}