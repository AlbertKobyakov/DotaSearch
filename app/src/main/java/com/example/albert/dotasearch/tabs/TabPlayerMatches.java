package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.MatchDetailActivity;
import com.example.albert.dotasearch.adapter.PlayerInfoMatchesAdapter;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

import static com.example.albert.dotasearch.activity.PlayerInfoActivity.viewModel;

public class TabPlayerMatches extends Fragment {

    private static final String TAG = "TabPlayerMatches";
    private static final int LAYOUT = R.layout.fragment_player_overview;

    private PlayerInfoMatchesAdapter mAdapter;
    private List<MatchShortInfo> matchList;
    private Map<Integer, Hero> heroList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate");

        heroList = viewModel.getHeroes();
        viewModel.getMatches().observe(this, new Observer<List<MatchShortInfo>>() {
            @Override
            public void onChanged(@Nullable List<MatchShortInfo> matchShortInfos) {
                if(matchShortInfos != null){
                    setAdapterAndRecyclerView();

                    matchList = matchShortInfos;
                    mAdapter.setData(matchList, heroList);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);

        Log.e(TAG, "onCreateView");
        ButterKnife.bind(this, view);

        return view;
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new PlayerInfoMatchesAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                toMatchDetailActivity(position);
            }
        }));
    }

    public void toMatchDetailActivity(int position) {
        Log.e("44444444", "444444444444442");
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