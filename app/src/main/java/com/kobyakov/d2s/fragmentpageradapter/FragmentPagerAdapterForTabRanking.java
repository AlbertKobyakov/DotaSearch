package com.kobyakov.d2s.fragmentpageradapter;

import android.content.Context;
import android.util.SparseArray;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kobyakov.d2s.R;
import com.kobyakov.d2s.tabs.RankingCountyFragment;

public class FragmentPagerAdapterForTabRanking extends FragmentPagerAdapter {
    private SparseArray<RankingCountyFragment> fragmentMap = new SparseArray<>();

    public FragmentPagerAdapterForTabRanking(Context context, FragmentManager fm) {
        super(fm);
        RankingCountyFragment rankingChinaFragment = new RankingCountyFragment("china", context.getString(R.string.china));
        fragmentMap.put(0, rankingChinaFragment);

        RankingCountyFragment rankingAmericanFragment = new RankingCountyFragment("americas", context.getString(R.string.americas));
        fragmentMap.put(1, rankingAmericanFragment);

        RankingCountyFragment rankingAsiaFragment = new RankingCountyFragment("se_asia", context.getString(R.string.se_asia));
        fragmentMap.put(2, rankingAsiaFragment);

        RankingCountyFragment rankingEuropeFragment = new RankingCountyFragment("europe", context.getString(R.string.europe));
        fragmentMap.put(3, rankingEuropeFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentMap.get(position);
    }

    @Override
    public int getCount() {
        return fragmentMap.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentMap.get(position).getTitle();
    }
}