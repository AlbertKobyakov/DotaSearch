package com.kobyakov.d2s.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kobyakov.d2s.R;
import com.kobyakov.d2s.modelfactory.FactoryForMatchDetailViewModel;
import com.kobyakov.d2s.tabs.FragmentMatchDetailCharts;
import com.kobyakov.d2s.tabs.FragmentMatchDetailOverview;
import com.kobyakov.d2s.viewModel.MatchDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MatchDetailActivity extends AppCompatActivity {

    public static final String TAG = "MatchDetailActivity";
    public static final int LAYOUT = R.layout.activity_match_detail;

    private FragmentMatchDetailOverview fragmentMatchDetailOverview;
    private FragmentMatchDetailCharts fragmentMatchDetailCharts;
    private MatchDetailViewModel viewModel;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.content)
    FrameLayout frameLayout;
    @BindView(R.id.block_error)
    LinearLayout blockError;
    @BindView(R.id.no_internet)
    TextView noInternet;
    @BindView(R.id.network_error)
    TextView networkError;

    public long matchId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(LAYOUT);

        Log.d(TAG, "onCreate");

        ButterKnife.bind(this);

        navigation.setVisibility(View.GONE);

        matchId = getIntent().getLongExtra("matchId", 0);

        viewModel = ViewModelProviders.of(this, new FactoryForMatchDetailViewModel(matchId)).get(MatchDetailViewModel.class);
        viewModel.getStatusCode().observe(this, statusCode -> {
            Log.d(TAG, statusCode + " status " + " fragment size " + getSupportFragmentManager().getFragments().size());
            if (statusCode != null) {
                if (statusCode == 200 && getSupportFragmentManager().getFragments().size() == 0) {
                    progressBar.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    navigation.setVisibility(View.VISIBLE);
                    navigation.setSelectedItemId(R.id.match_overview);
                } else if (statusCode == 200) {
                    progressBar.setVisibility(View.GONE);
                    frameLayout.setVisibility(View.VISIBLE);
                    navigation.setVisibility(View.VISIBLE);
                } else if (statusCode == -200) {
                    progressBar.setVisibility(View.GONE);
                    blockError.setVisibility(View.VISIBLE);
                    noInternet.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    blockError.setVisibility(View.VISIBLE);
                    networkError.setVisibility(View.VISIBLE);
                }
            }
        });

        fragmentMatchDetailOverview = FragmentMatchDetailOverview.newInstance(matchId);
        fragmentMatchDetailCharts = FragmentMatchDetailCharts.newInstance(matchId);

        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.match_overview:
                    changeFragment(fragmentMatchDetailOverview, "fragmentMatchOverview");
                    return true;

                case R.id.charts:
                    changeFragment(fragmentMatchDetailCharts, "fragmentMatchDetailCharts");
                    return true;
            }
            return false;
        });

        initToolbar();
    }

    @OnClick(R.id.btn_refresh)
    public void refresh() {
        viewModel.repeatRequest();
        blockError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initToolbar() {
        toolbar.setTitle(getResources().getString(R.string.match_detail, matchId));
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_left));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void changeFragment(Fragment fragment, String fragmentTagName) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment curFrag = mFragmentManager.getPrimaryNavigationFragment();
        if (curFrag != null) {
            Log.d(TAG, curFrag.getTag() + " detach");
            fragmentTransaction.detach(curFrag);
        }

        Fragment fragmentTemp = mFragmentManager.findFragmentByTag(fragmentTagName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            Log.d(TAG, "add");
            Log.d(TAG, fragmentTemp + " " + fragmentTagName);
            fragmentTransaction.add(R.id.content, fragmentTemp, fragmentTagName);
        } else {
            Log.d(TAG, fragmentTemp.getTag() + " attach");
            fragmentTransaction.attach(fragmentTemp);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}