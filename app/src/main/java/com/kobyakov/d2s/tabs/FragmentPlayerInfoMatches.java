package com.kobyakov.d2s.tabs;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.RVEmptyObserver;
import com.kobyakov.d2s.RecyclerTouchListener;
import com.kobyakov.d2s.activity.MatchDetailActivity;
import com.kobyakov.d2s.activity.PlayerInfoActivity;
import com.kobyakov.d2s.adapter.PlayerInfoMatchesAdapter;
import com.kobyakov.d2s.model.MatchShortInfo;
import com.kobyakov.d2s.modelfactory.FactoryForPlayerInfoMatchesViewModel;
import com.kobyakov.d2s.util.CheckLoadedData;
import com.kobyakov.d2s.util.ExpandedAppBarListener;
import com.kobyakov.d2s.viewModel.PlayerInfoMatchesViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentPlayerInfoMatches extends Fragment {

    private static final String TAG = "FrPlayerInfoMatches";
    private static final int LAYOUT = R.layout.fragment_player_overview;
    private static final String ACCOUNT_ID = "account_id";

    private PlayerInfoMatchesAdapter mAdapter;
    private List<MatchShortInfo> matchList;
    private PlayerInfoMatchesViewModel viewModel;
    private long accountId;
    private Unbinder unbinder;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.block_error)
    LinearLayout blockError;
    @BindView(R.id.no_internet)
    TextView text_no_internet;
    @BindView(R.id.network_error)
    TextView text_network_error;
    @BindView(R.id.for_empty_recycler_size)
    LinearLayout forEmptyRecyclerSize;
    @BindView(R.id.server_not_response)
    TextView serverNotResponse;

    private ExpandedAppBarListener expandedAppBarListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate");
    }

    public static FragmentPlayerInfoMatches newInstance(long accountId) {
        FragmentPlayerInfoMatches fragmentPlayerInfoMatches = new FragmentPlayerInfoMatches();
        Bundle bundle = new Bundle();
        bundle.putLong(ACCOUNT_ID, accountId);
        fragmentPlayerInfoMatches.setArguments(bundle);

        return fragmentPlayerInfoMatches;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        expandedAppBarListener = (ExpandedAppBarListener) getActivity();

        View view = inflater.inflate(LAYOUT, container, false);

        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            accountId = getArguments().getLong(ACCOUNT_ID);
        }

        Log.d(TAG, accountId + " idididididView");

        viewModel = ViewModelProviders.of(this, new FactoryForPlayerInfoMatchesViewModel(accountId)).get(PlayerInfoMatchesViewModel.class);
        viewModel.getMatches().observe(this, matchShortInfos -> {
            Log.d(TAG, "onChanged");
            if (matchShortInfos != null) {
                if (matchShortInfos.size() > 0) {
                    matchList = matchShortInfos;
                    mAdapter.setData(matchList/*, heroList*/);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    //expandedAppBarListener.onExpandAppBar(true);
                } else {
                    progressBar.setVisibility(View.GONE);
                    forEmptyRecyclerSize.setVisibility(View.VISIBLE);
                    expandedAppBarListener.onExpandAppBar(false);
                }
            }
        });

        viewModel.getStatusCode().observe(this, statusCode -> {
            if (statusCode != null && matchList == null) {
                Log.d(TAG, statusCode + "");
                int fistNumberStatusCode = statusCode / 100;
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);

                if (fistNumberStatusCode > 2) {
                    blockError.setVisibility(View.VISIBLE);
                    text_network_error.setVisibility(View.VISIBLE);
                } else if (fistNumberStatusCode == -2) {
                    blockError.setVisibility(View.VISIBLE);
                    text_no_internet.setVisibility(View.VISIBLE);
                } else if (fistNumberStatusCode == -3) {
                    blockError.setVisibility(View.VISIBLE);
                    serverNotResponse.setVisibility(View.VISIBLE);
                }
                expandedAppBarListener.onExpandAppBar(false);
            }
        });

        setAdapterAndRecyclerView();

        return view;
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new PlayerInfoMatchesAdapter(getActivity(), Glide.with(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.registerAdapterDataObserver(new RVEmptyObserver(recyclerView, progressBar, recyclerView));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, (view, position) -> toMatchDetailActivity(position)));
    }

    public void toMatchDetailActivity(int position) {
        Log.e("44444444", "444444444444442");
        Intent intent = new Intent(getActivity(), MatchDetailActivity.class);
        intent.putExtra("matchId", matchList.get(position).getMatchId());
        startActivity(intent);
    }

    @OnClick(R.id.btn_refresh)
    public void refresh() {

        PlayerInfoActivity activity = (PlayerInfoActivity) getActivity();
        if (activity != null && !activity.isLoadPlayerOverviewCombine) {
            CheckLoadedData checkLoadedData = ((CheckLoadedData) getActivity());
            checkLoadedData.repeat();
        }

        viewModel.repeatedRequest();
        progressBar.setVisibility(View.VISIBLE);
        blockError.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}