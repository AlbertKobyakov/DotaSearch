package com.kobyakov.d2s.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kobyakov.d2s.repository.StartActivityRepository;

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

    public void refresh() {
        repository.dataInitialization();
    }
}
