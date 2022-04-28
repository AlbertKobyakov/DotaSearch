package com.kobyakov.d2s.tabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.RVEmptyObserver;
import com.kobyakov.d2s.activity.PlayerInfoActivity;
import com.kobyakov.d2s.adapter.PlayerInfoHeroesAdapter;
import com.kobyakov.d2s.model.PlayerHero;
import com.kobyakov.d2s.modelfactory.FactoryForPlayerInfoHeroViewModel;
import com.kobyakov.d2s.util.CheckLoadedData;
import com.kobyakov.d2s.util.ExpandedAppBarListener;
import com.kobyakov.d2s.viewModel.PlayerInfoHeroesViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentPlayerInfoHeroes extends Fragment {
    private static final String TAG = "FragmentPlayerHeroes";
    private static final int LAYOUT = R.layout.fragment_player_overview;
    private static final String ACCOUNT_ID = "account_id";

    private View view;
    private PlayerInfoHeroesAdapter mAdapter;
    private long accountId;
    private List<PlayerHero> playerHeroes;
    private PlayerInfoHeroesViewModel viewModel;
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

    public static FragmentPlayerInfoHeroes newInstance(long accountId) {
        FragmentPlayerInfoHeroes fragmentPlayerInfoHeroes = new FragmentPlayerInfoHeroes();
        Bundle bundle = new Bundle();
        bundle.putLong(ACCOUNT_ID, accountId);
        fragmentPlayerInfoHeroes.setArguments(bundle);

        return fragmentPlayerInfoHeroes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        expandedAppBarListener = (ExpandedAppBarListener) getActivity();

        view = inflater.inflate(LAYOUT, container, false);

        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            accountId = getArguments().getLong(ACCOUNT_ID);
        }

        viewModel = ViewModelProviders.of(this, new FactoryForPlayerInfoHeroViewModel(accountId)).get(PlayerInfoHeroesViewModel.class);

        viewModel.getPlayerHeroes().observe(this, playerHeroesResponse -> {
            if (playerHeroesResponse != null) {
                if (playerHeroesResponse.size() > 0) {
                    playerHeroes = playerHeroesResponse;
                    mAdapter.setData(playerHeroesResponse/*, heroList*/);
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
            if (statusCode != null && playerHeroes == null) {
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new PlayerInfoHeroesAdapter(getActivity(), Glide.with(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.registerAdapterDataObserver(new RVEmptyObserver(recyclerView, progressBar, recyclerView));
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