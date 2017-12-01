package com.example.albert.dotasearch.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;

import butterknife.ButterKnife;

public class TabRanking extends AbstractTabFragment {

    private static final int LAYOUT = R.layout.fragment_ranking;

    public static TabRanking getInstance(Context context) {
        Bundle args = new Bundle();
        TabRanking fragment = new TabRanking();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_ranking));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

}
