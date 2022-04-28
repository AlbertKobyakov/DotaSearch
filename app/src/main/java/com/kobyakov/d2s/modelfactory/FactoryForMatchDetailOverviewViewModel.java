package com.kobyakov.d2s.modelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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