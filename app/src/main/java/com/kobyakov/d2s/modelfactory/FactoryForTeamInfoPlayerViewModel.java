package com.kobyakov.d2s.modelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kobyakov.d2s.viewModel.TeamInfoPlayerViewModel;

public class FactoryForTeamInfoPlayerViewModel extends ViewModelProvider.NewInstanceFactory {
    private final long accountId;

    public FactoryForTeamInfoPlayerViewModel(long accountId) {
        super();
        this.accountId = accountId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == TeamInfoPlayerViewModel.class) {
            return (T) new TeamInfoPlayerViewModel(accountId);
        }
        return null;
    }
}
