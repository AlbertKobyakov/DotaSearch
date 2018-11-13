package com.kobyakov.d2s.modelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.kobyakov.d2s.viewModel.MatchDetailOverviewViewModel;

public class FactoryForMatchDetailOverviewViewModel extends ViewModelProvider.NewInstanceFactory {
    private final long matchId;

    public FactoryForMatchDetailOverviewViewModel(long matchId) {
        super();
        this.matchId = matchId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MatchDetailOverviewViewModel.class) {
            return (T) new MatchDetailOverviewViewModel(matchId);
        }
        return null;
    }
}