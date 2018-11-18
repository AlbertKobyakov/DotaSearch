package com.kobyakov.d2s.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.kobyakov.d2s.model.TeamHero;
import com.kobyakov.d2s.repository.TeamInfoHeroRepository;

import java.util.List;

public class TeamInfoHeroesViewModel extends ViewModel {
    private static final String TAG = "PlayerHeroesViewModel";

    private TeamInfoHeroRepository repository;
    private long accountId;
    private LiveData<List<TeamHero>> teamHeroesLive;
    private LiveData<Integer> statusCode;

    public TeamInfoHeroesViewModel(long accountId) {
        this.accountId = accountId;
        repository = new TeamInfoHeroRepository(accountId);
        teamHeroesLive = repository.getTeamHeroes();
        statusCode = repository.getStatusCode();

        Log.d(TAG, "constructor");
    }

    public LiveData<List<TeamHero>> getTeamHeroesLive() {
        return teamHeroesLive;
    }

    public long getAccountId() {
        return accountId;
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public void repeatedRequest() {
        repository.sendRequest();
    }
}