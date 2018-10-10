package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.ClickListener;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.activity.PlayerInfoActivity;
import com.example.albert.dotasearch.adapter.MatchDetailAdapter;
import com.example.albert.dotasearch.model.ItemsWithMatchDetail;
import com.example.albert.dotasearch.model.MatchFullInfo;
import com.example.albert.dotasearch.model.Player;
import com.example.albert.dotasearch.modelfactory.FactoryForMatchDetailOverviewViewModel;
import com.example.albert.dotasearch.util.UtilDota;
import com.example.albert.dotasearch.viewModel.MatchDetailOverviewViewModel;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentMatchDetailOverview extends Fragment {

    private static final String TAG = "FrMatchDetailOver";
    private static final int LAYOUT = R.layout.fragment_match_detail;
    private static final int TOTAL_PLAYERS_IN_MATCH = 10;
    private static final String MATCH_ID = "match_id";

    private MatchDetailAdapter mAdapter;
    private List<Player> players;
    private MatchDetailOverviewViewModel viewModel;
    private Unbinder unbinder;
    private View view;
    private long matchId;

    @BindView(R.id.recycler_view_radiant)
    RecyclerView recyclerView;
    @BindView(R.id.who_win)
    TextView whoWin;
    @BindView(R.id.game_mode_and_lobby_type)
    TextView gameModeAndLobbyType;

    @BindView(R.id.radiant_score)
    TextView radiantScore;
    @BindView(R.id.dire_score)
    TextView direScore;
    @BindView(R.id.match_duration)
    TextView matchDuration;
    @BindView(R.id.league)
    TextView league;
    @BindView(R.id.date_start)
    TextView dateStart;

    @BindView(R.id.root_layout)
    ScrollView rootLayout;
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;
    @BindView(R.id.block_short_info_match)
    CardView blockShortInfoMatch;
    @BindView(R.id.block_general_content)
    RelativeLayout blockGeneralContent;

    public static FragmentMatchDetailOverview newInstance(long matchId) {
        FragmentMatchDetailOverview fragmentMatchDetailOverview = new FragmentMatchDetailOverview();
        Bundle bundle = new Bundle();
        bundle.putLong(MATCH_ID, matchId);
        fragmentMatchDetailOverview.setArguments(bundle);

        return fragmentMatchDetailOverview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        if (getArguments() != null) {
            matchId = getArguments().getLong(MATCH_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        Log.e(TAG, "onCreateView " + matchId);

        unbinder = ButterKnife.bind(this, view);

        setAdapterAndRecyclerView();

        viewModel = ViewModelProviders.of(this, new FactoryForMatchDetailOverviewViewModel(matchId)).get(MatchDetailOverviewViewModel.class);

        viewModel.getItemsWithMatchDetailLiveData().observe(this, itemsWithMatchDetail -> {
            if (itemsWithMatchDetail != null) {
                Log.d(TAG, itemsWithMatchDetail.getItems().size() + " ");
                showData(itemsWithMatchDetail);
                mAdapter.setData(itemsWithMatchDetail);
            }
        });

        return view;
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new MatchDetailAdapter(getContext(), Glide.with(this), new ClickListener() {
            @Override
            public void onPositionClicked(int position, boolean isGoToPlayerActivity) {
                Log.d(TAG, "with fragment position = " + position + " " + isGoToPlayerActivity);
                if (players.get(position).getAccountId() == 0 && isGoToPlayerActivity) {
                    Snackbar.make(view, R.string.profile_hidden, Snackbar.LENGTH_SHORT).show();
                } else if (players.get(position).getAccountId() != 0 && isGoToPlayerActivity) {
                    Log.e("44444444", "444444444444441");
                    Intent intent = new Intent(getActivity(), PlayerInfoActivity.class);
                    intent.putExtra("accountId", players.get(position).getAccountId());
                    intent.putExtra("personalName", players.get(position).getPersonaname());
                    intent.putExtra("lastMatchStr", players.get(position).getLastLogin());
                    startActivity(intent);
                }
            }
        });

        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getActivity(), R.drawable.divider)));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void showData(ItemsWithMatchDetail itemsWithMatchDetail) {
        blockGeneralContent.setVisibility(View.VISIBLE);
        MatchFullInfo matchFullInfo = itemsWithMatchDetail.getMatchFullInfo();
        List<Player> players = matchFullInfo.getPlayers();
        if (players.size() == TOTAL_PLAYERS_IN_MATCH) {
            rootLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            this.players = matchFullInfo.getPlayers();

            String gameMode = UtilDota.getGameModeById(matchFullInfo.getGameMode());
            String lobbyType = UtilDota.getLobbyTypeById(matchFullInfo.getLobbyType());
            gameModeAndLobbyType.setText(getResources().getString(R.string.game_mod_and_lobby_type, gameMode, lobbyType));

            if (matchFullInfo.isRadiantWin()) {
                if (matchFullInfo.getRadiantTeam() != null) {
                    whoWin.setText(getResources().getString(R.string.team_victory, matchFullInfo.getRadiantTeam().getName()));
                } else {
                    whoWin.setText(getResources().getString(R.string.radiant_victory));
                }
            } else {
                if (matchFullInfo.getDireTeam() != null) {
                    whoWin.setText(getResources().getString(R.string.team_victory, matchFullInfo.getDireTeam().getName()));
                } else {
                    whoWin.setText(getResources().getString(R.string.dire_victory));
                }
            }

            if (matchFullInfo.getLeague() != null && matchFullInfo.getLeague().getName() != null) {
                league.setText(matchFullInfo.getLeague().getName());
                league.setVisibility(View.VISIBLE);
            }

            radiantScore.setText(getResources().getString(R.string.radiant_score, matchFullInfo.getRadiantScore()));
            direScore.setText(getResources().getString(R.string.dire_score, matchFullInfo.getDireScore()));

            long secondsDuration = matchFullInfo.getDuration();
            String duration = DateUtils.formatElapsedTime(secondsDuration);
            matchDuration.setText(getResources().getString(R.string.match_duration, duration));

            long secondsDateStart = matchFullInfo.getStartTime();
            //long secondsDateEnd = secondsDateStart + secondsDuration;
            String dateMatchStart = DateUtils.getRelativeTimeSpanString(secondsDateStart * 1000).toString();
            dateStart.setText(dateMatchStart);
        } else {
            rootLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
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