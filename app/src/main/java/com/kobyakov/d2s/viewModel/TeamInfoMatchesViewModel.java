package com.kobyakov.d2s.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kobyakov.d2s.model.TeamMatch;
import com.kobyakov.d2s.repository.TeamInfoMatchesRepository;

import java.util.List;

public class TeamInfoMatchesViewModel extends ViewModel {

    private TeamInfoMatchesRepository repository;
    private LiveData<List<TeamMatch>> matches;
    private LiveData<Integer> statusCode;

    public TeamInfoMatchesViewModel(long accountId) {
        repository = new TeamInfoMatchesRepository(accountId);
        matches = repository.getMatches();
        statusCode = repository.getStatusCode();
        Log.d("TeamInfoMatchesViewM", "constructor");
    }

    public LiveData<List<TeamMatch>> getMatches() {
        return matches;
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public void repeatedRequest() {
        repository.sendRequest();
    }
}
