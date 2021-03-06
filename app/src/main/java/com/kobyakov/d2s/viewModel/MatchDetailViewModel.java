package com.kobyakov.d2s.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kobyakov.d2s.repository.MatchDetailRepository;

public class MatchDetailViewModel extends ViewModel {
    private MatchDetailRepository repository;
    private LiveData<Integer> statusCode;

    public MatchDetailViewModel(long matchId) {
        repository = new MatchDetailRepository(matchId);
        statusCode = repository.getStatusCode();
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public void repeatRequest() {
        repository.sendRequest();
    }
}