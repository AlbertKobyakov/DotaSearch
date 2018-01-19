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

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.adapter.MatchPlayerAdapter;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Match;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.albert.dotasearch.activity.StartActivity.db;
import static com.example.albert.dotasearch.activity.PlayerInfoActivity.accountId;

public class TabMatchesPlayer  extends AbstractTabFragment {

    private static final String FRAGMENT_NAME = "TabMatchesPlayer";
    private static final int LAYOUT = R.layout.fragment_matches_player;

    //public static AppDatabase db;

    public static List<Match> matches = new ArrayList<>();
    private MatchPlayerAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

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

        Log.e(FRAGMENT_NAME, "onCreateView");

        ButterKnife.bind(this, view);

        //fix console warning, skip recycle adapter
        setAdapterAndRecyclerView(new ArrayList<>());

        Single<List<Match>> matches = UtilDota.initRetrofitRx(/*"https://api.opendota.com"*/)
                //.create(DotaClient.class)
                .getMatchesPlayerRx(accountId, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
                /*.subscribe(this::setAdapterAndRecyclerView);*/

        Single<List<Hero>> heroes = db.heroDao().getAllRx()
                //.toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
                /*.subscribe(System.out::println);*/

        Single<GetHeroAndMatch> combine = Single.zip(matches, heroes, GetHeroAndMatch::new);

        combine.subscribe(new SingleObserver<GetHeroAndMatch>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(GetHeroAndMatch getHeroAndMatch) {
                List<Hero> heroList = getHeroAndMatch.heroes;

                List<Match> matchList = getHeroAndMatch.matches;

                Log.e("Hero",heroList.toString());
                Log.e("Pro",matchList.toString());

                for(Match match : matchList){
                    for(Hero hero : heroList){
                        if(match.getHeroId() == hero.getId()){
                            match.setHeroName(hero.getLocalizedName());
                            match.setIconUrl(hero.getIcon());
                            match.setImgUrl(hero.getImg());
                            break;
                        }
                    }
                }
                setAdapterAndRecyclerView(matchList);
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        return view;
    }

    public void setAdapterAndRecyclerView(List<Match> matches) {
        mAdapter = new MatchPlayerAdapter(matches, view.getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    class GetHeroAndMatch {
       List<Match> matches;
        List<Hero> heroes;

        public GetHeroAndMatch(List<Match> matches, List<Hero> heroes) {
            this.matches = matches;
            this.heroes = heroes;
        }
    }

}