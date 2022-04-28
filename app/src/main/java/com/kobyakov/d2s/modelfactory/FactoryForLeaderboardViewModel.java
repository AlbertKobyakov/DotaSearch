package com.kobyakov.d2s.modelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kobyakov.d2s.viewModel.LeaderBoardViewModel;

public class FactoryForLeaderboardViewModel extends ViewModelProvider.NewInstanceFactory {
    private final String division;

    public FactoryForLeaderboardViewModel(String division) {
        super();
        this.division = division;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == LeaderBoardViewModel.class) {
            return (T) new LeaderBoardViewModel(division);
        }
        return null;
    }
}
