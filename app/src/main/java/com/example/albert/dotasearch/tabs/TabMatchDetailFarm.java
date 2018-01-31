package com.example.albert.dotasearch.tabs;

import android.content.Context;
import android.os.Bundle;

import com.example.albert.dotasearch.AbstractTabFragmentMatchDetail;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.adapter.MatchDetailFarmAdapter;

import static com.example.albert.dotasearch.activity.MatchDetailActivity.items;
import static com.example.albert.dotasearch.activity.MatchDetailActivity.matchFullInfo;

public class TabMatchDetailFarm extends AbstractTabFragmentMatchDetail {

    private static final String TAG = "TabMatchDetailFarm";

    public static TabMatchDetailFarm getInstance(Context context) {
        Bundle args = new Bundle();
        TabMatchDetailFarm fragment = new TabMatchDetailFarm();
        fragment.setArguments(args);
        fragment.setContext(context);

        fragment.setTAG(TAG);
        fragment.setPlayers(matchFullInfo.getPlayers());
        fragment.setPlayersRadiant(fragment.getPlayers().subList(0,5));
        fragment.setPlayersDire(fragment.getPlayers().subList(5,10));
        fragment.setmAdapterRadiant(new MatchDetailFarmAdapter(fragment.getPlayersRadiant(), items, context));
        fragment.setmAdapterDire(new MatchDetailFarmAdapter(fragment.getPlayersDire(), items, context));

        fragment.setTitle(context.getString(R.string.tab_match_detail_farm));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
