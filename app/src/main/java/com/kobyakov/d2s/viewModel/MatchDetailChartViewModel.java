package com.kobyakov.d2s.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kobyakov.d2s.model.ChartData;
import com.kobyakov.d2s.repository.MatchDetailChartRepository;

import java.util.List;

public class MatchDetailChartViewModel extends ViewModel {
    private static final String TAG = "MatchDetailChartViewMod";
    private MatchDetailChartRepository repository;
    private LiveData<List<ChartData>> dataForDrawChart;

    public MatchDetailChartViewModel(long matchId, String typeChart) {
        Log.d(TAG, "MatchDetailChartViewModel constructor");
        repository = new MatchDetailChartRepository(matchId);
        dataForDrawChart = repository.getDataForDrawChart(typeChart);
    }

    public LiveData<List<ChartData>> getDataForDrawChart() {
        return dataForDrawChart;
    }
}
