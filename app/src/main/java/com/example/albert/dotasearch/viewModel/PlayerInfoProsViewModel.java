package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.albert.dotasearch.model.Pros;
import com.example.albert.dotasearch.repository.PlayerInfoProsRepository;

import java.util.List;

public class PlayerInfoProsViewModel extends ViewModel {
    private static final String TAG = "PlayerInfoProsViewModel";

    private PlayerInfoProsRepository repository;
    private long accountId;
    private LiveData<List<Pros>> pros;
    private LiveData<String> errorMessage;

    public PlayerInfoProsViewModel(long accountId) {
        Log.d(TAG, "constructor");
        repository = new PlayerInfoProsRepository(accountId);
        this.accountId = accountId;
        pros = repository.getPros();
        errorMessage = repository.getErrorMessage();
    }

    public LiveData<List<Pros>> getPros() {
        return pros;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void repeatedRequest() {
        repository.repeatedRequest();
    }
}