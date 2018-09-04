package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.albert.dotasearch.model.ProMatch;
import com.example.albert.dotasearch.model.Team;
import com.example.albert.dotasearch.repository.ProMatchRepository;
import com.example.albert.dotasearch.repository.ProTeamRepository;

import java.util.List;

public class ProMatchesViewModel extends ViewModel {
    private LiveData<List<ProMatch>> proMatches;
    private ProMatchRepository repository;

    public ProMatchesViewModel() {
        repository = new ProMatchRepository();
        proMatches = repository.getProMatches();
    }

    public LiveData<List<ProMatch>> getProMatches() {
        return proMatches;
    }
}
