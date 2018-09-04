package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.adapter.ProPlayerAdapter;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.viewModel.ProPlayersViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public class TabProPlayers extends AbstractTabFragment {

    private static final int LAYOUT = R.layout.fragment_pro_players;
    private static final String TAG = "TabProPlayers";

    private Unbinder unbinder;
    public List<ProPlayer> proPlayers;
    private List<ProPlayer> proPlayersTemp;
    public ProPlayerAdapter mAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.text_view_no_internet)
    TextView textViewNotInternet;

    public static TabProPlayers getInstance(Context context) {
        Bundle args = new Bundle();
        TabProPlayers fragment = new TabProPlayers();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_pro_players));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        setHasOptionsMenu(true);
        view = inflater.inflate(LAYOUT, container, false);

        unbinder = ButterKnife.bind(this, view);

        setAdapterAndRecyclerView();

        ProPlayersViewModel viewModel = ViewModelProviders.of(this).get(ProPlayersViewModel.class);
        viewModel.getProPlayers().observe(this, new Observer<List<ProPlayer>>() {
            @Override
            public void onChanged(@Nullable List<ProPlayer> proPlayersResponse) {
                if (proPlayersResponse != null) {
                    proPlayers = proPlayersResponse;
                    mAdapter.setData(proPlayers);
                }
                Log.d(TAG, proPlayers.size() + "");

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(), recyclerView, (view, position) -> {
            if (proPlayersTemp == null) {
                Toast.makeText(view.getContext(), proPlayers.get(position).getAccountId() + " is selected!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(), proPlayersTemp.get(position).getAccountId() + " is selected!", Toast.LENGTH_SHORT).show();
            }
        }));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) item.getActionView();
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

    public void setAdapterAndRecyclerView() {
        Log.e(TAG, "setAdapterAndRecyclerView");
        mAdapter = new ProPlayerAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        compositeDisposable.dispose();
    }

    public void filter(String text) {
        proPlayersTemp = new ArrayList<>();

        if (text.isEmpty()) {
            Log.d(TAG, "empty" + proPlayers.size());
            proPlayersTemp = proPlayers;
        } else {
            text = text.toLowerCase();
            for (ProPlayer item : proPlayers) {
                if (item.getPersonaname() != null && item.getName() != null && item.getTeamName() != null) {
                    if (item.getPersonaname().toLowerCase().contains(text)
                            || item.getName().toLowerCase().contains(text)
                            || item.getTeamName().toLowerCase().contains(text)) {
                        proPlayersTemp.add(item);
                    }
                }
            }
        }

        mAdapter.setData(proPlayersTemp);
    }
}
