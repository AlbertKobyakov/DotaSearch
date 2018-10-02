package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.albert.dotasearch.model.ItemsWithMatchDetail;
import com.example.albert.dotasearch.repository.MatchDetailOverViewRepository;

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