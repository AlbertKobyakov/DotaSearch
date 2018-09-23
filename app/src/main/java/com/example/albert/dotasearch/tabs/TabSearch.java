package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RVEmptyObserver;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.FoundPlayerActivity;
import com.example.albert.dotasearch.activity.PlayerInfoActivity;
import com.example.albert.dotasearch.adapter.FavoritePlayersAdapter;
import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.viewModel.SearchViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

import static android.widget.LinearLayout.VERTICAL;

public class TabSearch extends Fragment {
    private final static int LAYOUT = R.layout.fragment_search;
    private final static String TAG = "TabSearch";

    public SearchViewModel viewModel;

    private Unbinder unbinder;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String query;
    private FavoritePlayersAdapter mAdapter;
    private List<FavoritePlayer> favoritePlayerList;
    private FragmentActivity activity;
    private RequestManager glide;

    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.search_edit)
    EditText searchEditText;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view_favorite)
    RecyclerView recyclerViewFavorite;
    @BindView(R.id.empty_favorite_image)
    ImageView imageView;
    @BindView(R.id.linear)
    LinearLayout linearLayout;
    @BindView(R.id.list_favorite_players_wrapper)
    CardView listFavoritePlayersWrapper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glide = Glide.with(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        View view = inflater.inflate(LAYOUT, container, false);

        activity = getActivity();

        unbinder = ButterKnife.bind(this, view);

        setToolbarTitle();

        setAdapterAndRecyclerView();

        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        viewModel.getAllFavoritePlayers().observe(this, new Observer<List<FavoritePlayer>>() {
            @Override
            public void onChanged(@Nullable List<FavoritePlayer> favoritePlayers) {
                if (favoritePlayers != null) {
                    Log.d(TAG, favoritePlayers.size() + " response size");
                    favoritePlayerList = favoritePlayers;
                    mAdapter.setData(favoritePlayers);
                }
            }
        });

        viewModel.getIsRequestSearchSuccessful().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isSuccessSearchRequest) {
                if (isSuccessSearchRequest != null && isSuccessSearchRequest && query != null) {
                    goToFoundPlayerActivity(query);
                    query = null;
                }
            }
        });

        /*int id = getResources().getIdentifier("ic_dashboard_black_24dp", "drawable", getActivity().getPackageName());
        imageView.setImageResource(id);*/

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
        mAdapter = new FavoritePlayersAdapter(activity, glide);

        recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(activity));
        recyclerViewFavorite.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFavorite.setHasFixedSize(true);
        recyclerViewFavorite.setAdapter(mAdapter);
        if (getContext() != null) {
            recyclerViewFavorite.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
        }
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
                long currentAccountId = favoritePlayerList.get(viewHolder.getLayoutPosition()).getAccountId();
                String currentPlayerName = favoritePlayerList.get(viewHolder.getLayoutPosition()).getPersonaname();
                viewModel.deletePlayerWithFavoriteList(currentAccountId);

                if (activity != null) {
                    Snackbar.make(activity.findViewById(R.id.bottom_navigation_view), "Игрок " + currentPlayerName + " удален из избранного", Snackbar.LENGTH_SHORT).show();
                    Log.d(TAG, "item deleted " + favoritePlayerList.size() + " " + mAdapter.getFavoritePlayers().get(0).getPersonaname());

                    Log.d(TAG, "last item deleted");
                    AppBarLayout appBarLayout = activity.findViewById(R.id.appBarLayout);
                    appBarLayout.setExpanded(true, true);
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
}