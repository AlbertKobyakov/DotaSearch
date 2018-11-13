package com.kobyakov.d2s.modelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.kobyakov.d2s.viewModel.PlayerInfoHeroesViewModel;

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
