package com.kobyakov.d2s.tabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.kobyakov.d2s.AbstractChartFragment;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.datasetmpandroidchart.HorizontalBarDataSet;
import com.kobyakov.d2s.markerview.HorizontalChartMarker;
import com.kobyakov.d2s.model.ChartData;
import com.kobyakov.d2s.modelfactory.FactoryForMatchDetailChartViewModel;
import com.kobyakov.d2s.viewModel.MatchDetailChartViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MatchDetailHorizontalBarChart extends AbstractChartFragment {
    private static final String TAG = "HorizontalBarChart";
    private static final int LAYOUT = R.layout.horizontal_bar_chart;
    private static final String MATCH_ID = "match_id";
    private static final String TYPE_CHART = "type_chart";
    private Unbinder unbinder;
    private long matchId;
    private String typeChart;
    private MatchDetailChartViewModel viewModel;

    @BindView(R.id.chart_view)
    HorizontalBarChart chart;

    public MatchDetailHorizontalBarChart() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            matchId = savedInstanceState.getLong(MATCH_ID);
            typeChart = savedInstanceState.getString(TYPE_CHART);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(MATCH_ID, matchId);
        outState.putString(TYPE_CHART, typeChart);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        Log.d(TAG, "start");

        unbinder = ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(this, new FactoryForMatchDetailChartViewModel(matchId, typeChart)).get(MatchDetailChartViewModel.class);
        viewModel.getDataForDrawChart().observe(this, dataForDrawChart -> {
            if (dataForDrawChart != null) {
                Log.d(TAG, dataForDrawChart.size() + " = size");
                drawChart(dataForDrawChart);
            }
        });

        return view;
    }

    @Override
    public void setTypeChart(String typeChart) {
        this.typeChart = typeChart;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop " + getTitle());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume " + getTitle());
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    @Override
    public void drawChart(List<ChartData> data) {

        List<BarEntry> entries3 = new ArrayList<>();
        int count = 0;
        for (ChartData value : data) {
            entries3.add(new BarEntry(count++, value.getValue()));
        }

        HorizontalBarDataSet barDataSet = new HorizontalBarDataSet(entries3, "User 1", data);

        if (getActivity() != null) {
            barDataSet.setColors(ContextCompat.getColor(getActivity(), R.color.win),
                    ContextCompat.getColor(getActivity(), R.color.lose));
        }

        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        barData.setValueTextSize(13);

        chart.setData(barData);

        chart.getAxisLeft().setAxisMinimum(0);
        chart.setExtraOffsets(0, 0, 10, 0);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getXAxis().setDrawLabels(false);

        chart.invalidate(); // refresh

        IMarker marker = new HorizontalChartMarker(getActivity(), R.layout.gold_and_xp_marker_layout, data);
        chart.setMarker(marker);
    }
}