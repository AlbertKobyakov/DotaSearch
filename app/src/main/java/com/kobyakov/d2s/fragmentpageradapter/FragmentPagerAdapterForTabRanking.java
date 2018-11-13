package com.kobyakov.d2s.fragmentpageradapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.kobyakov.d2s.R;
import com.kobyakov.d2s.tabs.RankingCountyFragment;

public class FragmentPagerAdapterForTabRanking extends FragmentPagerAdapter {
    private SparseArray<RankingCountyFragment> fragmentMap = new SparseArray<>();

    public FragmentPagerAdapterForTabRanking(Context context, FragmentManager fm) {
        super(fm);
        RankingCountyFragment rankingChinaFragment = new RankingCountyFragment();
        rankingChinaFragment.setDivision("china");
        rankingChinaFragment.setTitle(context.getString(R.string.china));
        fragmentMap.put(0, rankingChinaFragment);

        RankingCountyFragment rankingAmericanFragment = new RankingCountyFragment();
        rankingAmericanFragment.setDivision("americas");
        rankingAmericanFragment.setTitle(context.getString(R.string.americas));
        fragmentMap.put(1, rankingAmericanFragment);

        RankingCountyFragment rankingAsiaFragment = new RankingCountyFragment();
        rankingAsiaFragment.setDivision("se_asia");
        rankingAsiaFragment.setTitle(context.getString(R.string.se_asia));
        fragmentMap.put(2, rankingAsiaFragment);

        RankingCountyFragment rankingEuropeFragment = new RankingCountyFragment();
        rankingEuropeFragment.setDivision("europe");
        rankingEuropeFragment.setTitle(context.getString(R.string.europe));
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