package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.albert.dotasearch.model.FoundPlayer;
import com.example.albert.dotasearch.repository.SearchRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private SearchRepository repository;
    private LiveData<List<FoundPlayer>> foundPlayers;

    public SearchViewModel() {
        repository = new SearchRepository();
        foundPlayers = repository.getFoundPlayers();
    }

    public LiveData<List<FoundPlayer>> getFoundPlayers() {
        return foundPlayers;
    }

    public void searchRequest(String query) {
        repository.searchResult(query);
    }
}
