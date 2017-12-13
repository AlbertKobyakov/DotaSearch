package com.example.albert.dotasearch.tabs;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.ServiceGenerator;
import com.example.albert.dotasearch.activity.MainActivity;
import com.example.albert.dotasearch.adapter.MatchPlayerAdapter;
import com.example.albert.dotasearch.dao.HeroDao;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Match;
import com.example.albert.dotasearch.retrofit.DotaClient;

import java.time.chrono.HijrahEra;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.albert.dotasearch.activity.MainActivity.db;
import static com.example.albert.dotasearch.activity.PlayerInfoActivity.accountId;

public class TabMatchesPlayer  extends AbstractTabFragment {

    private static final String FRAGMENT_NAME = "TabMatchesPlayer";
    private static final int LAYOUT = R.layout.fragment_matches_player;

    //public static AppDatabase db;

    public static List<Match> matches = new ArrayList<>();
    private MatchPlayerAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public static TabMatchesPlayer getInstance(Context context) {
        Bundle args = new Bundle();
        TabMatchesPlayer fragment = new TabMatchesPlayer();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_matches_player));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        /*db = Room.databaseBuilder(view.getContext(),
                AppDatabase.class, "Dota.db").build();*/



        Log.e(FRAGMENT_NAME, "onCreateView");

        ButterKnife.bind(this, view);

        new DatabaseAsync().execute(view);

        getMatchesServiceGenerator(accountId, 20);

        //Log.e(FRAGMENT_NAME,matches.toString());

        return view;
    }

    public void getMatchesServiceGenerator(final long accountId, int limit){
        DotaClient matchService = ServiceGenerator.createService(DotaClient.class);
        Call<List<Match>> call = matchService.getMatchesPlayer(accountId, limit);
        call.enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if (response.isSuccessful()) {
                    // tasks available
                    matches = response.body();
                    setAdapterAndRecyclerView(matches);
                    Log.e(FRAGMENT_NAME,matches.toString());

                } else {
                    Snackbar.make(view, "Что-то пошло не так", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }
        });
    }

    public void setAdapterAndRecyclerView(List<Match> matches) {
        mAdapter = new MatchPlayerAdapter(matches, view.getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private static class DatabaseAsync extends AsyncTask<View,Void,Void>{
        @Override
        protected Void doInBackground(View... views) {
            List<Hero> heroes = db.userDao().getAll();
            if(heroes.size() != 0){
//                Snackbar.make(views[0], "Table Hero is not empty", Snackbar.LENGTH_LONG).show();
                    Log.e("HeroesBD", db.userDao().getAll().toString());
            } else {
                Snackbar.make(views[0], "Table Hero is empty", Snackbar.LENGTH_LONG).show();
            }
            return null;
        }
    }
}