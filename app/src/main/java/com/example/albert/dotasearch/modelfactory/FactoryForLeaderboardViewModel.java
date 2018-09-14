package com.example.albert.dotasearch.modelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.albert.dotasearch.viewModel.LeaderBoardViewModel;

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
