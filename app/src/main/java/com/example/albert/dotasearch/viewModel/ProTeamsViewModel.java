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
    private LiveData<Integer> statusCode;
    private static final String TAG = ProTeamsViewModel.class.getSimpleName();

    public ProTeamsViewModel() {
        Log.d(TAG, "create");
        repository = new ProTeamRepository();
        teams = repository.getTeams();
        repository.checkValidateTeamsData();
        statusCode = repository.getStatusCode();
    }

    public LiveData<List<Team>> getTeams() {
        return teams;
    }

    public void repeatRequest() {
        repository.getTeamsWithRetrofitAndStoreToDB(true);
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }
}
