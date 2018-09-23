package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.repository.SearchPlayersRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private SearchPlayersRepository repository;

    private LiveData<List<FavoritePlayer>> favoritePlayersLive;
    private LiveData<Boolean> isRequestSearchSuccessful;

    public SearchViewModel() {
        repository = new SearchPlayersRepository();
        favoritePlayersLive = repository.getAllFavoritePlayer();
        isRequestSearchSuccessful = repository.getIsRequestSearchSuccessful();
    }

    public LiveData<List<FavoritePlayer>> getAllFavoritePlayers() {
        return favoritePlayersLive;
    }

    public void deletePlayerWithFavoriteList(long accountId) {
        repository.deletePlayerWithFavoriteList(accountId);
    }

    public void searchRequest(String query) {
        repository.searchResult(query);
    }

    public LiveData<Boolean> getIsRequestSearchSuccessful() {
        return isRequestSearchSuccessful;
    }
}