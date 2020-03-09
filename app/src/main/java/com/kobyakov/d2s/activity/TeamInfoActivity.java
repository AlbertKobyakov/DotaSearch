package com.kobyakov.d2s.activity;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.model.Team;
import com.kobyakov.d2s.tabs.FragmentTeamInfoHeroes;
import com.kobyakov.d2s.tabs.FragmentTeamInfoMatches;
import com.kobyakov.d2s.tabs.FragmentTeamInfoPlayers;
import com.kobyakov.d2s.util.ExpandedAppBarListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamInfoActivity extends AppCompatActivity implements ExpandedAppBarListener {

    public static final String TAG = "TeamInfoActivity";
    public static final int LAYOUT = R.layout.activity_team_info;

    public long accountId;
    private Team team;

    private FragmentTeamInfoMatches fragmentTeamInfoMatches;
    private FragmentTeamInfoHeroes fragmentTeamInfoHeroes;
    private FragmentTeamInfoPlayers fragmentTeamInfoPlayers;

    private RequestManager glide;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.team_logo)
    ImageView teamLogo;
    @BindView(R.id.team_wins)
    TextView teamWins;
    @BindView(R.id.team_loses)
    TextView teamLoses;
    @BindView(R.id.rating_team)
    TextView ratingTeam;

    @BindView(R.id.last_match_time)
    TextView lastMatchTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        Log.d(TAG, "onCreate");

        ButterKnife.bind(this);

        String teamJson = getIntent().getStringExtra("teamGson");
        team = new Gson().fromJson(teamJson, Team.class);
        accountId = team.getTeamId();

        glide = Glide.with(this);

        fragmentTeamInfoMatches = FragmentTeamInfoMatches.newInstance(accountId);
        fragmentTeamInfoPlayers = FragmentTeamInfoPlayers.newInstance(accountId);
        fragmentTeamInfoHeroes = FragmentTeamInfoHeroes.newInstance(accountId);

        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.player_overview:
                    changeFragment(fragmentTeamInfoMatches, "fragmentTeamInfoMatches");
                    return true;
                case R.id.pros:
                    changeFragment(fragmentTeamInfoPlayers, "fragmentTeamInfoPlayers");
                    return true;
                case R.id.navigation_notifications:
                    changeFragment(fragmentTeamInfoHeroes, "fragmentTeamInfoHeroes");
                    return true;
            }
            return false;
        });

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.player_overview);
        }

        initToolbar();
    }

    private void initToolbar() {
        collapsingToolbarLayout.setTitle(team.getName());

        RequestOptions centerCrop = new RequestOptions()
                .centerCrop()
                .override(100, 100);

        glide.load(team.getLogoUrl())
                .error(glide.load(R.drawable.help))
                .apply(centerCrop)
                .into(teamLogo);

        teamWins.setText(getString(R.string.only_digital_value, team.getWins()));
        teamLoses.setText(getString(R.string.only_digital_value, team.getLosses()));

        long secondsMatchStartTime = (team.getLastMatchTime()) * 1000;
        String lastActivity = DateUtils.getRelativeTimeSpanString(secondsMatchStartTime).toString();
        lastMatchTime.setText(lastActivity);

        ratingTeam.setText(getString(R.string.only_digital_value, (int) team.getRating()));

        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_left));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
            Log.d(TAG, "onBackPressed");
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onExpandAppBar(boolean isExpand) {
        appBarLayout.setExpanded(isExpand, true);
    }
}