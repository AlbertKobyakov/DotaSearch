package com.kobyakov.d2s.markerview;

import android.content.Context;
import android.widget.TextView;

import com.kobyakov.d2s.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class GoldAndXPMarker extends MarkerView {
    private TextView text;
    private Context context;

    public GoldAndXPMarker(Context context, int layoutResource) {
        super(context, layoutResource);
        this.context = context;

        text = findViewById(R.id.gold);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        float currentValue = e.getY();
        String typeTeam = e.getY() > 0 ? context.getString(R.string.radiant_title_chart) : context.getString(R.string.dire_title_chart);
        int minute = (int)e.getX();
        int value = e.getY() > 0 ? (int)e.getY() : (int)e.getY() * -1;
        String tooltip = context.getString(R.string.gold_and_xp_marker, typeTeam, minute, value);
        text.setText(tooltip);

        if(currentValue > 0){
            text.setTextColor(getResources().getColor(R.color.win));
        } else {
            text.setTextColor(getResources().getColor(R.color.lose));
        }

        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            mOffset = new MPPointF(-getWidth(), -getHeight());
        }

        return mOffset ;
    }
}