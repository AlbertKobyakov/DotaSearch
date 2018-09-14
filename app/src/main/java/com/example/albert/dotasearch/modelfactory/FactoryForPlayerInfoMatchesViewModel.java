package com.example.albert.dotasearch.modelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.albert.dotasearch.viewModel.PlayerInfoMatchesViewModel;

public class FactoryForPlayerInfoMatchesViewModel extends ViewModelProvider.NewInstanceFactory {
    private final long accountId;

    public FactoryForPlayerInfoMatchesViewModel(long accountId) {
        super();
        this.accountId = accountId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PlayerInfoMatchesViewModel.class) {
            return (T) new PlayerInfoMatchesViewModel(accountId);
        }
        return null;
    }
}
