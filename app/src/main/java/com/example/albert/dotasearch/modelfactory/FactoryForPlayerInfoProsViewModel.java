package com.example.albert.dotasearch.modelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.albert.dotasearch.viewModel.PlayerInfoMatchesViewModel;
import com.example.albert.dotasearch.viewModel.PlayerInfoProsViewModel;

public class FactoryForPlayerInfoProsViewModel extends ViewModelProvider.NewInstanceFactory {
    private final long accountId;

    public FactoryForPlayerInfoProsViewModel(long accountId) {
        super();
        this.accountId = accountId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PlayerInfoProsViewModel.class) {
            return (T) new PlayerInfoProsViewModel(accountId);
        }
        return null;
    }
}
