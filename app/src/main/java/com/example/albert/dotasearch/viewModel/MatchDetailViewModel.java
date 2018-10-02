package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.albert.dotasearch.repository.MatchDetailRepository;

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