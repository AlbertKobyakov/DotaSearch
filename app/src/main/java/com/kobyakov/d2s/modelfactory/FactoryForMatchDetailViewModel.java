package com.kobyakov.d2s.modelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kobyakov.d2s.viewModel.MatchDetailViewModel;

public class FactoryForMatchDetailViewModel extends ViewModelProvider.NewInstanceFactory {
    private final long matchId;

    public FactoryForMatchDetailViewModel(long matchId) {
        super();
        this.matchId = matchId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MatchDetailViewModel.class) {
            return (T) new MatchDetailViewModel(matchId);
        }
        return null;
    }
}
