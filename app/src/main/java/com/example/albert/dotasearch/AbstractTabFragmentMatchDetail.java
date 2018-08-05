package com.example.albert.dotasearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albert.dotasearch.activity.FoundPlayerActivity;
import com.example.albert.dotasearch.activity.PlayerInfoActivity;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.FoundPlayer;
import com.example.albert.dotasearch.model.Player;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
//import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.albert.dotasearch.activity.MatchDetailActivity.matchFullInfo;

public abstract class AbstractTabFragmentMatchDetail extends Fragment {
    private String title;

    private String TAG;
    private static final int LAYOUT = R.layout.fragment_match_detail;
    private static final int SECONDS_IN_MINUTE = 60;

    private Unbinder unbinder;

    public AppDatabase db;

    private RecyclerView.Adapter mAdapterRadiant;
    private RecyclerView.Adapter mAdapterDire;
    private List<Player> playersDire;
    private List<Player> playersRadiant;
    private List<Player> players;

    @BindView(R.id.recycler_view_radiant) RecyclerView recyclerViewRadiant;
    @BindView(R.id.recycler_view_dire) RecyclerView recyclerViewDire;

    @BindView(R.id.who_win) TextView whoWin;
    @BindView(R.id.game_mode_and_lobby_type) TextView gameModeAndLobbyType;
    @BindView(R.id.score_and_duration) TextView scoreAndDuration;

    protected Context context;
    protected View view;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        db = App.get().getDB();

        view = inflater.inflate(LAYOUT, container, false);

        unbinder = ButterKnife.bind(this, view);

        int idGameMode = (int)matchFullInfo.getGameMode();
        int idLobbyType = (int)matchFullInfo.getLobbyType();
        //int idLobbyType = 8;

        String gameMode = UtilDota.getGameModeById(idGameMode);
        //String lobbyType = UtilDota.getLobbyTypeById(idLobbyType);

        Disposable d1 = db.lobbyTypeDao().getLobbyTypeByIdRx(idLobbyType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lobbyType -> gameModeAndLobbyType.setText(getResources().getString(R.string.game_mode_and_lobby_type, gameMode, lobbyType.getName())
                        ));

        compositeDisposable.add(d1);

        long minutes = matchFullInfo.getDuration()/ SECONDS_IN_MINUTE;
        long seconds = matchFullInfo.getDuration()-(minutes* SECONDS_IN_MINUTE);

        long direScore = matchFullInfo.getDireScore();
        long radiantScore = matchFullInfo.getRadiantScore();

        boolean isRadiantWin = matchFullInfo.isRadiantWin();

        if(isRadiantWin){
            whoWin.setText(R.string.radiant_victory);
        } else {
            whoWin.setText(R.string.dire_victory);
        }

        //gameModeAndLobbyType.setText(getResources().getString(R.string.game_mode_and_lobby_type, gameMode, lobbyType));

        scoreAndDuration.setText(getResources().getString(R.string.score_and_duration, radiantScore, direScore, minutes, seconds));

        setAdapterAndRecyclerView();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.dispose();
        unbinder.unbind();
    }

    public void setAdapterAndRecyclerView() {
        RecyclerView.LayoutManager mLayoutManagerRadiant = new LinearLayoutManager(view.getContext());
        recyclerViewRadiant.setLayoutManager(mLayoutManagerRadiant);
        recyclerViewRadiant.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRadiant.setNestedScrollingEnabled(false);
        recyclerViewRadiant.setAdapter(mAdapterRadiant);
        setItemTouchListener(recyclerViewRadiant, playersRadiant);

        RecyclerView.LayoutManager mLayoutManagerDire = new LinearLayoutManager(view.getContext());
        recyclerViewDire.setLayoutManager(mLayoutManagerDire);
        recyclerViewDire.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDire.setNestedScrollingEnabled(false);
        recyclerViewDire.setAdapter(mAdapterDire);
        setItemTouchListener(recyclerViewDire, playersDire);
    }

    public void setItemTouchListener(RecyclerView recyclerView, List<Player> playerListView){
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, (view, position) -> {
                if(playerListView.get(position).getAccountId() != 0){
                    Intent intent = new Intent(context, PlayerInfoActivity.class);
                    intent.putExtra("accountId", playerListView.get(position).getAccountId());
                    intent.putExtra("personalName", playerListView.get(position).getPersonaname());
                    intent.putExtra("lastMatchStr", playerListView.get(position).getLastLogin());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Snackbar.make(view, R.string.profile_hidden, Snackbar.LENGTH_SHORT).show();
                }
        }));
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /*public RecyclerView.Adapter getmAdapterRadiant() {
        return mAdapterRadiant;
    }*/

    public void setmAdapterRadiant(RecyclerView.Adapter mAdapterRadiant) {
        this.mAdapterRadiant = mAdapterRadiant;
    }

    /*public RecyclerView.Adapter getmAdapterDire() {
        return mAdapterDire;
    }*/

    public void setmAdapterDire(RecyclerView.Adapter mAdapterDire) {
        this.mAdapterDire = mAdapterDire;
    }

    public List<Player> getPlayersDire() {
        return playersDire;
    }

    public void setPlayersDire(List<Player> playersDire) {
        this.playersDire = playersDire;
    }

    public List<Player> getPlayersRadiant() {
        return playersRadiant;
    }

    public void setPlayersRadiant(List<Player> playersRadiant) {
        this.playersRadiant = playersRadiant;
    }
}
