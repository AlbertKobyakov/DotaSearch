package com.kobyakov.d2s.fragmentpageradapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.kobyakov.d2s.R;
import com.kobyakov.d2s.tabs.TabRecord;

public class FragmentPagerAdapterForTabRecords extends FragmentPagerAdapter {
    private SparseArray<TabRecord> fragmentMap = new SparseArray<>();

    public FragmentPagerAdapterForTabRecords(Context context, FragmentManager fm) {
        super(fm);

        TabRecord tabRecordDuration = TabRecord.newInstance("duration", context.getString(R.string.duration_title));
        fragmentMap.put(0, tabRecordDuration);

        TabRecord tabRecordLastHits = TabRecord.newInstance("last_hits", context.getString(R.string.last_hits));
        fragmentMap.put(1, tabRecordLastHits);

        TabRecord tabRecordGPM = TabRecord.newInstance("gold_per_min", context.getString(R.string.gold_per_min_title));
        fragmentMap.put(2, tabRecordGPM);

        TabRecord tabRecordXPM = TabRecord.newInstance("xp_per_min", context.getString(R.string.xp_per_min_title));
        fragmentMap.put(3, tabRecordXPM);

        TabRecord tabRecordKills = TabRecord.newInstance("kills", context.getString(R.string.kills_title));
        fragmentMap.put(4, tabRecordKills);

        TabRecord tabRecordDeaths = TabRecord.newInstance("deaths", context.getString(R.string.deaths_title));
        fragmentMap.put(5, tabRecordDeaths);

        TabRecord tabRecordAssists = TabRecord.newInstance("assists", context.getString(R.string.assists_title));
        fragmentMap.put(6, tabRecordAssists);

        TabRecord tabRecordDenies = TabRecord.newInstance("denies", context.getString(R.string.denies_title));
        fragmentMap.put(7, tabRecordDenies);

        TabRecord tabRecordHeroDamage = TabRecord.newInstance("hero_damage", context.getString(R.string.hero_damage_title));
        fragmentMap.put(8, tabRecordHeroDamage);

        TabRecord tabRecordTowerDamage = TabRecord.newInstance("tower_damage", context.getString(R.string.tower_damage_title));
        fragmentMap.put(9, tabRecordTowerDamage);

        TabRecord tabRecordHeroHealing = TabRecord.newInstance("hero_healing", context.getString(R.string.hero_healing_title));
        fragmentMap.put(10, tabRecordHeroHealing);
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
        return fragmentMap.get(position).getTitleTab();
    }
}
