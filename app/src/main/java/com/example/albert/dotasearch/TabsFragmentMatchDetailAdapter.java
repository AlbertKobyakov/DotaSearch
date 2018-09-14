package com.example.albert.dotasearch;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.example.albert.dotasearch.tabs.TabMatchDetail;

public class TabsFragmentMatchDetailAdapter extends FragmentPagerAdapter {

    private SparseArray<AbstractTabFragment> fragmentSparseArray = new SparseArray<>();

    public TabsFragmentMatchDetailAdapter(FragmentManager fm) {
        super(fm);

        initTabsMap();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentSparseArray.get(position);
    }

    @Override
    public int getCount() {
        return fragmentSparseArray.size();
    }

    private void initTabsMap() {
        TabMatchDetail tabMatchDetail = new TabMatchDetail();
        tabMatchDetail.setTitle("Обзор");
        fragmentSparseArray.put(0, tabMatchDetail);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentSparseArray.get(position).getTitle();
    }
}
