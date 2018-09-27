package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.Observer;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.PlayerInfoActivity;
import com.example.albert.dotasearch.adapter.PlayerProsAdapter;
import com.example.albert.dotasearch.model.Pros;
import com.example.albert.dotasearch.modelfactory.FactoryForPlayerInfoProsViewModel;
import com.example.albert.dotasearch.viewModel.PlayerInfoProsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentPlayerInfoPros extends Fragment {
    private static final String TAG = "FragmentPlayerInfoPros";
    private static final int LAYOUT = R.layout.fragment_player_overview;
    private static final String ACCOUNT_ID = "account_id";

    public PlayerProsAdapter mAdapter;
    public List<Pros> allPros;
    private long accountId;
    private PlayerInfoProsViewModel viewModel;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.for_empty_recycler_size)
    TextView forEmptyRecyclerSize;
    @BindView(R.id.btn_refresh)
    Button btnRefresh;
    @BindView(R.id.block_error)
    LinearLayout blockError;
    @BindView(R.id.no_internet)
    TextView text_no_internet;
    @BindView(R.id.network_error)
    TextView text_network_error;

    public static FragmentPlayerInfoPros newInstance(long accountId) {
        FragmentPlayerInfoPros fragmentPlayerInfoPros = new FragmentPlayerInfoPros();
        Bundle bundle = new Bundle();
        bundle.putLong(ACCOUNT_ID, accountId);
        fragmentPlayerInfoPros.setArguments(bundle);

        return fragmentPlayerInfoPros;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        View view = inflater.inflate(LAYOUT, container, false);

        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            accountId = getArguments().getLong(ACCOUNT_ID);
        }

        setAdapterAndRecyclerView();

        viewModel = ViewModelProviders.of(this, new FactoryForPlayerInfoProsViewModel(accountId)).get(PlayerInfoProsViewModel.class);

        viewModel.getPros().observe(this, new Observer<List<Pros>>() {
            @Override
            public void onChanged(@Nullable List<Pros> pros) {
                if (pros != null) {
                    if (pros.size() > 0/* && allPros == null*/) {
                        Log.d(TAG, pros.size() + "");
                        allPros = pros;
                        mAdapter.setData(pros);
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
            if (statusCode != null && allPros == null) {
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

        return view;
    }

    @OnClick(R.id.btn_refresh)
    public void refresh() {
        viewModel.repeatedRequest();
        progressBar.setVisibility(View.VISIBLE);
        blockError.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new PlayerProsAdapter(getActivity(), Glide.with(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //mAdapter.registerAdapterDataObserver(new RVEmptyObserver(recyclerView, progressBar, recyclerView));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                toPlayerInfoActivity(position);
            }
        }));
    }

    public void toPlayerInfoActivity(int position) {
        Intent intent = new Intent(getActivity(), PlayerInfoActivity.class);
        intent.putExtra("accountId", allPros.get(position).getAccountId());
        intent.putExtra("personalName", allPros.get(position).getName());
        intent.putExtra("urlPlayer", allPros.get(position).getAvatarmedium());
        startActivity(intent);
    }
}