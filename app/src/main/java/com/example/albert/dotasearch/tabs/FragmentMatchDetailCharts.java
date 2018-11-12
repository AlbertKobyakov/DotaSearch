package com.example.albert.dotasearch.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.fragmentpageradapter.FragmentPagerAdapterMatchCharts;
import com.squareup.leakcanary.RefWatcher;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentMatchDetailCharts extends Fragment {
    private final static String TAG = "FrMatchDetailCharts";
    private static final int LAYOUT = R.layout.fragment_for_tabs;
    private static final String MATCH_ID = "match_id";

    private long matchId;

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private View view;

    private Unbinder unbinder;

    public static FragmentMatchDetailCharts newInstance(long matchId) {
        FragmentMatchDetailCharts fragmentMatchDetailCharts = new FragmentMatchDetailCharts();
        Bundle bundle = new Bundle();
        bundle.putLong(MATCH_ID, matchId);
        fragmentMatchDetailCharts.setArguments(bundle);

        return fragmentMatchDetailCharts;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            matchId = getArguments().getLong(MATCH_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        view = inflater.inflate(LAYOUT, container, false);

        unbinder = ButterKnife.bind(this, view);

        //setToolbarTitle();

        initTabLayoutAndViewPager();

        return view;
    }

    /*private void setToolbarTitle() {
        if (getActivity() != null && getActivity().findViewById(R.id.toolbar) != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.top_100_players);
        }
    }*/

    public void initTabLayoutAndViewPager() {
        if (getActivity() != null) {
            FragmentPagerAdapterMatchCharts adapter = new FragmentPagerAdapterMatchCharts(getActivity(), getChildFragmentManager(), matchId);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(Objects.requireNonNull(getActivity()));
        refWatcher.watch(this);
    }
}