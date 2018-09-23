package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.MatchDetailActivity;
import com.example.albert.dotasearch.activity.PlayerInfoActivity;
import com.example.albert.dotasearch.adapter.MatchDetailAdapter;
import com.example.albert.dotasearch.model.MatchDetailWithItems;
import com.example.albert.dotasearch.model.MatchFullInfo;
import com.example.albert.dotasearch.model.Player;
import com.example.albert.dotasearch.modelfactory.FactoryForMatchDetailViewModel;
import com.example.albert.dotasearch.util.UtilDota;
import com.example.albert.dotasearch.viewModel.MatchDetailViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TabMatchDetail extends AbstractTabFragment {

    private static final String TAG = "TabMatchDetail";
    private static final int LAYOUT = R.layout.fragment_match_detail;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int TOTAL_PLAYERS_IN_MATCH = 10;

    private MatchDetailAdapter mAdapterRadiant;
    private MatchDetailAdapter mAdapterDire;
    private List<Player> playersDire;
    private List<Player> playersRadiant;
    private MatchDetailViewModel viewModel;
    private Unbinder unbinder;
    private View view;

    @BindView(R.id.recycler_view_radiant)
    RecyclerView recyclerViewRadiant;
    @BindView(R.id.recycler_view_dire)
    RecyclerView recyclerViewDire;
    @BindView(R.id.who_win)
    TextView whoWin;
    @BindView(R.id.game_mode_and_lobby_type)
    TextView gameModeAndLobbyType;
    @BindView(R.id.text_dire)
    TextView textDire;
    @BindView(R.id.text_radiant)
    TextView textRadiant;

    @BindView(R.id.radiant_score)
    TextView radiantScore;
    @BindView(R.id.dire_score)
    TextView direScore;
    @BindView(R.id.match_duration)
    TextView matchDuration;

    @BindView(R.id.root_layout)
    ScrollView rootLayout;
    @BindView(R.id.empty_layout)
    RelativeLayout emptyLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        Log.e(TAG, "onCreateView");

        unbinder = ButterKnife.bind(this, view);

        setAdapterAndRecyclerView();

        return view;
    }

    public void setAdapterAndRecyclerView() {
        mAdapterRadiant = new MatchDetailAdapter(getContext(), Glide.with(this));
        recyclerViewRadiant.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewRadiant.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRadiant.setAdapter(mAdapterRadiant);
        setItemTouchListenerRadiant(recyclerViewRadiant);
        recyclerViewRadiant.setNestedScrollingEnabled(false);

        mAdapterDire = new MatchDetailAdapter(getContext(), Glide.with(this));
        recyclerViewDire.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDire.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDire.setAdapter(mAdapterDire);
        setItemTouchListenerDire(recyclerViewDire);
        recyclerViewDire.setNestedScrollingEnabled(false);
    }

    public void setItemTouchListenerDire(RecyclerView recyclerView) {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, (view, position) -> {
            if (playersDire.get(position).getAccountId() != 0) {
                Log.e("44444444", "444444444444441");
                Intent intent = new Intent(getActivity(), PlayerInfoActivity.class);
                intent.putExtra("accountId", playersDire.get(position).getAccountId());
                intent.putExtra("personalName", playersDire.get(position).getPersonaname());
                intent.putExtra("lastMatchStr", playersDire.get(position).getLastLogin());
                startActivity(intent);
            } else {
                Snackbar.make(view, R.string.profile_hidden, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }

    public void setItemTouchListenerRadiant(RecyclerView recyclerView) {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, (view, position) -> {
            if (playersRadiant.get(position).getAccountId() != 0) {
                Log.e("44444444", "444444444444441");
                Intent intent = new Intent(getActivity(), PlayerInfoActivity.class);
                intent.putExtra("accountId", playersRadiant.get(position).getAccountId());
                intent.putExtra("personalName", playersRadiant.get(position).getPersonaname());
                intent.putExtra("lastMatchStr", playersRadiant.get(position).getLastLogin());
                startActivity(intent);
            } else {
                Snackbar.make(view, R.string.profile_hidden, Snackbar.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(TAG + MatchDetailActivity.matchId);

        viewModel = ViewModelProviders.of(this, new FactoryForMatchDetailViewModel(MatchDetailActivity.matchId)).get(MatchDetailViewModel.class);
        LiveData<MatchDetailWithItems> matchDetail = viewModel.getMatchDetail();
        matchDetail.observe(this, new Observer<MatchDetailWithItems>() {
            @Override
            public void onChanged(@Nullable MatchDetailWithItems matchDetailWithItems) {
                if (matchDetailWithItems != null) {
                    showData(matchDetailWithItems);
                }
            }
        });
    }

    private void showData(MatchDetailWithItems matchDetailWithItems) {
        MatchFullInfo matchFullInfo = matchDetailWithItems.getMatchFullInfo();
        List<Player> players = matchFullInfo.getPlayers();
        if (players.size() == TOTAL_PLAYERS_IN_MATCH) {
            rootLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            playersDire = matchFullInfo.getPlayers().subList(5, 10);
            playersRadiant = matchFullInfo.getPlayers().subList(0, 5);
            mAdapterRadiant.setData(playersRadiant, matchDetailWithItems.getItems());
            mAdapterDire.setData(playersDire, matchDetailWithItems.getItems());

            String gameMode = UtilDota.getGameModeById(matchFullInfo.getGameMode());
            String lobbyType = UtilDota.getLobbyTypeById(matchFullInfo.getLobbyType());
            gameModeAndLobbyType.setText(getResources().getString(R.string.game_mod_and_lobby_type, gameMode, lobbyType));

            if (matchFullInfo.isRadiantWin()) {
                whoWin.setText(getResources().getString(R.string.radiant_victory));
                textRadiant.setTextColor(getResources().getColor(R.color.win));
                textDire.setTextColor(getResources().getColor(R.color.lose));
            } else {
                whoWin.setText(getResources().getString(R.string.dire_victory));
                textRadiant.setTextColor(getResources().getColor(R.color.lose));
                textDire.setTextColor(getResources().getColor(R.color.win));
            }

            long minutes = matchFullInfo.getDuration() / SECONDS_IN_MINUTE;
            long seconds = matchFullInfo.getDuration() - (minutes * SECONDS_IN_MINUTE);
            radiantScore.setText(getResources().getString(R.string.radiant_score, matchFullInfo.getRadiantScore()));
            direScore.setText(getResources().getString(R.string.dire_score, matchFullInfo.getDireScore()));
            matchDuration.setText(getResources().getString(R.string.match_duration, minutes + " " + seconds));
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
}