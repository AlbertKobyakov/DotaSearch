package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.FoundPlayerActivity;
import com.example.albert.dotasearch.activity.PlayerInfoActivity;
import com.example.albert.dotasearch.adapter.FavoritePlayersAdapter;
import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.model.FoundPlayer;
import com.example.albert.dotasearch.viewModel.FavoritePlayersViewModel;
import com.example.albert.dotasearch.viewModel.SearchViewModel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.toptas.fancyshowcase.FancyShowCaseView;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class TabSearch extends AbstractTabFragment {
    private final static int LAYOUT = R.layout.fragment_search;
    private final static String TAG = "TabSearch";

    public FavoritePlayersViewModel mWordViewModel;

    private Unbinder unbinder;
    public Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String query;
    private FavoritePlayersAdapter mAdapter;
    private List<FavoritePlayer> favoritePlayerList;
    private SearchViewModel viewModel;

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
                if (favoritePlayers != null) {
                    favoritePlayerList = favoritePlayers;
                    mAdapter.setData(favoritePlayers);
                }
            }
        });

        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        viewModel.getFoundPlayers().observe(this, new Observer<List<FoundPlayer>>() {
            @Override
            public void onChanged(@Nullable List<FoundPlayer> foundPlayers) {
                if(foundPlayers != null && foundPlayers.size() > 0){
                    Log.d(TAG, foundPlayers.get(0).getPersonaname());
                    if(query != null){
                        goToFoundPlayerActivity(query);
                    }

                }
            }
        });

        new MaterialShowcaseView.Builder(getActivity())
                .setTarget(recyclerViewFavorite)
                .setDismissOnTouch(true)
                .setContentText("This is some amazing feature you should know about")
                .setDelay(2000) // optional but starting animations immediately in onCreate can make them choppy
                //.singleUse("111") // provide a unique ID used to ensure it is only shown once
                .setMaskColour(R.color.lose)
                .useFadeAnimation()
                .show();

        /*new FancyShowCaseView.Builder(getActivity())
                .focusOn(recyclerViewFavorite)
                .title("Focus on View")
                //.showOnce("2")
                .titleGravity(Gravity.BOTTOM)
                .backgroundColor(R.color.win)

                .build()
                .show();*/

        // sequence example
        /*ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(2000); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), "1");

        sequence.setConfig(config);

        sequence.addSequenceItem(btnSearch,
                "This is button one", "GOT IT");

        sequence.addSequenceItem(searchEditText,
                "This is button two", "GOT IT");

        sequence.addSequenceItem(recyclerViewFavorite,
                "This is button three", "GOT IT");

        sequence.start();*/

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
                Disposable dis = hasInternetConnection().subscribe(
                        isInternet -> {
                            if (isInternet) {
                                goToPlayerInfoActivity(favoritePlayerList.get(position));
                            } else {
                                Snackbar.make(view, getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
                            }
                        },
                        err -> System.out.println(err.getLocalizedMessage())
                );

                compositeDisposable.add(dis);
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
        query = searchEditText.getText().toString();
        if(query.trim().length() == 0 || query.trim().length() < 3){
            Snackbar.make(v, "Запрос должен состоять минимум из 3 символов", Snackbar.LENGTH_SHORT).show();
        } else {
            btnSearch.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            Disposable dis = hasInternetConnection().subscribe(
                    isInternet -> {
                        if (isInternet) {
                            viewModel.searchRequest(query);
                        } else {
                            Snackbar.make(v, getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
                            btnSearch.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    },
                    err -> System.out.println(err.getLocalizedMessage())
            );

            compositeDisposable.add(dis);
        }

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

    Single<Boolean> hasInternetConnection() {
        return Single.fromCallable(() -> {
            try {
                // Connect to Google DNS to check for connection
                int timeoutMs = 1500;
                Socket socket = new Socket();
                InetSocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);

                socket.connect(socketAddress, timeoutMs);
                socket.close();

                return true;
            } catch (IOException io) {
                return false;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}