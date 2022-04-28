package com.kobyakov.d2s.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kobyakov.d2s.model.ItemsWithMatchDetail;
import com.kobyakov.d2s.repository.MatchDetailOverViewRepository;

public class MatchDetailOverviewViewModel extends ViewModel {
    private MatchDetailOverViewRepository repository;
    private static final String TAG = MatchDetailOverviewViewModel.class.getSimpleName();
    private LiveData<ItemsWithMatchDetail> itemsWithMatchDetailLiveData;

    public MatchDetailOverviewViewModel(long matchId) {
        Log.d(TAG, "create");
        repository = new MatchDetailOverViewRepository(matchId);
        //mediatorLiveData = repository.getMediatorLiveData();
        itemsWithMatchDetailLiveData = repository.getItemsWithMatchDetailMutableLiveData();
    }

    public LiveData<ItemsWithMatchDetail> getItemsWithMatchDetailLiveData() {
        return itemsWithMatchDetailLiveData;
    }
}