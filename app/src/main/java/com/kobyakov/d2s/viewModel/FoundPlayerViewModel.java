package com.kobyakov.d2s.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kobyakov.d2s.model.FoundPlayer;
import com.kobyakov.d2s.repository.FoundPlayerRepository;

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