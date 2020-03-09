package com.kobyakov.d2s.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.RecyclerTouchListener;
import com.kobyakov.d2s.activity.PlayerInfoActivity;
import com.kobyakov.d2s.adapter.ProPlayerAdapter;
import com.kobyakov.d2s.model.ProPlayer;
import com.kobyakov.d2s.viewModel.ProPlayersViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TabProPlayers extends Fragment {

    private static final int LAYOUT = R.layout.fragment_pro_players;
    private static final String TAG = TabProPlayers.class.getSimpleName();

    private Unbinder unbinder;
    public List<ProPlayer> proPlayers;
    public ProPlayerAdapter mAdapter;
    private View view;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.for_empty_recycler_size)
    LinearLayout forEmptyRecyclerSize;
    @BindView(R.id.block_error)
    LinearLayout blockError;
    @BindView(R.id.no_internet)
    TextView noInternet;
    @BindView(R.id.network_error)
    TextView networkError;

    private ProPlayersViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        setHasOptionsMenu(true);

        view = inflater.inflate(LAYOUT, container, false);

        unbinder = ButterKnife.bind(this, view);

        setToolbarTitle();

        setAdapterAndRecyclerView();

        viewModel = ViewModelProviders.of(this).get(ProPlayersViewModel.class);
        viewModel.getProPlayers().observe(this, proPlayersResponse -> {
            if (proPlayersResponse != null) {
                if (proPlayersResponse.size() > 0) {
                    proPlayers = proPlayersResponse;
                    mAdapter.setData(proPlayers);
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getStatusCode().observe(this, responseStatusCode -> {
            Log.d(TAG, "responseCode: " + responseStatusCode);

            if (responseStatusCode != null) {
                if (responseStatusCode > 200) {
                    blockError.setVisibility(View.VISIBLE);
                    networkError.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else if (responseStatusCode == -200) {
                    blockError.setVisibility(View.VISIBLE);
                    noInternet.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(), recyclerView, (view, position)
                -> goToPlayerInfoActivity(mAdapter.getProPlayers().get(position))));//Toast.makeText(getActivity(), mAdapter.getProPlayers().get(position).getAccountId() + "", Toast.LENGTH_SHORT).show()));
        return view;
    }

    @OnClick(R.id.btn_refresh)
    public void refresh() {
        viewModel.repeatRequest();
        blockError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setToolbarTitle() {
        if (getActivity() != null && getActivity().findViewById(R.id.toolbar) != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.fragment_pro_players);
        }
    }

    public void goToPlayerInfoActivity(ProPlayer proPlayer) {
        Intent intent = new Intent(getContext(), PlayerInfoActivity.class);
        intent.putExtra("accountId", proPlayer.getAccountId());
        intent.putExtra("name", proPlayer.getName());
        intent.putExtra("urlPlayer", proPlayer.getAvatarfull());
        startActivity(intent);
    }

    public String getTAG() {
        return TAG;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getString(R.string.search_by_name_and_team));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit " + query);
                mAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange " + newText);
                mAdapter.filter(newText);
                return false;
            }
        });
    }

    public void setAdapterAndRecyclerView() {
        Log.e(TAG, "setAdapterAndRecyclerView");
        mAdapter = new ProPlayerAdapter(getActivity(), Glide.with(this));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.number_row_in_line_tab_pro_players)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }
}