package com.kobyakov.d2s;

import android.support.v4.app.Fragment;

import com.kobyakov.d2s.model.ChartData;

import java.util.List;

public abstract class AbstractChartFragment extends Fragment {

    private String title;
    private String typeChart;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract void setTypeChart(String typeChart);

    public String getTypeChart() {
        return typeChart;
    }

    public abstract void drawChart(List<ChartData> data);
}
