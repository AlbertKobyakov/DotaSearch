package com.example.albert.dotasearch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.adapter.FoundPlayerAdapter;
import com.example.albert.dotasearch.model.FoundPlayer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.albert.dotasearch.activity.StartActivity.db;

public class FoundPlayerActivity extends AppCompatActivity {

    public static final String TAG = "FoundPlayerActivity";
    public static final int LAYOUT = R.layout.found_player_activity;

    public static List<FoundPlayer> foundPlayers;
    private FoundPlayerAdapter mAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        ButterKnife.bind(this);

        initToolbar();

        Disposable d1 = db.foundPlayerDao()
                .getAllRx()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        FoundPlayerActivity::setFoundPlayers,
                        error -> Log.e(TAG, error.getLocalizedMessage()),
                        this::setAdapterAndRecyclerView
                );

        compositeDisposable.add(d1);
    }

    public static void setFoundPlayers(List<FoundPlayer> foundPlayers1) {
        foundPlayers = foundPlayers1;
    }

    public void setAdapterAndRecyclerView(){
        mAdapter = new FoundPlayerAdapter(foundPlayers, FoundPlayerActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                FoundPlayer foundPlayer = foundPlayers.get(position);

                Intent intent = new Intent(FoundPlayerActivity.this, PlayerInfoActivity.class);
                intent.putExtra("accountId", foundPlayer.getAccountId());
                intent.putExtra("personalName", foundPlayer.getPersonaname());
                intent.putExtra("lastMatchStr", foundPlayer.getLastMatchTime());
                startActivity(intent);
            }

            /*@Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Delete is selected?", Toast.LENGTH_SHORT).show();
            }*/
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

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

        return super.onCreateOptionsMenu(menu);
    }

    private void initToolbar() {
            toolbar.setTitle(R.string.search_players);
            toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_left));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
