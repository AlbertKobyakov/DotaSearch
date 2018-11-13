package com.kobyakov.d2s.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.kobyakov.d2s.model.ProPlayer;
import com.kobyakov.d2s.repository.ProPlayerRepository;

import java.util.List;

public class ProPlayersViewModel extends ViewModel {

    private LiveData<List<ProPlayer>> proPlayers;
    private LiveData<Integer> statusCode;
    private ProPlayerRepository repository;

    public ProPlayersViewModel() {
        repository = new ProPlayerRepository();
        proPlayers = repository.getProPlayersWithDB();
        repository.checkValidateProPlayersData();
        statusCode = repository.getStatusCode();
        Log.d("ProPlayersViewModel", "create");
    }

    public LiveData<List<ProPlayer>> getProPlayers() {
        return proPlayers;
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public void repeatRequest(){
        repository.checkValidateProPlayersData();
    }

}
