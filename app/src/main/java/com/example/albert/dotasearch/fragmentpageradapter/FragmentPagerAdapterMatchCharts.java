package com.example.albert.dotasearch.fragmentpageradapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.example.albert.dotasearch.AbstractChartFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.tabs.MatchDetailBarChart;
import com.example.albert.dotasearch.tabs.MatchDetailHorizontalBarChart;

public class FragmentPagerAdapterMatchCharts extends FragmentPagerAdapter {

    private SparseArray<AbstractChartFragment> fragmentMap = new SparseArray<>();
    private long matchId;

    public FragmentPagerAdapterMatchCharts(Context context, FragmentManager fm, long matchId) {
        super(fm);
        this.matchId = matchId;

        MatchDetailBarChart matchDetailChart = new MatchDetailBarChart();
        matchDetailChart.setTitle(context.getString(R.string.gold_title));
        matchDetailChart.setMatchId(matchId);
        matchDetailChart.setTypeChart("gold");
        fragmentMap.put(0, matchDetailChart);

        MatchDetailBarChart matchDetailChart1 = new MatchDetailBarChart();
        matchDetailChart1.setTitle(context.getString(R.string.exp_title));
        matchDetailChart1.setMatchId(matchId);
        matchDetailChart1.setTypeChart("exp");
        fragmentMap.put(1, matchDetailChart1);

        MatchDetailHorizontalBarChart matchDetailHorizontalBarChartGpm = new MatchDetailHorizontalBarChart();
        matchDetailHorizontalBarChartGpm.setTitle(context.getString(R.string.gold_per_min));
        matchDetailHorizontalBarChartGpm.setMatchId(matchId);
        matchDetailHorizontalBarChartGpm.setTypeChart("gpm");
        fragmentMap.put(2, matchDetailHorizontalBarChartGpm);

        MatchDetailHorizontalBarChart matchDetailHorizontalBarChartXpm = new MatchDetailHorizontalBarChart();
        matchDetailHorizontalBarChartXpm.setTitle(context.getString(R.string.xp_per_min));
        matchDetailHorizontalBarChartXpm.setMatchId(matchId);
        matchDetailHorizontalBarChartXpm.setTypeChart("xpm");
        fragmentMap.put(3, matchDetailHorizontalBarChartXpm);

        MatchDetailHorizontalBarChart matchDetailHorizontalBarChartTotalDamage = new MatchDetailHorizontalBarChart();
        matchDetailHorizontalBarChartTotalDamage.setTitle(context.getString(R.string.hero_damage_title));
        matchDetailHorizontalBarChartTotalDamage.setMatchId(matchId);
        matchDetailHorizontalBarChartTotalDamage.setTypeChart("total damage");
        fragmentMap.put(4, matchDetailHorizontalBarChartTotalDamage);

        MatchDetailHorizontalBarChart matchDetailHorizontalBarChartTotalGold = new MatchDetailHorizontalBarChart();
        matchDetailHorizontalBarChartTotalGold.setTitle(context.getString(R.string.total_gold_title));
        matchDetailHorizontalBarChartTotalGold.setMatchId(matchId);
        matchDetailHorizontalBarChartTotalGold.setTypeChart("total gold");
        fragmentMap.put(5, matchDetailHorizontalBarChartTotalGold);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentMap.get(position);
    }

    @Override
    public int getCount() {
        return fragmentMap.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentMap.get(position).getTitle();
    }
}