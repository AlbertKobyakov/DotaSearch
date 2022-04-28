package com.kobyakov.d2s.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.RecyclerTouchListener;
import com.kobyakov.d2s.activity.MatchDetailActivity;
import com.kobyakov.d2s.adapter.RecordAdapter;
import com.kobyakov.d2s.modelfactory.FactoryForRecordViewModel;
import com.kobyakov.d2s.viewModel.RecordViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;

public class TabRecord extends Fragment {
    private static final String TAG = "TabRecord";
    private static final String TITLE_RECORD = "title_record";
    private static final String TITLE_TAB = "title_tab";

    private static final int LAYOUT = R.layout.leaderboard;
    private String titleRecord;
    private String titleTab;
    private RecordViewModel viewModel;
    private Unbinder unbinder;
    private RecordAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.block_error)
    LinearLayout blockError;
    @BindView(R.id.no_internet)
    TextView noInternet;
    @BindView(R.id.network_error)
    TextView networkError;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public static TabRecord newInstance(String titleRecord, String titleTab) {
        TabRecord tabRecord = new TabRecord();

        //for getPageTitle in FragmentPagerAdapterForTabRecords
        tabRecord.setTitleTab(titleTab);

        Bundle bundle = new Bundle();
        bundle.putString(TITLE_RECORD, titleRecord);
        bundle.putString(TITLE_TAB, titleTab);
        tabRecord.setArguments(bundle);

        return tabRecord;
    }

    public void setTitleTab(String titleTab) {
        this.titleTab = titleTab;
    }

    public String getTitleTab() {
        return titleTab;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            titleRecord = getArguments().getString(TITLE_RECORD);
        }
        if (getArguments() != null) {
            titleTab = getArguments().getString(TITLE_TAB);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(LAYOUT, container, false);
        Log.d(TAG, "start");

        unbinder = ButterKnife.bind(this, view);

        setRecyclerViewAdapter();

        viewModel = ViewModelProviders.of(this, new FactoryForRecordViewModel(titleRecord)).get(RecordViewModel.class);
        viewModel.getRecordListLiveData().observe(this, records -> {
            if (records != null) {
                if (records.size() > 0) {
                    Log.d(TAG, records.size() + " = size record");
                    mAdapter.setData(records);
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sort_for_record, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem itemSortByDateUp = menu.findItem(R.id.sort_date_up);
        itemSortByDateUp.setOnMenuItemClickListener(menuItem -> {
            mAdapter.sortByDateUp();
            return false;
        });

        MenuItem itemSortByDateDown = menu.findItem(R.id.sort_date_down);
        itemSortByDateDown.setOnMenuItemClickListener(menuItem -> {
            mAdapter.sortByDateDown();
            return false;
        });

        MenuItem itemSortByScoreUp = menu.findItem(R.id.sort_score_up);
        itemSortByScoreUp.setOnMenuItemClickListener(menuItem -> {
            mAdapter.sortByScoreUp();
            return false;
        });

        MenuItem itemSortByScoreDown = menu.findItem(R.id.sort_score_down);
        itemSortByScoreDown.setOnMenuItemClickListener(menuItem -> {
            mAdapter.sortByScoreDown();
            return false;
        });
    }

    public void setRecyclerViewAdapter() {
        mAdapter = new RecordAdapter(getActivity(), titleTab, titleRecord, Glide.with(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.number_row_in_line_record));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        if (getContext() != null) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), HORIZONTAL));
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, (view, position) -> {
            long matchId = mAdapter.getMatchIdByPosition(position);
            if (matchId > 0) {
                goToMatchDetailActivity(matchId);
            } else {
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(R.id.bottom_navigation_view), R.string.no_data_about_the_game, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }

    public void goToMatchDetailActivity(long matchId) {
        Intent intent = new Intent(getActivity(), MatchDetailActivity.class);
        intent.putExtra("matchId", matchId);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}