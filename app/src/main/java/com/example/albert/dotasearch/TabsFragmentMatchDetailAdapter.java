package com.example.albert.dotasearch;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.albert.dotasearch.tabs.TabMatchDetail;

import java.util.HashMap;
import java.util.Map;

public class TabsFragmentMatchDetailAdapter extends FragmentPagerAdapter {
    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    public TabsFragmentMatchDetailAdapter(FragmentManager fm, Context context) {
        super(fm);

        this.context = context;

        initTabsMap(context);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabsMap(Context context) {
        tabs = new HashMap<>();
        tabs.put(0, TabMatchDetail.getInstance(context));
        /*tabs.put(1, TabMatchDetailDamage.getInstance(context));
        tabs.put(2, TabMatchDetailFarm.getInstance(context));*/
    }
}
