package com.kobyakov.d2s.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.kobyakov.d2s.RecyclerTouchListener;
import com.kobyakov.d2s.activity.PlayerInfoActivity;
import com.kobyakov.d2s.adapter.TeamPlayerAdapter;
import com.kobyakov.d2s.model.TeamPlayer;
import com.kobyakov.d2s.modelfactory.FactoryForTeamInfoPlayerViewModel;
import com.kobyakov.d2s.util.ExpandedAppBarListener;
import com.kobyakov.d2s.viewModel.TeamInfoPlayerViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentTeamInfoPlayers extends Fragment {
    private static final String TAG = "FragmentTeamInfoPlayers";
    private static final int LAYOUT = R.layout.fragment_player_overview;
    private static final String ACCOUNT_ID = "account_id";
    private static final String NAME = "name";

    public TeamPlayerAdapter mAdapter;
    public List<TeamPlayer> allPlayers;
    private long accountId;
    private TeamInfoPlayerViewModel viewModel;
    private ExpandedAppBarListener expandedAppBarListener;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.for_empty_recycler_size)
    LinearLayout forEmptyRecyclerSize;
    @BindView(R.id.btn_refresh)
    Button btnRefresh;
    @BindView(R.id.block_error)
    LinearLayout blockError;
    @BindView(R.id.no_internet)
    TextView text_no_internet;
    @BindView(R.id.network_error)
    TextView text_network_error;
    @BindView(R.id.no_data_text)
    TextView text_no_data;
    @BindView(R.id.server_not_response)
    TextView serverNotResponse;

    public static FragmentTeamInfoPlayers newInstance(long accountId) {
        FragmentTeamInfoPlayers fragmentPlayerInfoPros = new FragmentTeamInfoPlayers();
        Bundle bundle = new Bundle();
        bundle.putLong(ACCOUNT_ID, accountId);
        fragmentPlayerInfoPros.setArguments(bundle);

        return fragmentPlayerInfoPros;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        expandedAppBarListener = (ExpandedAppBarListener) getActivity();

        View view = inflater.inflate(LAYOUT, container, false);

        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            accountId = getArguments().getLong(ACCOUNT_ID);
        }

        setAdapterAndRecyclerView();

        viewModel = ViewModelProviders.of(this, new FactoryForTeamInfoPlayerViewModel(accountId)).get(TeamInfoPlayerViewModel.class);

        viewModel.getTeamPlayers().observe(this, teamPlayers -> {
            if (teamPlayers != null) {
                if (teamPlayers.size() > 0) {
                    allPlayers = teamPlayers;
                    mAdapter.setData(teamPlayers);
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

        //text_no_data.setText(getString(R.string.no_games_with_pro));

        viewModel.getStatusCode().observe(this, statusCode -> {
            if (statusCode != null && allPlayers == null) {
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
        mAdapter = new TeamPlayerAdapter(getActivity(), Glide.with(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //mAdapter.registerAdapterDataObserver(new RVEmptyObserver(recyclerView, progressBar, recyclerView));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, (view, position)
                -> toPlayerInfoActivity(position)));
    }

    public void toPlayerInfoActivity(int position) {
        Intent intent = new Intent(getActivity(), PlayerInfoActivity.class);
        intent.putExtra("accountId", allPlayers.get(position).getAccountId());
        intent.putExtra(NAME, allPlayers.get(position).getName());
        startActivity(intent);
    }
}