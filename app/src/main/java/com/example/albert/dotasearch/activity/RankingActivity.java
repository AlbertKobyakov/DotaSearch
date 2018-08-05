package com.example.albert.dotasearch.activity;

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

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.adapter.RankingAdapter;
import com.example.albert.dotasearch.model.Leaderboard;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RankingActivity extends AppCompatActivity {

    public static String TAG = "RankingActivity";
    public static int LATYOUT = R.layout.activity_ranking;

    public static List<Leaderboard> leaderboards;
    private RankingAdapter mAdapter;
    private String division;
    private String divisionTranslate;
    public Unbinder unbinder;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LATYOUT);

        unbinder = ButterKnife.bind(this);

        division = getIntent().getStringExtra("division");

        setDivisionTranslate();

        initToolbar();

        Disposable d1 = App.get().getDB().leaderboardDao().getAllRx()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::setRecyclerViewAdapter,
                        error -> Log.e(TAG, error.getLocalizedMessage())
                );

        compositeDisposable.add(d1);
    }

    public void setRecyclerViewAdapter(List<Leaderboard> leaderboards){
        RankingActivity.leaderboards = leaderboards;
        mAdapter = new RankingAdapter(leaderboards, RankingActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    public void setDivisionTranslate(){
        if(division.equals("china")){
            divisionTranslate = getString(R.string.China);
        } else if(division.equals("se_asia")){
            divisionTranslate = getString(R.string.Asia);
        } else if(division.equals("americas")){
            divisionTranslate = getString(R.string.USA);
        } else if(division.equals("europe")){
            divisionTranslate = getString(R.string.Europe);
        }
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.toolbar_for_ranking, divisionTranslate));

        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_left));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //toolbar.inflateMenu(R.menu.menu_main);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        compositeDisposable.dispose();
    }
}