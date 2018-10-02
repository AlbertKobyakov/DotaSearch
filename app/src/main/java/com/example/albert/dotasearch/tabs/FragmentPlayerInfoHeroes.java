package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RVEmptyObserver;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.adapter.PlayerInfoHeroesAdapter;
import com.example.albert.dotasearch.model.PlayerHero;
import com.example.albert.dotasearch.modelfactory.FactoryForPlayerInfoHeroViewModel;
import com.example.albert.dotasearch.viewModel.PlayerInfoHeroesViewModel;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentPlayerInfoHeroes extends Fragment {
    private static final String TAG = "FragmentPlayerHeroes";
    private static final int LAYOUT = R.layout.fragment_player_overview;
    private static final String ACCOUNT_ID = "account_id";

    private View view;
    private PlayerInfoHeroesAdapter mAdapter;
    private long accountId;
    private List<PlayerHero> playerHeroes;
    private PlayerInfoHeroesViewModel viewModel;

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
    TextView forEmptyRecyclerSize;

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

        view = inflater.inflate(LAYOUT, container, false);

        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            accountId = getArguments().getLong(ACCOUNT_ID);
        }

        viewModel = ViewModelProviders.of(this, new FactoryForPlayerInfoHeroViewModel(accountId)).get(PlayerInfoHeroesViewModel.class);

        viewModel.getPlayerHeroes().observe(this, new Observer<List<PlayerHero>>() {
            @Override
            public void onChanged(@Nullable List<PlayerHero> playerHeroesResponse) {
                if (playerHeroesResponse != null) {
                    if (playerHeroesResponse.size() > 0) {
                        playerHeroes = playerHeroesResponse;
                        mAdapter.setData(playerHeroesResponse/*, heroList*/);
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        forEmptyRecyclerSize.setVisibility(View.VISIBLE);
                    }
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
                }
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

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getContext(), mAdapter.getPlayerHeroByPosition(position).getHeroId() + "", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @OnClick(R.id.btn_refresh)
    public void refresh() {
        viewModel.repeatedRequest();
        progressBar.setVisibility(View.VISIBLE);
        blockError.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(Objects.requireNonNull(getActivity()));
        refWatcher.watch(this);
    }
}