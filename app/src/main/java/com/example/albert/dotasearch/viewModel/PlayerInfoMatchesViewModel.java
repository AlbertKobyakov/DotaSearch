package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.repository.PlayerInfoMatchesRepository;

import java.util.List;

public class PlayerInfoMatchesViewModel extends ViewModel {

    private PlayerInfoMatchesRepository repository;
    private LiveData<List<MatchShortInfo>> matches;

    public PlayerInfoMatchesViewModel(long accountId) {
        repository = new PlayerInfoMatchesRepository();
        matches = repository.getMatches(accountId);
        Log.d("PlayerMatchesViewModel", "constructor");
    }

    public LiveData<List<MatchShortInfo>> getMatches() {
        return matches;
    }
}
