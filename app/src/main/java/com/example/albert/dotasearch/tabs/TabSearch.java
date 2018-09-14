package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public class TabSearch extends Fragment {
    private final static int LAYOUT = R.layout.fragment_search;
    private final static String TAG = "TabSearch";

    public FavoritePlayersViewModel mWordViewModel;

    private Unbinder unbinder;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String query;
    private FavoritePlayersAdapter mAdapter;
    private List<FavoritePlayer> favoritePlayerList;
    private SearchViewModel viewModel;
    private FragmentActivity activity;

    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.search_edit)
    EditText searchEditText;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view_favorite)
    RecyclerView recyclerViewFavorite;
    @BindView(R.id.search_image)
    ImageView imageView;
    @BindView(R.id.linear)
    LinearLayout linearLayout;
    @BindView(R.id.list_favorite_players_wrapper)
    LinearLayout listFavoritePlayersWrapper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        View view = inflater.inflate(LAYOUT, container, false);

        activity = getActivity();

        unbinder = ButterKnife.bind(this, view);

        setToolbarTitle();

        setAdapterAndRecyclerView();

        mWordViewModel = ViewModelProviders.of(this).get(FavoritePlayersViewModel.class);

        mWordViewModel.getAllFavoritePlayers().observe(this, new Observer<List<FavoritePlayer>>() {
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
                if (foundPlayers != null && foundPlayers.size() > 0) {
                    Log.d(TAG, foundPlayers.get(0).getPersonaname());
                    if (query != null) {
                        goToFoundPlayerActivity(query);
                        query = null;
                    } else {
                        Log.d(TAG, "onChanged not query");
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

    private void setToolbarTitle() {
        if (activity.findViewById(R.id.toolbar) != null) {
            Toolbar toolbar = activity.findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.search);
        }
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new FavoritePlayersAdapter(activity);

        recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewFavorite.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFavorite.setHasFixedSize(true);
        recyclerViewFavorite.setAdapter(mAdapter);
        mAdapter.registerAdapterDataObserver(new RVEmptyObserver(recyclerViewFavorite, linearLayout, listFavoritePlayersWrapper));

        recyclerViewFavorite.addOnItemTouchListener(new RecyclerTouchListener(activity, recyclerViewFavorite, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d(TAG, "onClick recyclerViewFavorite" + position);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goToPlayerInfoActivity(favoritePlayerList.get(position));
                    }
                }, 150);

            }
        }));

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                View viewActivity;
                long currentAccountId = favoritePlayerList.get(viewHolder.getLayoutPosition()).getAccountId();
                String currentPlayerName = favoritePlayerList.get(viewHolder.getLayoutPosition()).getPersonaname();
                mWordViewModel.deletePlayerWithFavoriteList(currentAccountId);

                if (activity != null) {
                    viewActivity = activity.getWindow().getDecorView().getRootView();
                    Snackbar.make(viewActivity, "Игрок " + currentPlayerName + " удален из избранного", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(recyclerViewFavorite);
    }

    public void goToPlayerInfoActivity(FavoritePlayer favoritePlayer) {
        Intent intent = new Intent(activity, PlayerInfoActivity.class);
        intent.putExtra("accountId", favoritePlayer.getAccountId());
        intent.putExtra("personalName", favoritePlayer.getPersonaname());
        intent.putExtra("urlPlayer", favoritePlayer.getAvatarfull());
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (btnSearch != null && btnSearch.getVisibility() == View.GONE) {
            btnSearch.setVisibility(View.VISIBLE);
        }
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_search)
    public void onClick(final View v) {

        query = searchEditText.getText().toString();

        Log.d(TAG, "onClick btn_search");
        if (query.trim().length() == 0 || query.trim().length() < 3) {
            Snackbar.make(activity.getWindow().getDecorView().getRootView(), "Запрос должен состоять минимум из 3 символов", Snackbar.LENGTH_SHORT).show();
        } else {
            viewModel.searchRequest(query);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnSearch.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }, 300);
        }
    }

    public void goToFoundPlayerActivity(String query) {
        Log.d(TAG, "goToFoundPlayerActivity");
        Intent intent = new Intent(activity, FoundPlayerActivity.class);
        intent.putExtra("query", query);
        startActivity(intent);
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        compositeDisposable.dispose();
    }

    /*Single<Boolean> hasInternetConnection() {
        return Single.fromCallable(() -> {
            try {
                // Connect to Google DNS to checkValidateTeamsData for connection
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
    }*/
}