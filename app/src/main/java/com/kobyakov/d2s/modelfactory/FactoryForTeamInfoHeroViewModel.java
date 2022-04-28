package com.kobyakov.d2s.modelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
