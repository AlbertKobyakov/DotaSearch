package com.example.albert.dotasearch.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.model.PlayerInfo;
import com.example.albert.dotasearch.model.PlayerOverviewCombine;
import com.example.albert.dotasearch.model.WinLose;
import com.example.albert.dotasearch.modelfactory.FactoryForPlayerInfoViewModel;
import com.example.albert.dotasearch.tabs.FragmentPlayerInfoPros;
import com.example.albert.dotasearch.tabs.FragmentPlayerInfoHeroes;
import com.example.albert.dotasearch.tabs.FragmentPlayerInfoMatches;
import com.example.albert.dotasearch.viewModel.PlayerInfoViewModel;

import java.text.DecimalFormat;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerInfoActivity extends AppCompatActivity {

    public static final String TAG = "PlayerInfoActivity";
    public static final int LAYOUT = R.layout.activity_player_info;

    public long accountId;
    public String personalName;
    public PlayerInfoViewModel viewModel;

    private FragmentPlayerInfoMatches fragmentPlayerInfoMatches;
    private FragmentPlayerInfoHeroes fragmentPlayerInfoHeroes;
    private FragmentPlayerInfoPros fragmentPlayerInfoPros;

    private PlayerInfo playerInfo;
    private WinLose winLose;
    private String urlPlayerImg;

    private LiveData<Boolean> isFavorite;
    private RequestManager glide;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.player_img)
    ImageView playerImg;
    @BindView(R.id.player_rank_tier_image)
    ImageView playerRankTierImage;
    @BindView(R.id.player_rank_tier_image_star)
    ImageView playerRankTierStarImage;
    @BindView(R.id.player_name)
    TextView playerName;
    @BindView(R.id.win_rate_value)
    TextView playerWinRate;
    @BindView(R.id.count_win)
    TextView countWin;
    @BindView(R.id.count_lose)
    TextView countLose;
    @BindView(R.id.solo_mmr_value)
    TextView playerSoloMMR;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        Log.d(TAG, "onCreate");

        ButterKnife.bind(this);

        accountId = getIntent().getLongExtra("accountId", 0);
        personalName = getIntent().getStringExtra("personalName");

        glide = Glide.with(this);

        fragmentPlayerInfoMatches = FragmentPlayerInfoMatches.newInstance(accountId);
        fragmentPlayerInfoHeroes = FragmentPlayerInfoHeroes.newInstance(accountId);
        fragmentPlayerInfoPros = FragmentPlayerInfoPros.newInstance(accountId);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.player_overview:
                        changeFragment(fragmentPlayerInfoMatches, "fragmentPlayerInfoMatches");
                        return true;
                    case R.id.pros:
                        changeFragment(fragmentPlayerInfoPros, "fragmentPlayerInfoPros");
                        return true;
                    case R.id.navigation_notifications:
                        changeFragment(fragmentPlayerInfoHeroes, "fragmentPlayerInfoHeroes");
                        return true;
                }
                return false;
            }
        });

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.player_overview);
        }

        viewModel = ViewModelProviders.of(this, new FactoryForPlayerInfoViewModel(accountId)).get(PlayerInfoViewModel.class);
        LiveData<PlayerOverviewCombine> playerFullInfo = viewModel.getPlayerFullInfo();
        playerFullInfo.observe(this, new Observer<PlayerOverviewCombine>() {
            @Override
            public void onChanged(@Nullable PlayerOverviewCombine playerOverviewCombine) {
                Log.e("FRAGMENT", "777777777777777777777");

                if (playerOverviewCombine != null) {

                    playerInfo = playerOverviewCombine.getPlayerInfo();
                    winLose = playerOverviewCombine.getWinLose();

                    if (playerInfo.getErrorMessage() == null && winLose.getErrorMessage() == null) {
                        setToolbarTitle(getRealPlayerName(playerInfo));
                        setPlayerSoloMMRNameImgAndRankTier(playerInfo);
                        setPlayerRecordAndWinRate(winLose);
                    }
                }
            }
        });

        isFavorite = viewModel.getIsFavoritePlayer();

        initToolbar();
    }

    public void setToolbarTitle(String name) {
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.statistics_player, name, accountId));
    }

    public void setPlayerRecordAndWinRate(WinLose winLose) {
        long win = winLose.getWin();
        long lose = winLose.getLose();
        double winRateNotRound = ((win * 1.0) / (lose + win)) * 100;

        countLose.setText(getResources().getString(R.string.record, lose));
        countWin.setText(getResources().getString(R.string.record, win));
        playerWinRate.setText(getWinRate(winRateNotRound));
    }

    public String getWinRate(double winRateNotRound) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(winRateNotRound) + "%";
    }

    public void setPlayerSoloMMRNameImgAndRankTier(PlayerInfo playerInfo) {
        long soloMmr = playerInfo.getSoloCompetitiveRank();
        String name = getRealPlayerName(playerInfo);

        if (playerInfo.getProfile() != null) {
            urlPlayerImg = playerInfo.getProfile().getAvatarfull();
        } else {
            Toast.makeText(this, "Данные об игроке отсутствуют", Toast.LENGTH_SHORT).show();
            finish();
        }

        long rankTier = playerInfo.getRankTier();
        long leaderBoard = playerInfo.getLeaderboardRank();

        if (soloMmr != 0) {
            playerSoloMMR.setText(this.getResources().getString(R.string.solo_mmr, soloMmr));
        }

        glide.load(urlPlayerImg)
                .error(glide.load(R.drawable.avatar_unknown_medium).apply(RequestOptions.circleCropTransform()))
                .apply(RequestOptions.circleCropTransform())
                .into(playerImg);
        playerName.setText(name);
        setRankTier(rankTier, leaderBoard);
    }

    public void setRankTier(long rank, long leaderBoard) {
        if (rank != 0) {
            long first = rank / 10;
            long second = rank - (first * 10);

            if (leaderBoard == 1) {
                playerRankTierImage.setImageResource(R.drawable.top_1_rank);
            } else if (first == 7 && second == 6) {

                String nameGeneralImg;
                if (leaderBoard <= 10) {
                    nameGeneralImg = "rank_icon_" + first + "c";
                } else if (leaderBoard <= 100) {
                    nameGeneralImg = "rank_icon_" + first + "b";
                } else {
                    nameGeneralImg = "rank_icon_" + first + "a";
                }

                int id = getResources().getIdentifier(nameGeneralImg, "drawable", getPackageName());
                playerRankTierImage.setImageResource(id);

            } else {
                String nameGeneralImg = "rank_icon_" + first;
                String nameStar = "rank_star_" + second;

                Log.d(TAG, "Img : " + nameGeneralImg + " " + second);
                int id = getResources().getIdentifier(nameGeneralImg, "drawable", getPackageName());
                int idStar = getResources().getIdentifier(nameStar, "drawable", getPackageName());

                playerRankTierImage.setImageResource(id);
                playerRankTierStarImage.setImageResource(idStar);
            }

        }
    }

    private void initToolbar() {
        /*if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation) {
            appBarLayout.setExpanded(false, true);
        } else {
            appBarLayout.setExpanded(true, true);
        }*/

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_player_info, menu);
        MenuItem itemStar = menu.getItem(0);
        if (isFavorite.getValue() != null && isFavorite.getValue()) {
            itemStar.setIcon(getResources().getDrawable(R.mipmap.ic_star_white_48dp));
        } else {
            itemStar.setIcon(getResources().getDrawable(R.mipmap.ic_star_outline_white_48dp));
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.star && isFavorite.getValue() != null && isFavorite.getValue()) {
            item.setIcon(getResources().getDrawable(R.mipmap.ic_star_outline_white_48dp));
            viewModel.deletePlayerWithFavoriteList(accountId);
            Snackbar.make(findViewById(R.id.navigation), "Пользователь удален из избранного", Snackbar.LENGTH_SHORT).show();
        } else if (id == R.id.star && !isFavorite.getValue()) {
            if (playerInfo != null) {
                viewModel.insertPlayerToFavoriteList(new FavoritePlayer(accountId, urlPlayerImg, getRealPlayerName(playerInfo)));
                item.setIcon(getResources().getDrawable(R.mipmap.ic_star_white_48dp));
                Snackbar.make(findViewById(R.id.navigation), "Пользователь добавлен в избранное", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(findViewById(R.id.navigation), "Пожалуйста дождитесь полной загрузки профиля игрока", Snackbar.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public String getRealPlayerName(PlayerInfo playerInfo) {
        String name = "";
        if (playerInfo.getProfile() != null) {
            if (playerInfo.getProfile().getName() != null) {
                name = playerInfo.getProfile().getName();
            } else {
                name = personalName;
            }
        }

        return name;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}