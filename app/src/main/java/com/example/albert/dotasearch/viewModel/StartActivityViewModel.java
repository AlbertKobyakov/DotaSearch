package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.albert.dotasearch.repository.StartActivityRepository;

public class StartActivityViewModel extends ViewModel {
    private static final String TAG = "StartActivityViewModel";

    private StartActivityRepository repository;
    private LiveData<Boolean> isLoadMainActivity;

    public StartActivityViewModel() {
        repository = new StartActivityRepository();
        repository.dataInitialization();
        isLoadMainActivity = repository.getIsDataSuccess();
    }

    public LiveData<Boolean> getIsLoadMainActivity() {
        return isLoadMainActivity;
    }
}
