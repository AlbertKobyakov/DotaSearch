package com.kobyakov.d2s.modelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

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
