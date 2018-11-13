package com.kobyakov.d2s.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.kobyakov.d2s.model.MatchShortInfo;
import com.kobyakov.d2s.repository.PlayerInfoMatchesRepository;

import java.util.List;

public class PlayerInfoMatchesViewModel extends ViewModel {

    private PlayerInfoMatchesRepository repository;
    private LiveData<List<MatchShortInfo>> matches;
    private LiveData<Integer> statusCode;

    public PlayerInfoMatchesViewModel(long accountId) {
        repository = new PlayerInfoMatchesRepository(accountId);
        matches = repository.getMatches();
        statusCode = repository.getStatusCode();
        Log.d("PlayerMatchesViewModel", "constructor");
    }

    public LiveData<List<MatchShortInfo>> getMatches() {
        return matches;
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public void repeatedRequest() {
        repository.sendRequest();
    }
}
