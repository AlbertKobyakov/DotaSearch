package com.example.albert.dotasearch.datasetmpandroidchart;

import com.example.albert.dotasearch.model.ChartData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

public class HorizontalBarDataSet extends BarDataSet {
    private List<ChartData> chartData;

    public HorizontalBarDataSet(List<BarEntry> yVals, String label, List<ChartData> chartData) {
        super(yVals, label);
        this.chartData = chartData;
    }

    @Override
    public int getColor(int index) {
        int color = mColors.get(0);
        for (ChartData data : chartData) {
            if (data.getValue() == (long) getEntryForIndex(index).getY()) {
                if (data.isRadiant()) {
                    color = mColors.get(0);
                } else {
                    color = mColors.get(1);
                }
            }
        }
        return color;
    }
}