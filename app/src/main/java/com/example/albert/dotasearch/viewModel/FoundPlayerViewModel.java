package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.albert.dotasearch.model.FoundPlayer;
import com.example.albert.dotasearch.repository.FoundPlayerRepository;

import java.util.List;

public class FoundPlayerViewModel extends ViewModel {
    private FoundPlayerRepository repository;
    private LiveData<List<FoundPlayer>> foundPlayers;

    public FoundPlayerViewModel() {
        repository = new FoundPlayerRepository();
        foundPlayers = repository.getFoundPlayers();
    }

    public LiveData<List<FoundPlayer>> getFoundPlayers() {
        return foundPlayers;
    }
}