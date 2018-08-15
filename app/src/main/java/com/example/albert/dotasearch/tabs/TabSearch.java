package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.FoundPlayerActivity;
import com.example.albert.dotasearch.activity.PlayerInfoActivity;
import com.example.albert.dotasearch.adapter.FavoritePlayersAdapter;
import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.util.UtilDota;
import com.example.albert.dotasearch.viewModel.FavoritePlayersViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TabSearch extends AbstractTabFragment {
    private final static int LAYOUT = R.layout.fragment_search;
    private final static String TAG = "TabSearch";

    public FavoritePlayersViewModel mWordViewModel;

    private Unbinder unbinder;
    public Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    String editText;
    private FavoritePlayersAdapter mAdapter;
    private List<FavoritePlayer> favoritePlayerList;

    @BindView(R.id.btn_search) Button btnSearch;
    @BindView(R.id.search_edit) EditText searchEditText;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view_favorite) RecyclerView recyclerViewFavorite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        final View view = inflater.inflate(LAYOUT, container, false);

        context = view.getContext();

        unbinder = ButterKnife.bind(this, view);

        setAdapterAndRecyclerView();

        mWordViewModel = ViewModelProviders.of(this).get(FavoritePlayersViewModel.class);

        mWordViewModel.getmAllWords().observe(this, new Observer<List<FavoritePlayer>>() {
            @Override
            public void onChanged(@Nullable List<FavoritePlayer> favoritePlayers) {
                if (favoritePlayers != null && favoritePlayers.size() > 0) {
                    favoritePlayerList = favoritePlayers;
                    mAdapter.setData(favoritePlayers);
                }
            }
        });

        return view;
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new FavoritePlayersAdapter(getActivity());
        recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFavorite.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFavorite.setAdapter(mAdapter);

        recyclerViewFavorite.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewFavorite, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                goToPlayerInfoActivity(favoritePlayerList.get(position));
            }
        }));
    }

    public void goToPlayerInfoActivity(FavoritePlayer favoritePlayer){
        Intent intent = new Intent(getContext(), PlayerInfoActivity.class);
        intent.putExtra("accountId", favoritePlayer.getAccountId());
        intent.putExtra("personalName", favoritePlayer.getPersonaname());
        intent.putExtra("urlPlayer", favoritePlayer.getAvatarfull());
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(btnSearch != null && btnSearch.getVisibility() == View.INVISIBLE){
            btnSearch.setVisibility(View.VISIBLE);
        }
        if(progressBar != null && progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public static TabSearch getInstance(Context context) {
        Bundle args = new Bundle();
        TabSearch fragment = new TabSearch();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.search));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @OnClick(R.id.btn_search)
    public void onClick(final View v) {
        editText = searchEditText.getText().toString();
        if(editText.trim().length() == 0){
            Toast.makeText(v.getContext(), "Вы не ввели не 1 символа ((", Toast.LENGTH_LONG).show();
        } else {
            btnSearch.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            Disposable dis = UtilDota.initRetrofitRx().getFoundPlayersRx(editText)
                    .flatMap(foundPlayers -> {
                        App.get().getDB().foundPlayerDao().deleteAllFoundPlayer();
                        App.get().getDB().foundPlayerDao().insertAll(foundPlayers);
                        return Observable.fromIterable(foundPlayers);
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            response -> Log.d(TAG, response.getPersonaname()),
                            this::handleError,
                            () -> goToFoundPlayerActivity(editText)
                    );

            compositeDisposable.add(dis);
        }

    }

    public void handleError(Throwable t){
        btnSearch.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Log.e(TAG, "handleError: " + t.getMessage() + " " + t.getLocalizedMessage());
        //Toast.makeText(v.getContext(), t.getMessage() + " " + 44, Toast.LENGTH_LONG).show();
    }

    public void goToFoundPlayerActivity(String query){
        Log.d(TAG, "goToFoundPlayerActivity");
        Intent intent = new Intent(context, FoundPlayerActivity.class);
        intent.putExtra("query", query);
        startActivity(intent);
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        compositeDisposable.dispose();
    }
}
