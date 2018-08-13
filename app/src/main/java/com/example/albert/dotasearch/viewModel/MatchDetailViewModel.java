package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.albert.dotasearch.model.MatchDetailWithItems;
import com.example.albert.dotasearch.repository.MatchDetailRepository;

public class MatchDetailViewModel extends ViewModel {
    private MatchDetailRepository repository;
    private LiveData<MatchDetailWithItems> matchDetail;

    public MatchDetailViewModel(long matchId) {
        repository = new MatchDetailRepository(matchId);
        matchDetail = repository.getMatchDetailWithItemsMutableLiveData();
    }

    public LiveData<MatchDetailWithItems> getMatchDetail() {
        return matchDetail;
    }
}
