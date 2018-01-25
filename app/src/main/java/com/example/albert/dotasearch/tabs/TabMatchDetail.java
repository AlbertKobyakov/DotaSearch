package com.example.albert.dotasearch.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.adapter.MatchDetailAdapter;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.MatchFullInfo;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.albert.dotasearch.activity.MatchDetailActivity.items;
import static com.example.albert.dotasearch.activity.MatchDetailActivity.matchFullInfo;

public class TabMatchDetail extends AbstractTabFragment {

    private static final String TAG = "TabMatchDetail";
    private static final int LAYOUT = R.layout.fragment_match_detail;

    private Unbinder unbinder;
    private MatchDetailAdapter mAdapterRadiant;
    private MatchDetailAdapter mAdapterDire;

    private List<MatchFullInfo.Player> playersDire;
    private List<MatchFullInfo.Player> playersRadiant;


    //@BindView(R.id.text_detail) TextView textView;
    @BindView(R.id.recycler_view_radiant) RecyclerView recyclerViewRadiant;
    @BindView(R.id.recycler_view_dire) RecyclerView recyclerViewDire;

    @BindView(R.id.who_win) TextView whoWin;
    @BindView(R.id.game_mode_and_lobby_type) TextView gameModeAndLobbyType;
    @BindView(R.id.score_and_duration) TextView scoreAndDuration;

    public List<MatchFullInfo.Player> players;

    public static TabMatchDetail getInstance(Context context) {
        Bundle args = new Bundle();
        TabMatchDetail fragment = new TabMatchDetail();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_match_detail));
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

        Log.e(TAG, "onCreateView");

        Toast.makeText(context, matchFullInfo.getMatchId()+"", Toast.LENGTH_SHORT).show();

        Log.d(TAG, matchFullInfo.getPlayers().toString());

        players = matchFullInfo.getPlayers();


        int idGameMode = (int)matchFullInfo.getGameMode();
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

        scoreAndDuration.setText(getResources().getString(R.string.score_and_duration, radiantScore, direScore, minutes, seconds));

        Log.d(TAG, gameMode + " " + lobbyType + " " + minutes + ":" + seconds + " " + radiantScore + " : " + direScore + " Radiant win? " + isRadiantWin);


        /*StringBuilder name = new StringBuilder();

        for(int  i = 0; i < matchFullInfo.getPlayers().size(); i++) {
            name.append(matchFullInfo.getPlayers().get(i).getPersonaname()).append("; ");
        }

        textView.setText(name.append(" size items = ").append(items.size()));*/

        setAdapterAndRecyclerView(matchFullInfo, items, view.getContext());

        return view;
    }

    public void setAdapterAndRecyclerView(MatchFullInfo matchFullInfo, List<Item> items, Context context) {
        playersRadiant = players.subList(0, 5);
        playersDire = players.subList(5, 10);

        mAdapterRadiant = new MatchDetailAdapter(playersRadiant, items, context);
        RecyclerView.LayoutManager mLayoutManagerRadiant = new LinearLayoutManager(view.getContext());
        recyclerViewRadiant.setLayoutManager(mLayoutManagerRadiant);
        recyclerViewRadiant.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRadiant.setNestedScrollingEnabled(false);
        recyclerViewRadiant.setAdapter(mAdapterRadiant);

        mAdapterDire = new MatchDetailAdapter(playersDire, items, context);
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
