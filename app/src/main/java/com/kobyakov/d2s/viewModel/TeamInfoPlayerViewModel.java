package com.kobyakov.d2s.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kobyakov.d2s.model.TeamPlayer;
import com.kobyakov.d2s.repository.TeamInfoPlayerRepository;

import java.util.List;

public class TeamInfoPlayerViewModel extends ViewModel {
    private static final String TAG = "PlayerInfoProsViewModel";

    private TeamInfoPlayerRepository repository;
    private LiveData<List<TeamPlayer>> teamPlayers;
    private LiveData<Integer> statusCode;

    public TeamInfoPlayerViewModel(long accountId) {
        Log.d(TAG, "constructor");
        repository = new TeamInfoPlayerRepository(accountId);
        teamPlayers = repository.getTeamPlayers();
        statusCode = repository.getStatusCode();
    }

    public LiveData<List<TeamPlayer>> getTeamPlayers() {
        return teamPlayers;
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public void repeatedRequest() {
        repository.sendRequest();
    }
}