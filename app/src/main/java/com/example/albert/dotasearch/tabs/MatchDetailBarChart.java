package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albert.dotasearch.AbstractChartFragment;
import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.datasetmpandroidchart.GoldAndXpBarDataSet;
import com.example.albert.dotasearch.markerview.GoldAndXPMarker;
import com.example.albert.dotasearch.model.ChartData;
import com.example.albert.dotasearch.modelfactory.FactoryForMatchDetailChartViewModel;
import com.example.albert.dotasearch.viewModel.MatchDetailChartViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MatchDetailBarChart extends AbstractChartFragment {
    private static final String TAG = "MatchDetailBarChart";
    private static final int LAYOUT = R.layout.bar_chart;
    private static final String MATCH_ID = "match_id";
    private static final String TYPE_CHART = "type_chart";
    private Unbinder unbinder;
    private long matchId;
    private String typeChart;
    private MatchDetailChartViewModel viewModel;

    @BindView(R.id.chart_view)
    BarChart chart;
    @BindView(R.id.no_data_for_chart)
    TextView noData;
    @BindView(R.id.emotion_sad)
    ImageView emotionSad;

    public MatchDetailBarChart() {
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
            } else {
                chart.setVisibility(View.GONE);
                emotionSad.setVisibility(View.VISIBLE);
                noData.setVisibility(View.VISIBLE);
                Log.d(TAG, "Данные для постраения графика отсутствуют :( ");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(Objects.requireNonNull(getActivity()));
        refWatcher.watch(this);
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    @Override
    public void drawChart(List<ChartData> data) {
        List<BarEntry> entries = new ArrayList<>();
        int count = 0;
        for (ChartData value : data) {

            entries.add(new BarEntry(count++, value.getValue()));
        }

        GoldAndXpBarDataSet barDataSet = new GoldAndXpBarDataSet(entries, "");

        if (getActivity() != null) {
            barDataSet.setColors(ContextCompat.getColor(getActivity(), R.color.win),
                    ContextCompat.getColor(getActivity(), R.color.lose));
        }

        barDataSet.setDrawValues(false);

        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        barData.setDrawValues(false);

        chart.setData(barData);
        chart.setTouchEnabled(true);
        chart.getDescription().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setNoDataText("");
        chart.setDescription(null);
        chart.setExtraOffsets(0, 16, 0, 0);
        chart.invalidate(); // refresh

        IMarker marker = new GoldAndXPMarker(getActivity(), R.layout.gold_and_xp_marker_layout);
        chart.setMarker(marker);
    }
}