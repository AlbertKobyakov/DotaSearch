package com.kobyakov.d2s.modelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.kobyakov.d2s.viewModel.PlayerInfoHeroesViewModel;
import com.kobyakov.d2s.viewModel.TeamInfoHeroesViewModel;

public class FactoryForTeamInfoHeroViewModel extends ViewModelProvider.NewInstanceFactory {
    private final long accountId;

    public FactoryForTeamInfoHeroViewModel(long accountId) {
        super();
        this.accountId = accountId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == TeamInfoHeroesViewModel.class) {
            return (T) new TeamInfoHeroesViewModel(accountId);
        }
        return null;
    }
}
