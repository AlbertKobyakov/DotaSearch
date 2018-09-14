package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.repository.FavoritePlayersRepository;

import java.util.List;

public class FavoritePlayersViewModel extends ViewModel {
    private FavoritePlayersRepository mRepository;

    private LiveData<List<FavoritePlayer>> favoritePlayersLive;

    public FavoritePlayersViewModel() {
        mRepository = new FavoritePlayersRepository();
        favoritePlayersLive = mRepository.getAllFavoritePlayer();
    }

    public LiveData<List<FavoritePlayer>> getAllFavoritePlayers() {
        return favoritePlayersLive;
    }

    public void deletePlayerWithFavoriteList(long accountId) {
        mRepository.deletePlayerWithFavoriteList(accountId);
    }
}
