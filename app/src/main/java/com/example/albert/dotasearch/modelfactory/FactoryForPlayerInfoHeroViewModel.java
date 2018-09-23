package com.example.albert.dotasearch.modelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.albert.dotasearch.viewModel.PlayerInfoHeroesViewModel;
import com.example.albert.dotasearch.viewModel.PlayerInfoProsViewModel;

public class FactoryForPlayerInfoHeroViewModel extends ViewModelProvider.NewInstanceFactory {
    private final long accountId;

    public FactoryForPlayerInfoHeroViewModel(long accountId) {
        super();
        this.accountId = accountId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PlayerInfoHeroesViewModel.class) {
            return (T) new PlayerInfoHeroesViewModel(accountId);
        }
        return null;
    }
}
