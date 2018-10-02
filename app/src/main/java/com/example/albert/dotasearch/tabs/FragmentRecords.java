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

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.fragmentpageradapter.FragmentPagerAdapterForTabRecords;
import com.example.albert.dotasearch.R;
import com.squareup.leakcanary.RefWatcher;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentRecords extends Fragment {
    private static final String TAG = "FragmentRecords";
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
            toolbar.setTitle(R.string.fragment_records);
        }
    }

    public void initTabLayoutAndViewPager() {
        if (getActivity() != null) {
            FragmentPagerAdapterForTabRecords adapter = new FragmentPagerAdapterForTabRecords(getActivity(), getChildFragmentManager());
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
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(Objects.requireNonNull(getActivity()));
        refWatcher.watch(this);
    }
}

