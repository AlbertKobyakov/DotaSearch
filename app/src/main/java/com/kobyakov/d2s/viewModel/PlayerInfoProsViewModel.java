package com.kobyakov.d2s.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.kobyakov.d2s.model.Pros;
import com.kobyakov.d2s.repository.PlayerInfoProsRepository;

import java.util.List;

public class PlayerInfoProsViewModel extends ViewModel {
    private static final String TAG = "PlayerInfoProsViewModel";

    private PlayerInfoProsRepository repository;
    private long accountId;
    private LiveData<List<Pros>> pros;
    private LiveData<Integer> statusCode;

    public PlayerInfoProsViewModel(long accountId) {
        this.accountId = accountId;
        Log.d(TAG, "constructor");
        repository = new PlayerInfoProsRepository(accountId);
        pros = repository.getPros();
        statusCode = repository.getStatusCode();
    }

    public LiveData<List<Pros>> getPros() {
        return pros;
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public void repeatedRequest() {
        repository.sendRequest();
    }
}