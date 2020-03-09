package com.kobyakov.d2s.modelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kobyakov.d2s.viewModel.TeamInfoMatchesViewModel;

public class FactoryForTeamInfoMatchesViewModel extends ViewModelProvider.NewInstanceFactory {
    private final long teamId;

    public FactoryForTeamInfoMatchesViewModel(long teamId) {
        super();
        this.teamId = teamId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == TeamInfoMatchesViewModel.class) {
            return (T) new TeamInfoMatchesViewModel(teamId);
        }
        return null;
    }
}
