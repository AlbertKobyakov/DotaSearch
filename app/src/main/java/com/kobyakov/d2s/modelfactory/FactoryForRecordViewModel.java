package com.kobyakov.d2s.modelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.kobyakov.d2s.viewModel.RecordViewModel;

public class FactoryForRecordViewModel extends ViewModelProvider.NewInstanceFactory {
    private final String titleRecord;

    public FactoryForRecordViewModel(String titleRecord) {
        super();
        this.titleRecord = titleRecord;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == RecordViewModel.class) {
            return (T) new RecordViewModel(titleRecord);
        }
        return null;
    }
}
