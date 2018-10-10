package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.adapter.RankingAdapter;
import com.example.albert.dotasearch.model.TimeRefreshLeaderBoard;
import com.example.albert.dotasearch.modelfactory.FactoryForLeaderboardViewModel;
import com.example.albert.dotasearch.viewModel.LeaderBoardViewModel;
import com.squareup.leakcanary.RefWatcher;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.widget.LinearLayout.VERTICAL;

public class RankingCountyFragment extends Fragment {
    private static final String TAG = "RankingCountyFragment";
    private static final int LAYOUT = R.layout.leaderboard;
    private String division;
    private String title;
    private LeaderBoardViewModel viewModel;
    private Unbinder unbinder;
    public TimeRefreshLeaderBoard timeRefreshLeaderBoardTemp;
    private RankingAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public RankingCountyFragment() {
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(LAYOUT, container, false);
        Log.d(TAG, "start " + division);

        unbinder = ButterKnife.bind(this, view);

        setRecyclerViewAdapter();

        viewModel = ViewModelProviders.of(this, new FactoryForLeaderboardViewModel(division)).get(LeaderBoardViewModel.class);
        viewModel.getTimeRefreshLeaderBoardLiveData().observe(this, timeRefreshLeaderBoard -> {
            if (timeRefreshLeaderBoard != null && timeRefreshLeaderBoard.getLeaderboard() != null) {
                Log.d(TAG, "onChanged " + timeRefreshLeaderBoard.getLeaderboard().size() + " " + division);
                timeRefreshLeaderBoardTemp = timeRefreshLeaderBoard;
                mAdapter.setData(timeRefreshLeaderBoard);
            } else {
                Log.d(TAG, (timeRefreshLeaderBoard == null) + " ");
            }
        });

        return view;
    }

    public void setRecyclerViewAdapter() {
        mAdapter = new RankingAdapter(getActivity(), Glide.with(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        if (getContext() != null) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop " + getTitle());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume " + getTitle());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(Objects.requireNonNull(getActivity()));
        refWatcher.watch(this);
    }
}