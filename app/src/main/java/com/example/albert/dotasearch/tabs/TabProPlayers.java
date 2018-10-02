package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.PlayerInfoActivity;
import com.example.albert.dotasearch.adapter.ProPlayerAdapter;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.viewModel.ProPlayersViewModel;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TabProPlayers extends Fragment {

    private static final int LAYOUT = R.layout.fragment_pro_players;
    private static final String TAG = TabProPlayers.class.getSimpleName();

    private Unbinder unbinder;
    public List<ProPlayer> proPlayers;
    public ProPlayerAdapter mAdapter;
    private View view;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ProPlayersViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        setHasOptionsMenu(true);

        view = inflater.inflate(LAYOUT, container, false);

        unbinder = ButterKnife.bind(this, view);

        setToolbarTitle();

        setAdapterAndRecyclerView();

        viewModel = ViewModelProviders.of(this).get(ProPlayersViewModel.class);
        viewModel.getProPlayers().observe(this, new Observer<List<ProPlayer>>() {
            @Override
            public void onChanged(@Nullable List<ProPlayer> proPlayersResponse) {
                if (proPlayersResponse != null) {
                    proPlayers = proPlayersResponse;
                    mAdapter.setData(proPlayers);
                }
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(), recyclerView, (view, position)
                -> goToPlayerInfoActivity(mAdapter.getProPlayers().get(position))));//Toast.makeText(getActivity(), mAdapter.getProPlayers().get(position).getAccountId() + "", Toast.LENGTH_SHORT).show()));
        return view;
    }

    private void setToolbarTitle() {
        if (getActivity() != null && getActivity().findViewById(R.id.toolbar) != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.fragment_pro_players);
        }
    }

    public void goToPlayerInfoActivity(ProPlayer proPlayer) {
        Intent intent = new Intent(getContext(), PlayerInfoActivity.class);
        intent.putExtra("accountId", proPlayer.getAccountId());
        intent.putExtra("personalName", proPlayer.getPersonaname());
        intent.putExtra("urlPlayer", proPlayer.getAvatarfull());
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Поиск по имени и команде");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit " + query);
                mAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange " + newText);
                mAdapter.filter(newText);
                return false;
            }
        });
    }

    public void setAdapterAndRecyclerView() {
        Log.e(TAG, "setAdapterAndRecyclerView");
        mAdapter = new ProPlayerAdapter(getActivity(), Glide.with(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(Objects.requireNonNull(getActivity()));
        refWatcher.watch(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }
}