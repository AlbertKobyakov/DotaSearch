package com.kobyakov.d2s.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.RecyclerTouchListener;
import com.kobyakov.d2s.adapter.FoundPlayerAdapter;
import com.kobyakov.d2s.model.FoundPlayer;
import com.kobyakov.d2s.viewModel.FoundPlayerViewModel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FoundPlayerActivity extends AppCompatActivity {

    public static final String TAG = "FoundPlayerActivity";
    public static final int LAYOUT = R.layout.found_player_activity;

    private FoundPlayer foundPlayer;
    private String query;

    private FoundPlayerAdapter mAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Unbinder unbinder;
    private FoundPlayerViewModel viewModel;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_toolbar_parallax_1)
    TextView textToolbarParallax1;
    @BindView(R.id.text_toolbar_parallax_2)
    TextView textToolbarParallax2;
    @BindView(R.id.text_toolbar_parallax_3)
    TextView textToolbarParallax3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        unbinder = ButterKnife.bind(this);

        setAdapterAndRecyclerView();

        query = getIntent().getStringExtra("query");

        initToolbar();

        viewModel = ViewModelProviders.of(this).get(FoundPlayerViewModel.class);
        viewModel.getFoundPlayers().observe(this, foundPlayers -> {
            if (foundPlayers != null && foundPlayers.size() > 0) {
                mAdapter.setData(foundPlayers);
                textToolbarParallax2.setText(getResources().getString(R.string.found, query));
                textToolbarParallax3.setText(getResources().getString(R.string.result_search_query, foundPlayers.size()));
            }
        });
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new FoundPlayerAdapter(getApplicationContext(), Glide.with(this));
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), getResources().getInteger(R.integer.number_row_in_line_found_players));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, (view, position) -> {
            foundPlayer = mAdapter.getFoundPlayerByPosition(position);

            Disposable dis = hasInternetConnection().subscribe(
                    isInternet -> {
                        if (isInternet) {
                            goToPlayerInfoActivity(foundPlayer);
                        } else {
                            Snackbar.make(findViewById(R.id.coordinator_layout), getString(R.string.no_internet), Snackbar.LENGTH_SHORT).show();
                        }
                    },
                    err -> System.out.println(err.getLocalizedMessage())
            );

            compositeDisposable.add(dis);
        }));
    }

    public void goToPlayerInfoActivity(FoundPlayer foundPlayer) {
        Intent intent = new Intent(FoundPlayerActivity.this, PlayerInfoActivity.class);
        intent.putExtra("accountId", foundPlayer.getAccountId());
        intent.putExtra("personalName", foundPlayer.getPersonaname());
        intent.putExtra("urlPlayer", foundPlayer.getAvatarfull());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) item.getActionView();
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
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        unbinder.unbind();
    }

    Single<Boolean> hasInternetConnection() {
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
    }
}