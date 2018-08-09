package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.PlayerOverviewCombine;
import com.example.albert.dotasearch.repository.PlayerInfoRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerInfoViewModel extends ViewModel {
    private PlayerInfoRepository repository;
    private Map<Integer, Hero> heroes;
    private LiveData<PlayerOverviewCombine> playerFullInfo;


    public LiveData<PlayerOverviewCombine> getPlayerFullInfo() {
        return playerFullInfo;
    }

    public PlayerInfoViewModel(long accountId) {
        repository = new PlayerInfoRepository(accountId);
        heroes = sortHeroes(repository.getAllHeroes());
        playerFullInfo = repository.getLiveDataPlayerFullInfo();
    }

    public Map<Integer, Hero> getHeroes() {
        return heroes;
    }

    public Map<Integer, Hero> sortHeroes(List<Hero> heroes){
        Map<Integer, Hero> sortedHeroes = new HashMap<>();
        for(int i = 0; i < heroes.size(); i++){
            System.out.println("id = " + heroes.get(i).getId() + " HERO = " + heroes.get(i));
            sortedHeroes.put(heroes.get(i).getId(), heroes.get(i));
        }
        return sortedHeroes;
    }
}
