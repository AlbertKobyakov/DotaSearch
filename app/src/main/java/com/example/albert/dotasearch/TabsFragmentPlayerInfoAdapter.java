package com.example.albert.dotasearch;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Map;

public class TabsFragmentPlayerInfoAdapter extends FragmentPagerAdapter {
    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    public TabsFragmentPlayerInfoAdapter(FragmentManager fm) {
        super(fm);
    }

    /*public TabsFragmentPlayerInfoAdapter(FragmentManager fm, Context context) {
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
        tabs.put(0, TabPlayerMatches.getInstance(context));
        tabs.put(1, TabMatchesPlayer.getInstance(context));
    }*/
}
