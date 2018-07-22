package com.example.albert.dotasearch.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.activity.RankingActivity;
import com.example.albert.dotasearch.model.Leaderboard;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TabRanking extends AbstractTabFragment {

    private final static String TAG = "TabRanking";
    private static final int LAYOUT = R.layout.fragment_ranking;

    @BindView(R.id.textTestLeaderBoard) TextView textView;
    @BindView(R.id.radioGroup) RadioGroup radioGroup;
    @BindView(R.id.btn_ranked) Button btn_ranked;

    private Unbinder unbinder;
    private String division;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static TabRanking getInstance(Context context) {
        Bundle args = new Bundle();
        TabRanking fragment = new TabRanking();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_ranking));
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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioBtnAmerica){
                    division = "americas";
                } else if (checkedId == R.id.radioBtnAsia){
                    division = "se_asia";
                } else if (checkedId == R.id.radioBtnChina){
                    division = "china";
                } else if (checkedId == R.id.radioBtnEurope){
                    division = "europe";
                }
            }
        });

        return view;
    }

    @OnClick(R.id.btn_ranked)
    public void onClick(View view){
        if(division == null){
            Snackbar.make(view, "Выберите регион", Snackbar.LENGTH_LONG).show();
        } else {
            getLeaderBoardApiAndStoreToBd(division);
            Snackbar.make(view, "Selected: " + division, Snackbar.LENGTH_LONG).show();
        }
    }

    public void goToRankingActivity(){
        Intent intent = new Intent(view.getContext(), RankingActivity.class);
        intent.putExtra("division", division);
        startActivity(intent);
    }

    public void getLeaderBoardApiAndStoreToBd(String division){

        Disposable d1 = UtilDota.initRetrofitRxDota2Ru().getLeaderBorderRx(division)
                .map(timeRefreshLeaderBoard -> addPositionToLeaderboard(timeRefreshLeaderBoard.getLeaderboard()))
                .doOnNext(UtilDota::storeLeaderboardInDB)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        timeRefreshLeaderBoard -> goToRankingActivity(),
                        error -> Log.e(TAG, error.getLocalizedMessage())
                );

        compositeDisposable.add(d1);
    }

    public List<Leaderboard> addPositionToLeaderboard(List<Leaderboard> leaderboards){
        int position = 1;

        for (Leaderboard leaderboard : leaderboards){
            leaderboard.setPosition(position++);
        }
        return leaderboards;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        compositeDisposable.dispose();
    }
}
