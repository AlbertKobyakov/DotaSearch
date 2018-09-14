package com.example.albert.dotasearch.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.fragmentpageradapter.FragmentPagerAdapterForTabRanking;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TabRanking extends Fragment {

    private final static String TAG = "TabRanking";
    private static final int LAYOUT = R.layout.fragment_for_tabs;

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private View view;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        view = inflater.inflate(LAYOUT, container, false);

        unbinder = ButterKnife.bind(this, view);

        setToolbarTitle();

        initTabLayoutAndViewPager();

        return view;
    }

    private void setToolbarTitle() {
        if (getActivity() != null && getActivity().findViewById(R.id.toolbar) != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.top_100_players);
        }
    }

    public void initTabLayoutAndViewPager() {
        if (getActivity() != null) {
            FragmentPagerAdapterForTabRanking adapter = new FragmentPagerAdapterForTabRanking(getActivity(), getChildFragmentManager());
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
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
        Log.d(TAG, "onDestroy");
    }
}
