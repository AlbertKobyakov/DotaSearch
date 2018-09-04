package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.repository.ProPlayerRepository;

import java.util.List;

public class ProPlayersViewModel extends ViewModel {

    private LiveData<List<ProPlayer>> proPlayers;
    private ProPlayerRepository repository;

    public ProPlayersViewModel() {
        repository = new ProPlayerRepository();
        proPlayers = repository.getProPlayersWithDB();
    }

    public LiveData<List<ProPlayer>> getProPlayers() {
        return proPlayers;
    }

    public void addData(List<ProPlayer> proPlayers){
        //repository.addData(proPlayers);
    }
}
