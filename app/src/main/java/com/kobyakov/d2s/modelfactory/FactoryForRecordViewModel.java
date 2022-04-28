package com.kobyakov.d2s.modelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
