package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.adapter.ProTeamAdapter;
import com.example.albert.dotasearch.model.Team;
import com.example.albert.dotasearch.viewModel.ProTeamsViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabProTeam extends Fragment {
    private static final String TAG = "TabProTeam";
    private static final int LAYOUT = R.layout.tab_pro_team;
    private ProTeamAdapter mAdapter;
    private List<Team> teamList;
    private List<Team> teamsTemp;
    private ProTeamsViewModel viewModel;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        setHasOptionsMenu(true);

        View view = inflater.inflate(LAYOUT, container, false);

        ButterKnife.bind(this, view);

        setAdapterAndRecyclerView();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, (viewModel == null) + "");

        if(viewModel == null){
            viewModel = ViewModelProviders.of(this).get(ProTeamsViewModel.class);
        }
        viewModel.getTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable List<Team> teams) {
                Log.d(TAG, teams.size() + " response");
                teamList = teams;
                mAdapter.setData(teams);
                Toast.makeText(getContext(), " mAdapter.setData(teams);", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(teamList != null){
            mAdapter.setData(teamList);
        }
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new ProTeamAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //recyclerView.hasFixedSize();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(teamsTemp == null){
                    Toast.makeText(getContext(), teamList.get(position).getTeamId() + "", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), teamsTemp.get(position).getTeamId() + "", Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    @Override
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
    }

}
