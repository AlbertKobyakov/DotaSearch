package com.kobyakov.d2s.markerview;

import android.content.Context;
import android.widget.TextView;

import com.kobyakov.d2s.R;
import com.kobyakov.d2s.model.ChartData;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.List;

public class HorizontalChartMarker extends MarkerView {
    private TextView textView;
    private Context context;
    private List<ChartData> chartData;

    public HorizontalChartMarker(Context context, int layoutResource, List<ChartData> chartData) {
        super(context, layoutResource);
        this.context = context;
        this.chartData = chartData;

        textView = findViewById(R.id.gold);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        long currentValue = (long) e.getY();
        String title = "";
        for (ChartData data : chartData) {

            if (currentValue == data.getValue()) {
                if (data.getTitle() != null) {
                    title = data.getTitle() + " " + data.getValue();
                } else {
                    title = context.getString(R.string.anonymous) + " " + data.getValue();
                }
            }
        }
        textView.setText(title);
        textView.setTextColor(getResources().getColor(R.color.white));
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if (mOffset == null) {
            mOffset = new MPPointF(-getWidth(), -getHeight());
        }

        return mOffset;
    }
}