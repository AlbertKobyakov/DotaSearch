package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.Observer;
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
import com.example.albert.dotasearch.adapter.PlayerInfoHeroesAdapter;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.PlayerHero;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.albert.dotasearch.activity.PlayerInfoActivity.viewModel;

public class FragmentPlayerInfoHeroes extends Fragment {
    private static final String TAG = "FragmentPlayerHeroes";
    private static final int LAYOUT = R.layout.fragment_player_overview;
    private View view;

    private List<PlayerHero> playerHeroes;
    private Map<Integer, Hero> heroList;
    private PlayerInfoHeroesAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        view = inflater.inflate(LAYOUT, container, false);

        ButterKnife.bind(this, view);

        setAdapterAndRecyclerView();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        heroList = viewModel.getHeroes();

        viewModel.getPlayerHeroes().observe(this, new Observer<List<PlayerHero>>() {
            @Override
            public void onChanged(@Nullable List<PlayerHero> playerHeroes) {
                if (playerHeroes != null) {
                    Log.d(TAG, playerHeroes.toString());

                    mAdapter.setData(playerHeroes, heroList);
                }
            }
        });
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new PlayerInfoHeroesAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
        @Override
        public void onClick(View view, int position) {
            //toMatchDetailActivity(position);
        }
    }));
}
}
