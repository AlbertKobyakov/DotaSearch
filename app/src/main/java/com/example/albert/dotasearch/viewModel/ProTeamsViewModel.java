package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.albert.dotasearch.model.Team;
import com.example.albert.dotasearch.repository.ProTeamRepository;

import java.util.List;

public class ProTeamsViewModel extends ViewModel {
    private LiveData<List<Team>> teams;
    private ProTeamRepository repository;

    public ProTeamsViewModel() {
        repository = new ProTeamRepository();
        teams = repository.getProTeam();
        Log.d("viewmodel", "create");
    }

    public LiveData<List<Team>> getTeams() {
        return teams;
    }
}
