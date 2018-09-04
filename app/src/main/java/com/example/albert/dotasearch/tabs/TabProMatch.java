package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.Toast;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.adapter.ProMatchAdapter;
import com.example.albert.dotasearch.model.ProMatch;
import com.example.albert.dotasearch.viewModel.ProMatchesViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabProMatch extends Fragment {
    private static final String TAG = "TabProMatch";
    private static final int LAYOUT = R.layout.tab_pro_match;
    private View view;
    private ProMatchAdapter mAdapter;
    private List<ProMatch> proMatchList;
    private List<ProMatch> proMatchesTemp;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        setHasOptionsMenu(true);

        view = inflater.inflate(LAYOUT, container, false);

        ButterKnife.bind(this, view);

        setAdapterAndRecyclerView();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProMatchesViewModel viewModel = ViewModelProviders.of(this).get(ProMatchesViewModel.class);
        viewModel.getProMatches().observe(this, new Observer<List<ProMatch>>() {
            @Override
            public void onChanged(@Nullable List<ProMatch> proMatches) {
                Log.d(TAG, proMatches.size() + " response");
                proMatchList = proMatches;
                mAdapter.setData(proMatches);
            }
        });
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new ProMatchAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(proMatchesTemp == null){
                    Toast.makeText(getContext(), proMatchList.get(position).getMatchId() + "", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), proMatchesTemp.get(position).getMatchId() + "", Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    public void filter(String text) {
        teamsTemp = new ArrayList<>();

        if(text.isEmpty()){
            Log.d(TAG, "empty" + teamList.size());
            teamsTemp = teamList;
        } else {
            text = text.toLowerCase();
            for(Team team: teamList){
                if(team.getName() != null && team.getTag() != null){
                    if(team.getName().toLowerCase().contains(text)
                            || team.getTag().toLowerCase().contains(text)){
                        teamsTemp.add(team);
                    }
                }
            }
        }

        mAdapter.setData(teamsTemp);
    }*/

}

