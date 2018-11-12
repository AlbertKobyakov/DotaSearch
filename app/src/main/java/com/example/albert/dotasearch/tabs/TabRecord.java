package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.MatchDetailActivity;
import com.example.albert.dotasearch.adapter.RecordAdapter;
import com.example.albert.dotasearch.model.Record;
import com.example.albert.dotasearch.modelfactory.FactoryForRecordViewModel;
import com.example.albert.dotasearch.viewModel.RecordViewModel;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
                if(records.size() > 0){
                    Log.d(TAG, records.size() + " = size record");
                    mAdapter.setData(records);
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getStatusCode().observe(this, responseStatusCode -> {
            if(responseStatusCode != null){
                if(responseStatusCode > 200){
                    blockError.setVisibility(View.VISIBLE);
                    networkError.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if(responseStatusCode == -200){
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
        itemSortByDateUp.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mAdapter.sortByDateUp();
                return false;
            }
        });

        MenuItem itemSortByDateDown = menu.findItem(R.id.sort_date_down);
        itemSortByDateDown.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mAdapter.sortByDateDown();
                return false;
            }
        });

        MenuItem itemSortByScoreUp = menu.findItem(R.id.sort_score_up);
        itemSortByScoreUp.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mAdapter.sortByScoreUp();
                return false;
            }
        });

        MenuItem itemSortByScoreDown = menu.findItem(R.id.sort_score_down);
        itemSortByScoreDown.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mAdapter.sortByScoreDown();
                return false;
            }
        });
    }

    public void setRecyclerViewAdapter() {
        mAdapter = new RecordAdapter(getActivity(), titleTab, titleRecord, Glide.with(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        if (getContext() != null) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                long matchId = mAdapter.getMatchIdByPosition(position);
                if (matchId > 0) {
                    goToMatchDetailActivity(matchId);
                } else {
                    Toast.makeText(getContext(), "Нет данных о матче", Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    public void goToMatchDetailActivity(long matchId) {
        Log.e("44444444", "444444444444442");
        Intent intent = new Intent(getActivity(), MatchDetailActivity.class);
        intent.putExtra("matchId", matchId);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(Objects.requireNonNull(getActivity()));
        refWatcher.watch(this);
    }
}