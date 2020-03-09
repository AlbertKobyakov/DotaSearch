package com.kobyakov.d2s.modelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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
