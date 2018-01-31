package com.example.albert.dotasearch.tabs;

import android.content.Context;
import android.os.Bundle;

import com.example.albert.dotasearch.AbstractTabFragmentMatchDetail;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.adapter.MatchDetailDamageAdapter;

import static com.example.albert.dotasearch.activity.MatchDetailActivity.items;
import static com.example.albert.dotasearch.activity.MatchDetailActivity.matchFullInfo;

public class TabMatchDetailDamage extends AbstractTabFragmentMatchDetail {

    private static final String TAG = "TabMatchDetailDamage";

    public static TabMatchDetailDamage getInstance(Context context) {
        Bundle args = new Bundle();
        TabMatchDetailDamage fragment = new TabMatchDetailDamage();
        fragment.setArguments(args);
        fragment.setContext(context);

        fragment.setTAG(TAG);
        fragment.setPlayers(matchFullInfo.getPlayers());
        fragment.setPlayersRadiant(fragment.getPlayers().subList(0,5));
        fragment.setPlayersDire(fragment.getPlayers().subList(5,10));
        fragment.setmAdapterRadiant(new MatchDetailDamageAdapter(fragment.getPlayersRadiant(), items, context));
        fragment.setmAdapterDire(new MatchDetailDamageAdapter(fragment.getPlayersDire(), items, context));

        fragment.setTitle(context.getString(R.string.tab_match_detail_damage));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
