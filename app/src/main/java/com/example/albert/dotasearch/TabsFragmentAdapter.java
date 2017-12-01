package com.example.albert.dotasearch;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.albert.dotasearch.tabs.TabProPlayers;
import com.example.albert.dotasearch.tabs.TabRanking;
import com.example.albert.dotasearch.tabs.TabSearch;

import java.util.HashMap;
import java.util.Map;

public class TabsFragmentAdapter extends FragmentPagerAdapter {
    private Map<Integer, AbstractTabFragment> tabs;
    private Context context;

    public TabsFragmentAdapter(FragmentManager fm, Context context) {
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
        tabs.put(0, TabSearch.getInstance(context));
        tabs.put(1, TabProPlayers.getInstance(context));
        tabs.put(2, TabRanking.getInstance(context));
        /*tabs.put(1, SecondPage.getInstance(context));
        tabs.put(2, ThirdPage.getInstance(context));
        tabs.put(3, FourthPage.getInstance(context));*/
    }
}
