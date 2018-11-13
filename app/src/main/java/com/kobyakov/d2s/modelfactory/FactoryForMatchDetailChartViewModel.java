package com.kobyakov.d2s.modelfactory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.kobyakov.d2s.viewModel.MatchDetailChartViewModel;

public class FactoryForMatchDetailChartViewModel extends ViewModelProvider.NewInstanceFactory {
    private final long matchId;
    private final String typeChart;

    public FactoryForMatchDetailChartViewModel(long matchId, String typeChart) {
        super();
        this.matchId = matchId;
        this.typeChart = typeChart;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MatchDetailChartViewModel.class) {
            return (T) new MatchDetailChartViewModel(matchId, typeChart);
        }
        return null;
    }
}
