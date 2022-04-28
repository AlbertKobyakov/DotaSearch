package com.kobyakov.d2s.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kobyakov.d2s.model.FavoritePlayer;
import com.kobyakov.d2s.repository.SearchPlayersRepository;

import java.util.List;

import io.reactivex.Single;

public class SearchViewModel extends ViewModel {
    private SearchPlayersRepository repository;

    private LiveData<List<FavoritePlayer>> favoritePlayersLive;
    private LiveData<Integer> responseStatusCode;

    public SearchViewModel() {
        repository = new SearchPlayersRepository();
        favoritePlayersLive = repository.getAllFavoritePlayer();
        responseStatusCode = repository.getResponseStatusCode();
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

    public LiveData<Integer> getResponseStatusCode() {
        return responseStatusCode;
    }

    public Single<Boolean> hasInternet() {
        return repository.hasInternetConnection();
    }
}