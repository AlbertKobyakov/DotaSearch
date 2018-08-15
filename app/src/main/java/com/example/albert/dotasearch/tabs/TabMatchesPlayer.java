package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.MatchDetailActivity;
import com.example.albert.dotasearch.adapter.MatchPlayerAdapter;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.model.PlayerOverviewCombine;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class TabMatchesPlayer  extends AbstractTabFragment {

    private static final String TAG = "TabMatchesPlayer";
    private static final int LAYOUT = R.layout.fragment_matches_player;

    private MatchPlayerAdapter mAdapter;
    private List<MatchShortInfo> matchList;
    private Map<Integer, Hero> heroList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    public static TabMatchesPlayer getInstance(Context context) {
        Bundle args = new Bundle();
        TabMatchesPlayer fragment = new TabMatchesPlayer();
        fragment.setArguments(args);
        fragment.setTitle(context.getString(R.string.tab_matches_player));
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*heroList = viewModel.getHeroes();
        System.out.println(heroList);
        LiveData<PlayerOverviewCombine> playerFullInfo = viewModel.getPlayerFullInfo();
        playerFullInfo.observe(getActivity(), playerOverviewCombine -> {
            if(playerOverviewCombine != null){
                List<MatchShortInfo> matchShortInfos = playerOverviewCombine.getMatches();
                matchList = matchShortInfos;
                mAdapter.setData(matchShortInfos, heroList);
            }
        });*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        Log.e(TAG, "onCreateView");

        ButterKnife.bind(this, view);

        setAdapterAndRecyclerView();

        return view;
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new MatchPlayerAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, (view, position) -> {
            toMatchDetailActivity(position);
        }));
    }

    public void toMatchDetailActivity(int position){
        Log.e("position", position + "");
        Intent intent = new Intent(getActivity(), MatchDetailActivity.class);
        intent.putExtra("matchId", matchList.get(position).getMatchId());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.dispose();
    }
}