package com.example.albert.dotasearch.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.MatchFullInfo;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.albert.dotasearch.activity.MatchDetailActivity.matchFullInfo;
import static com.example.albert.dotasearch.activity.MatchDetailActivity.matchId;
import static com.example.albert.dotasearch.activity.StartActivity.db;

public class TabMatchDetail extends AbstractTabFragment {

    private static final String TAG = "TabMatchDetail";
    private static final int LAYOUT = R.layout.fragment_match_detail;

    private Unbinder unbinder;

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

        ButterKnife.bind(this, view);

        Toast.makeText(context, matchFullInfo.getMatchId()+"", Toast.LENGTH_SHORT).show();

        Log.d(TAG, matchFullInfo.getPlayers().toString());

        db.itemDao().getItemByIdRx(25)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> Log.d(TAG, item.getItemUrl()),
                        error -> Log.e(TAG, error.getLocalizedMessage())
                );

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
