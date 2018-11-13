package com.kobyakov.d2s.modelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.kobyakov.d2s.viewModel.PlayerInfoProsViewModel;

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
