package com.example.albert.dotasearch.tabs;

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
import android.widget.Toast;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.MatchDetailActivity;
import com.example.albert.dotasearch.adapter.MatchPlayerAdapter;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.albert.dotasearch.activity.StartActivity.db;
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
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_matches_player));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        Log.e(TAG, "onCreateView");

        ButterKnife.bind(this, view);

        Observable<List<MatchShortInfo>> matches = UtilDota.initRetrofitRx()
                .getMatchesPlayerRx(accountId, 20)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<List<Hero>> heroes = db.heroDao().getAllRx()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<GetHeroAndMatch> combine = Observable.zip(matches, heroes, GetHeroAndMatch::new);

        Disposable d1 = combine.subscribe(
                this::setLocalArrays,
                error -> Log.e(TAG, error.getLocalizedMessage()),
                () -> setAdapterAndRecyclerView(matchList, heroList)
        );

        compositeDisposable.add(d1);

        return view;
    }

    public void setLocalArrays(GetHeroAndMatch getHeroAndMatch){
        matchList = getHeroAndMatch.matches;
        heroList = getHeroAndMatch.heroes;
    }

    public void setAdapterAndRecyclerView(List<MatchShortInfo> matches, List<Hero> heroes) {
        mAdapter = new MatchPlayerAdapter(matches, heroes, view.getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                toMatchDetailActivity(position);
            }
        }));
    }

    public void toMatchDetailActivity(int position){
        Intent intent = new Intent(context, MatchDetailActivity.class);
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