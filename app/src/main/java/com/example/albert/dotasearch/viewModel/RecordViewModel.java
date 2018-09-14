package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.albert.dotasearch.model.Record;
import com.example.albert.dotasearch.repository.RecordRepository;

import java.util.List;

public class RecordViewModel extends ViewModel {
    private LiveData<List<Record>> recordListLiveData;
    private RecordRepository repository;

    public RecordViewModel(String titleRecord) {
        repository = new RecordRepository(titleRecord);

        recordListLiveData = repository.getRecordListLiveData();
    }

    public LiveData<List<Record>> getRecordListLiveData() {
        return recordListLiveData;
    }
}
