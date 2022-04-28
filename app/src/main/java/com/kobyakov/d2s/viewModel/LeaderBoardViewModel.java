package com.kobyakov.d2s.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kobyakov.d2s.model.TimeRefreshLeaderBoard;
import com.kobyakov.d2s.repository.LeaderboardRepository;

public class LeaderBoardViewModel extends ViewModel {
    private LiveData<TimeRefreshLeaderBoard> timeRefreshLeaderBoardLiveData;
    private LeaderboardRepository repository;
    private LiveData<Integer> statusCode;
    String division;

    public LeaderBoardViewModel(String division) {
        repository = new LeaderboardRepository(division);
        repository.checkValidLeaderBoardData(division);
        timeRefreshLeaderBoardLiveData = repository.getTimeRefreshLeaderBoardLiveData();
        statusCode = repository.getStatusCode();
        this.division = division;
    }

    public LiveData<TimeRefreshLeaderBoard> getTimeRefreshLeaderBoardLiveData() {
        return timeRefreshLeaderBoardLiveData;
    }

    public LiveData<Integer> getStatusCode() {
        return statusCode;
    }

    public void repeatRequest() {
        repository.checkValidLeaderBoardData(division);
    }
}
