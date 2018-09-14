package com.example.albert.dotasearch.modelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.albert.dotasearch.viewModel.RecordViewModel;

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
