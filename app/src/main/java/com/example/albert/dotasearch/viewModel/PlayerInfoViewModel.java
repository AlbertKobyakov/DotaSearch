package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.SparseArray;

import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.PlayerHero;
import com.example.albert.dotasearch.model.PlayerOverviewCombine;
import com.example.albert.dotasearch.model.Pros;
import com.example.albert.dotasearch.repository.PlayerInfoRepository;

import java.util.List;

public class PlayerInfoViewModel extends ViewModel {
    private PlayerInfoRepository repository;
    private SparseArray<Hero> heroes;
    private LiveData<PlayerOverviewCombine> playerFullInfo;
    private LiveData<Boolean> isFavoritePlayer;
    private long accountId;
    private LiveData<List<Pros>> pros;
    private LiveData<List<PlayerHero>> playerHeroes;

    public LiveData<List<Pros>> getPros() {
        return pros;
    }

    public LiveData<PlayerOverviewCombine> getPlayerFullInfo() {
        return playerFullInfo;
    }

    public PlayerInfoViewModel(long accountId) {
        repository = new PlayerInfoRepository(accountId);
        heroes = sortHeroes(repository.getAllHeroes());
        playerFullInfo = repository.getLiveDataPlayerFullInfo();
        this.accountId = accountId;
        isFavoritePlayer = repository.isPlayerFavoriteById(accountId);
        pros = repository.getPros();
        playerHeroes = repository.getPlayerHeroes();
    }

    public LiveData<Boolean> getIsFavoritePlayer() {
        return isFavoritePlayer;
    }

    public SparseArray<Hero> getHeroes() {
        return heroes;
    }

    public LiveData<List<PlayerHero>> getPlayerHeroes() {
        return playerHeroes;
    }

    private SparseArray<Hero> sortHeroes(List<Hero> heroes) {
        SparseArray<Hero> sortedHeroes = new SparseArray<>();
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println("id = " + heroes.get(i).getId() + " HERO = " + heroes.get(i));
            sortedHeroes.put(heroes.get(i).getId(), heroes.get(i));
        }
        return sortedHeroes;
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