package com.kobyakov.d2s.tabs;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.adapter.RankingAdapter;
import com.kobyakov.d2s.model.TimeRefreshLeaderBoard;
import com.kobyakov.d2s.modelfactory.FactoryForLeaderboardViewModel;
import com.kobyakov.d2s.viewModel.LeaderBoardViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    @BindView(R.id.for_empty_recycler_size)
    LinearLayout forEmptyRecyclerSize;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.block_error)
    LinearLayout blockError;
    @BindView(R.id.no_internet)
    TextView noInternet;
    @BindView(R.id.network_error)
    TextView networkError;

    public RankingCountyFragment(String division, String title) {
        this.division = division;
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

        unbinder = ButterKnife.bind(this, view);

        setRecyclerViewAdapter();

        viewModel = ViewModelProviders.of(this, new FactoryForLeaderboardViewModel(division)).get(LeaderBoardViewModel.class);
        viewModel.getTimeRefreshLeaderBoardLiveData().observe(this, timeRefreshLeaderBoard -> {
            if (timeRefreshLeaderBoard != null && timeRefreshLeaderBoard.getLeaderboard() != null) {
                if (timeRefreshLeaderBoard.getLeaderboard().size() > 0) {
                    timeRefreshLeaderBoardTemp = timeRefreshLeaderBoard;
                    mAdapter.setData(timeRefreshLeaderBoard);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.getStatusCode().observe(this, responseStatusCode -> {
            if (responseStatusCode != null) {
                if (responseStatusCode > 200) {
                    blockError.setVisibility(View.VISIBLE);
                    networkError.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if (responseStatusCode == -200) {
                    blockError.setVisibility(View.VISIBLE);
                    noInternet.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    @OnClick(R.id.btn_refresh)
    public void refresh() {
        viewModel.repeatRequest();
        blockError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void setRecyclerViewAdapter() {
        mAdapter = new RankingAdapter(getActivity(), Glide.with(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.number_row_in_line_ranking));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        if (getContext() != null) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), HORIZONTAL));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.search_by_name));
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
}