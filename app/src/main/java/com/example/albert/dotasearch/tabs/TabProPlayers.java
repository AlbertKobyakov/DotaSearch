package com.example.albert.dotasearch.tabs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.MainActivity;
import com.example.albert.dotasearch.adapter.ProPlayerAdapter;
import com.example.albert.dotasearch.model.ProPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.albert.dotasearch.activity.StartActivity.db;

public class TabProPlayers extends AbstractTabFragment {

    private static final int LAYOUT = R.layout.fragment_pro_players;
    private static final String TAG = "TabProPlayers";

    private Unbinder unbinder;
    public static List<ProPlayer> proPlayers = new ArrayList<>();
    public ProPlayerAdapter mAdapter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.text_view_no_internet) TextView textViewNotInternet;

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

        setHasOptionsMenu(true);
        view = inflater.inflate(LAYOUT, container, false);

        unbinder = ButterKnife.bind(this, view);

        db.proPlayerDao()
                .getAllRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setAdapterAndRecyclerView);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ProPlayer proPlayer = proPlayers.get(position);
                Toast.makeText(view.getContext(), proPlayer.getAccountId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            /*@Override
            public void onLongClick(View view, int position) {
                Toast.makeText(view.getContext(), "Delete is selected?", Toast.LENGTH_SHORT).show();
            }*/
        }));
        return view;
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

    public void setAdapterAndRecyclerView(List<ProPlayer> proPlayers){
        Log.e(TAG, "setAdapterAndRecyclerView");
        TabProPlayers.proPlayers = proPlayers;
        mAdapter = new ProPlayerAdapter(proPlayers, view.getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
