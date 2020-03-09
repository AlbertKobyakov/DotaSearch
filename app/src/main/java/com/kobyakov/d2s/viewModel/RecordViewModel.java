package com.kobyakov.d2s.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kobyakov.d2s.model.Record;
import com.kobyakov.d2s.repository.RecordRepository;

import java.util.List;

public class RecordViewModel extends ViewModel {
    private LiveData<List<Record>> recordListLiveData;
    private RecordRepository repository;
    private LiveData<Integer> statusCode;

    public RecordViewModel(String titleRecord) {
        repository = new RecordRepository(titleRecord);
        recordListLiveData = repository.getRecordListLiveData();
        statusCode = repository.getStatusCode();
    }

    public LiveData<List<Record>> getRecordListLiveData() {
        return recordListLiveData;
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public void repeatRequest() {
        repository.sendRequest();
    }
}
