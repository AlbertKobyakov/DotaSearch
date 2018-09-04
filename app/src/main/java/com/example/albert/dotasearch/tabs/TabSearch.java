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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RVEmptyObserver;
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

public class TabSearch extends AbstractTabFragment {
    private final static int LAYOUT = R.layout.fragment_search;
    private final static String TAG = "TabSearch";

    public FavoritePlayersViewModel mWordViewModel;

    private Unbinder unbinder;
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String query;
    private FavoritePlayersAdapter mAdapter;
    private List<FavoritePlayer> favoritePlayerList;
    private SearchViewModel viewModel;

    @BindView(R.id.btn_search) Button btnSearch;
    @BindView(R.id.search_edit) EditText searchEditText;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view_favorite) RecyclerView recyclerViewFavorite;
    @BindView(R.id.search_image) ImageView imageView;
    //@BindView(R.id.card2) CardView cardView;
    @BindView(R.id.linear) LinearLayout linearLayout;
    @BindView(R.id.list_favorite_players_wrapper) LinearLayout listFavoritePlayersWrapper;

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

        /*new MaterialTapTargetPrompt.Builder(getActivity())
                .setTarget(recyclerViewFavorite)
                .setPrimaryText("Send your first email")
                .setSecondaryText("Tap the envelope to start composing your first email")
                .setBackgroundColour(getResources().getColor(R.color.win))
                .setPromptFocal(new RectanglePromptFocal())
                .setPromptBackground(new FullscreenPromptBackground())
                .setPrimaryTextGravity(Gravity.TOP)
                *//*.setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                        {
                            // User has pressed the prompt target
                        }
                    }
                })*//*
                .show();*/

        return view;
    }


    public void setAdapterAndRecyclerView() {
        mAdapter = new FavoritePlayersAdapter(getActivity());

        recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFavorite.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFavorite.setHasFixedSize(true);
        recyclerViewFavorite.setAdapter(mAdapter);
        /*recyclerViewFavorite.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));*/
        mAdapter.registerAdapterDataObserver(new RVEmptyObserver(recyclerViewFavorite, linearLayout, listFavoritePlayersWrapper));

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

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long currentAccountId = favoritePlayerList.get(viewHolder.getLayoutPosition()).getAccountId();
                String currentPlayerName = favoritePlayerList.get(viewHolder.getLayoutPosition()).getPersonaname();
                //Toast.makeText(context, "swipe " + currentAccountId, Toast.LENGTH_SHORT).show();
                mWordViewModel.deletePlayerWithFavoriteList(currentAccountId);
                Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), "Игрок " + currentPlayerName + " удален из избранного", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(recyclerViewFavorite);
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
        if(btnSearch != null && btnSearch.getVisibility() == View.GONE){
            btnSearch.setVisibility(View.VISIBLE);
        }
        if(progressBar != null && progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.GONE);
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
            Snackbar.make(getActivity().getWindow().getDecorView().getRootView(), "Запрос должен состоять минимум из 3 символов", Snackbar.LENGTH_SHORT).show();
        } else {
            btnSearch.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            Disposable dis = hasInternetConnection().subscribe(
                    isInternet -> {
                        if (isInternet) {
                            viewModel.searchRequest(query);
                        } else {
                            Snackbar.make(v, getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
                            btnSearch.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
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