package com.kobyakov.d2s.modelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.kobyakov.d2s.viewModel.PlayerInfoViewModel;

public class FactoryForPlayerInfoViewModel extends ViewModelProvider.NewInstanceFactory {
    private final long id;

    public FactoryForPlayerInfoViewModel(long id) {
        super();
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PlayerInfoViewModel.class) {
            return (T) new PlayerInfoViewModel(id);
        }
        return null;
    }
}
