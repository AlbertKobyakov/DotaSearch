package com.kobyakov.d2s.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.adapter.ProTeamAdapter;
import com.kobyakov.d2s.model.Team;
import com.kobyakov.d2s.viewModel.ProTeamsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TabProTeam extends Fragment {
    private static final String TAG = "TabProTeam";
    private static final int LAYOUT = R.layout.tab_pro_team;
    private ProTeamAdapter mAdapter;
    private ProTeamsViewModel viewModel;
    private Unbinder unbinder;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.block_error)
    LinearLayout blockError;
    @BindView(R.id.no_internet)
    TextView noInternet;
    @BindView(R.id.network_error)
    TextView networkError;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        setHasOptionsMenu(true);

        View view = inflater.inflate(LAYOUT, container, false);

        unbinder = ButterKnife.bind(this, view);

        setToolbarTitle();

        setAdapterAndRecyclerView();

        viewModel = ViewModelProviders.of(this).get(ProTeamsViewModel.class);

        viewModel.getTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable List<Team> teams) {
                if (teams != null) {
                    if(teams.size() > 0){
                        mAdapter.setData(teams);
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });

        viewModel.getStatusCode().observe(this, responseStatusCode -> {
            Log.d(TAG, "responseCode: " + responseStatusCode);

            if(responseStatusCode != null){
                if(responseStatusCode > 200){
                    blockError.setVisibility(View.VISIBLE);
                    networkError.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if(responseStatusCode == -200){
                    blockError.setVisibility(View.VISIBLE);
                    noInternet.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

        });

        return view;
    }

    @OnClick(R.id.btn_refresh)
    public void refresh() {
        viewModel.repeatRequest();
        blockError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setToolbarTitle() {
        if (getActivity() != null && getActivity().findViewById(R.id.toolbar) != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.fragment_pro_teams);
        }
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new ProTeamAdapter(getActivity(), Glide.with(this));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), calculateNoOfColumns(getContext())));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.hasFixedSize();

        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (mAdapter.getTeamsForAdapter() != null && mAdapter.getTeamsForAdapter().size() >= position) {
                    Toast.makeText(getContext(), mAdapter.getTeamsForAdapter().get(position).getTeamId() + "", Toast.LENGTH_SHORT).show();
                }
            }
        }));*/
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 90);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.team_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}