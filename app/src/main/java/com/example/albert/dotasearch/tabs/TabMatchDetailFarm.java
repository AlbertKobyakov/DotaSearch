package com.example.albert.dotasearch.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.adapter.MatchDetailFarmAdapter;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.MatchFullInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.albert.dotasearch.activity.MatchDetailActivity.items;
import static com.example.albert.dotasearch.activity.MatchDetailActivity.matchFullInfo;

public class TabMatchDetailFarm extends AbstractTabFragment {

    private static final String TAG = "TabMatchDetailFarm";
    private static final int LAYOUT = R.layout.fragment_match_detail_farm;

    private Unbinder unbinder;
    private MatchDetailFarmAdapter mAdapterRadiant;
    private MatchDetailFarmAdapter mAdapterDire;

    private List<MatchFullInfo.Player> playersDire;
    private List<MatchFullInfo.Player> playersRadiant;

    @BindView(R.id.recycler_view_radiant) RecyclerView recyclerViewRadiant;
    @BindView(R.id.recycler_view_dire) RecyclerView recyclerViewDire;

    /*@BindView(R.id.who_win) TextView whoWin;
    @BindView(R.id.game_mode_and_lobby_type) TextView gameModeAndLobbyType;
    @BindView(R.id.score_and_duration) TextView scoreAndDuration;*/

    public List<MatchFullInfo.Player> players;

    public static TabMatchDetailFarm getInstance(Context context) {
        Bundle args = new Bundle();
        TabMatchDetailFarm fragment = new TabMatchDetailFarm();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_match_detail_farm));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        unbinder = ButterKnife.bind(this, view);

        Toast.makeText(context, TAG, Toast.LENGTH_SHORT).show();

        players = matchFullInfo.getPlayers();

        /*int idGameMode = (int)matchFullInfo.getGameMode();
        int idLobbyType = (int)matchFullInfo.getLobbyType();

        String gameMode = UtilDota.getGameModeById(idGameMode);
        String lobbyType = UtilDota.getLobbyTypeById(idLobbyType);

        long minutes = matchFullInfo.getDuration()/60;
        long seconds = matchFullInfo.getDuration()-(minutes*60);

        long direScore = matchFullInfo.getDireScore();
        long radiantScore = matchFullInfo.getRadiantScore();

        boolean isRadiantWin = matchFullInfo.isRadiantWin();

        if(isRadiantWin){
            whoWin.setText(R.string.radiant_victory);
        } else {
            whoWin.setText(R.string.dire_victory);
        }

        gameModeAndLobbyType.setText(getResources().getString(R.string.game_mode_and_lobby_type, gameMode, lobbyType));

        scoreAndDuration.setText(getResources().getString(R.string.score_and_duration, radiantScore, direScore, minutes, seconds));*/

        setAdapterAndRecyclerView(items, view.getContext());

        return view;
    }

    public void setAdapterAndRecyclerView(List<Item> items, Context context) {
        playersRadiant = players.subList(0, 5);
        playersDire = players.subList(5, 10);

        mAdapterRadiant = new MatchDetailFarmAdapter(playersRadiant, items, context);
        RecyclerView.LayoutManager mLayoutManagerRadiant = new LinearLayoutManager(view.getContext());
        recyclerViewRadiant.setLayoutManager(mLayoutManagerRadiant);
        recyclerViewRadiant.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRadiant.setNestedScrollingEnabled(false);
        recyclerViewRadiant.setAdapter(mAdapterRadiant);

        mAdapterDire = new MatchDetailFarmAdapter(playersDire, items, context);
        RecyclerView.LayoutManager mLayoutManagerDire = new LinearLayoutManager(view.getContext());
        recyclerViewDire.setLayoutManager(mLayoutManagerDire);
        recyclerViewDire.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDire.setNestedScrollingEnabled(false);
        recyclerViewDire.setAdapter(mAdapterDire);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
