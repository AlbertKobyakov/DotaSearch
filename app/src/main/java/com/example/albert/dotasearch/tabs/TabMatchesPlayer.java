package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.example.albert.dotasearch.modelfactory.FactoryForPlayerInfoViewModel;
import com.example.albert.dotasearch.viewModel.PlayerInfoViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

import static com.example.albert.dotasearch.activity.PlayerInfoActivity.accountId;

public class TabMatchesPlayer  extends AbstractTabFragment {

    private static final String TAG = "TabMatchesPlayer";
    private static final int LAYOUT = R.layout.fragment_matches_player;

    private MatchPlayerAdapter mAdapter;
    private List<MatchShortInfo> matchList;
    private List<Hero> heroList;
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
        PlayerInfoViewModel viewModel = ViewModelProviders.of(this, new FactoryForPlayerInfoViewModel(accountId)).get(PlayerInfoViewModel.class);
        LiveData<List<MatchShortInfo>> matches = viewModel.getMatches();
        matches.observe(getActivity(), new Observer<List<MatchShortInfo>>() {
            @Override
            public void onChanged(@Nullable List<MatchShortInfo> matchShortInfos) {
                heroList = viewModel.getHeroes();
                matchList = matchShortInfos;

                setAdapterAndRecyclerView(matchList, heroList);
                System.out.println(matchShortInfos);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        Log.e(TAG, "onCreateView");

        ButterKnife.bind(this, view);

        return view;
    }

    public void setLocalArrays(GetHeroAndMatch getHeroAndMatch){
        matchList = getHeroAndMatch.matches;
        heroList = getHeroAndMatch.heroes;
    }

    public void setAdapterAndRecyclerView(List<MatchShortInfo> matches, List<Hero> heroes) {
        mAdapter = new MatchPlayerAdapter(matches, heroes, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                toMatchDetailActivity(position);
            }
        }));
    }

    public void toMatchDetailActivity(int position){
        Intent intent = new Intent(getActivity(), MatchDetailActivity.class);
        intent.putExtra("matchId", matchList.get(position).getMatchId());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.dispose();
    }

    class GetHeroAndMatch {
        List<MatchShortInfo> matches;
        List<Hero> heroes;

        public GetHeroAndMatch(List<MatchShortInfo> matches, List<Hero> heroes) {
            this.matches = matches;
            this.heroes = heroes;
        }
    }
}