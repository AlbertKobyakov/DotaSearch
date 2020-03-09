package com.kobyakov.d2s.modelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
