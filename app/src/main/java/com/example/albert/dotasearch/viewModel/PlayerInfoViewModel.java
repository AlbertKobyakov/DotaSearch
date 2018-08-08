package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.repository.PlayerInfoRepository;

import java.util.List;

public class PlayerInfoViewModel extends ViewModel {
    PlayerInfoRepository repository;
    LiveData<List<MatchShortInfo>> matches;
    List<Hero> heroes;

    public PlayerInfoViewModel(long accountId) {
        repository = new PlayerInfoRepository(accountId);
        insertMatches();
        matches = repository.getAllMatches();
        heroes = repository.getAllHeroes();
    }

    public LiveData<List<MatchShortInfo>> getMatches() {
        return matches;
    }

    private void insertMatches(){
        repository.insertAllMatchesPlayerToRoom();
    }

    public List<Hero> getHeroes() {
        return heroes;
    }
}
