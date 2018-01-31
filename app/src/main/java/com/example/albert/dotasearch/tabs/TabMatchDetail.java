package com.example.albert.dotasearch.tabs;

import android.content.Context;
import android.os.Bundle;

import com.example.albert.dotasearch.AbstractTabFragmentMatchDetail;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.adapter.MatchDetailAdapter;

import static com.example.albert.dotasearch.activity.MatchDetailActivity.items;
import static com.example.albert.dotasearch.activity.MatchDetailActivity.matchFullInfo;

public class TabMatchDetail extends AbstractTabFragmentMatchDetail {

    private static final String TAG = "TabMatchDetail";

    public static TabMatchDetail getInstance(Context context) {
        Bundle args = new Bundle();
        TabMatchDetail fragment = new TabMatchDetail();
        fragment.setArguments(args);
        fragment.setContext(context);

        fragment.setTAG(TAG);
        fragment.setPlayers(matchFullInfo.getPlayers());
        fragment.setPlayersRadiant(fragment.getPlayers().subList(0,5));
        fragment.setPlayersDire(fragment.getPlayers().subList(5,10));
        fragment.setmAdapterRadiant(new MatchDetailAdapter(fragment.getPlayersRadiant(), items, context));
        fragment.setmAdapterDire(new MatchDetailAdapter(fragment.getPlayersDire(), items, context));

        fragment.setTitle(context.getString(R.string.tab_match_overview));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
