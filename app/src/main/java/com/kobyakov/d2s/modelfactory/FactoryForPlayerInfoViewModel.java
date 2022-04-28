package com.kobyakov.d2s.modelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
