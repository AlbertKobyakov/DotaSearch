package com.example.albert.dotasearch.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.albert.dotasearch.model.ChartData;
import com.example.albert.dotasearch.repository.MatchDetailChartRepository;

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
