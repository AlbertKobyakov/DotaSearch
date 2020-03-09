package com.kobyakov.d2s.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.RecyclerTouchListener;
import com.kobyakov.d2s.activity.PlayerInfoActivity;
import com.kobyakov.d2s.adapter.PlayerProsAdapter;
import com.kobyakov.d2s.model.Pros;
import com.kobyakov.d2s.modelfactory.FactoryForPlayerInfoProsViewModel;
import com.kobyakov.d2s.util.CheckLoadedData;
import com.kobyakov.d2s.util.ExpandedAppBarListener;
import com.kobyakov.d2s.viewModel.PlayerInfoProsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentPlayerInfoPros extends Fragment {
    private static final String TAG = "FragmentPlayerInfoPros";
    private static final int LAYOUT = R.layout.fragment_player_overview;
    private static final String ACCOUNT_ID = "account_id";
    private static final String NAME = "name";

    public PlayerProsAdapter mAdapter;
    public List<Pros> allPros;
    private long accountId;
    private String name;
    private PlayerInfoProsViewModel viewModel;
    private Unbinder unbinder;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.for_empty_recycler_size)
    LinearLayout forEmptyRecyclerSize;
    @BindView(R.id.btn_refresh)
    Button btnRefresh;
    @BindView(R.id.block_error)
    LinearLayout blockError;
    @BindView(R.id.no_internet)
    TextView text_no_internet;
    @BindView(R.id.network_error)
    TextView text_network_error;
    @BindView(R.id.no_data_text)
    TextView text_no_data;
    @BindView(R.id.server_not_response)
    TextView serverNotResponse;

    private ExpandedAppBarListener expandedAppBarListener;

    public static FragmentPlayerInfoPros newInstance(long accountId, String personalName) {
        FragmentPlayerInfoPros fragmentPlayerInfoPros = new FragmentPlayerInfoPros();
        Bundle bundle = new Bundle();
        bundle.putLong(ACCOUNT_ID, accountId);
        bundle.putString(NAME, personalName);
        fragmentPlayerInfoPros.setArguments(bundle);

        return fragmentPlayerInfoPros;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        expandedAppBarListener = (ExpandedAppBarListener) getActivity();

        View view = inflater.inflate(LAYOUT, container, false);

        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null) {
            accountId = getArguments().getLong(ACCOUNT_ID);
            name = getArguments().getString(NAME);
            Log.d(TAG, "personal name: " + name);
        }

        setAdapterAndRecyclerView();

        viewModel = ViewModelProviders.of(this, new FactoryForPlayerInfoProsViewModel(accountId)).get(PlayerInfoProsViewModel.class);

        viewModel.getPros().observe(this, pros -> {
            if (pros != null) {
                if (pros.size() > 0) {
                    allPros = pros;

                    removeRepeatFirstProPlayer(pros);

                    mAdapter.setData(pros);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    //expandedAppBarListener.onExpandAppBar(true);
                } else {
                    progressBar.setVisibility(View.GONE);
                    forEmptyRecyclerSize.setVisibility(View.VISIBLE);
                    Log.e(TAG, "expanded");
                    expandedAppBarListener.onExpandAppBar(false);
                }
            }
        });

        viewModel.getStatusCode().observe(this, statusCode -> {
            if (statusCode != null && allPros == null) {
                Log.d(TAG, statusCode + "");
                int fistNumberStatusCode = statusCode / 100;
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);

                if (fistNumberStatusCode > 2) {
                    blockError.setVisibility(View.VISIBLE);
                    text_network_error.setVisibility(View.VISIBLE);
                } else if (fistNumberStatusCode == -2) {
                    blockError.setVisibility(View.VISIBLE);
                    text_no_internet.setVisibility(View.VISIBLE);
                } else if (fistNumberStatusCode == -3) {
                    blockError.setVisibility(View.VISIBLE);
                    serverNotResponse.setVisibility(View.VISIBLE);
                }
                expandedAppBarListener.onExpandAppBar(false);
            }
        });

        return view;
    }

    @OnClick(R.id.btn_refresh)
    public void refresh() {

        PlayerInfoActivity activity = (PlayerInfoActivity) getActivity();
        if (activity != null && !activity.isLoadPlayerOverviewCombine) {
            CheckLoadedData checkLoadedData = ((CheckLoadedData) getActivity());
            checkLoadedData.repeat();
        }

        viewModel.repeatedRequest();
        progressBar.setVisibility(View.VISIBLE);
        blockError.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void removeRepeatFirstProPlayer(List<Pros> prosList) {
        //remove first list item if it equals name current player
        if (prosList.get(0).getName().equals(name)) {
            prosList.remove(0);
        }
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new PlayerProsAdapter(getActivity(), Glide.with(this));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.number_row_in_line_pros_player)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //mAdapter.registerAdapterDataObserver(new RVEmptyObserver(recyclerView, progressBar, recyclerView));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, (view, position)
                -> toPlayerInfoActivity(position)));
    }

    public void toPlayerInfoActivity(int position) {
        Intent intent = new Intent(getActivity(), PlayerInfoActivity.class);
        intent.putExtra("accountId", allPros.get(position).getAccountId());
        intent.putExtra(NAME, allPros.get(position).getName());
        intent.putExtra("urlPlayer", allPros.get(position).getAvatarmedium());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}