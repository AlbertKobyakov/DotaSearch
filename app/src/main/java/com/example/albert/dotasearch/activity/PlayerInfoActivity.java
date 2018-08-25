package com.example.albert.dotasearch.activity;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.model.PlayerInfo;
import com.example.albert.dotasearch.model.PlayerOverviewCombine;
import com.example.albert.dotasearch.model.WinLose;
import com.example.albert.dotasearch.modelfactory.FactoryForPlayerInfoViewModel;
import com.example.albert.dotasearch.tabs.FragmentPlayerInfoPros;
import com.example.albert.dotasearch.tabs.FragmentPlayerInfoHeroes;
import com.example.albert.dotasearch.tabs.TabPlayerMatches;
import com.example.albert.dotasearch.viewModel.PlayerInfoViewModel;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerInfoActivity extends AppCompatActivity {

    public static final String TAG = "PlayerInfoActivity";
    public static final int LAYOUT = R.layout.activity_player_info;

    public static long accountId;
    public static String personalName;
    public static String urlPlayer;
    public static PlayerInfoViewModel viewModel;

    private TabPlayerMatches tabPlayerMatches;
    private FragmentPlayerInfoPros fragmentPlayerInfoPros;
    private FragmentPlayerInfoHeroes fragmentPlayerInfoHeroes;

    LiveData<Boolean> isFavorite;

    @BindView(R.id.toolbar) Toolbar toolbar;
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
    @BindView(R.id.last_match_value)
    TextView playerLastMatch;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.player_overview:
                    tabPlayerMatches = new TabPlayerMatches();
                    changeFragment(tabPlayerMatches);
                    return true;
                case R.id.pros:
                    fragmentPlayerInfoPros = new FragmentPlayerInfoPros();
                    changeFragment(fragmentPlayerInfoPros);
                    return true;
                case R.id.navigation_notifications:
                    fragmentPlayerInfoHeroes = new FragmentPlayerInfoHeroes();
                    changeFragment(fragmentPlayerInfoHeroes);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState==null){
            navigation.setSelectedItemId(R.id.player_overview);
        }

        accountId = getIntent().getLongExtra("accountId", 0);
        personalName = getIntent().getStringExtra("personalName");
        urlPlayer = getIntent().getStringExtra("urlPlayer");

        viewModel = ViewModelProviders.of(this, new FactoryForPlayerInfoViewModel(accountId)).get(PlayerInfoViewModel.class);
        LiveData<PlayerOverviewCombine> playerFullInfo = viewModel.getPlayerFullInfo();
        playerFullInfo.observe(this, new Observer<PlayerOverviewCombine>() {
            @Override
            public void onChanged(@Nullable PlayerOverviewCombine playerOverviewCombine) {
                Log.e("FRAGMENT","777777777777777777777");
                if (navigation.getVisibility() == View.INVISIBLE){
                    navigation.setVisibility(View.VISIBLE);
                }

                Log.e(TAG,"7777777777777774777777" + (playerOverviewCombine!=null));
                initToolbar();

                if(playerOverviewCombine != null){
                    PlayerInfo playerInfo = playerOverviewCombine.getPlayerInfo();
                    WinLose winLose = playerOverviewCombine.getWinLose();

                    setPlayerSoloMMRNameImgAndRankTier(playerInfo);
                    setPlayerRecordAndWinRate(winLose);
                }
            }
        });

        isFavorite = viewModel.getIsFavoritePlayer();
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
        String urlPlayerImg = playerInfo.getProfile().getAvatarfull();
        String playerPersonalName = playerInfo.getProfile().getPersonaname();
        long rankTier = playerInfo.getRankTier();
        long leaderBoard = playerInfo.getLeaderboardRank();

        if (soloMmr != 0) {
            playerSoloMMR.setText(this.getResources().getString(R.string.solo_mmr, soloMmr));
        }

        Picasso.with(this).load(urlPlayerImg).into(playerImg);
        playerName.setText(playerPersonalName);
        setRankTier(rankTier, leaderBoard);
    }

    public void setRankTier(long rank, long leaderBoard) {
        if (rank != 0) {
            long first = rank / 10;
            long second = rank - (first * 10);

            if (first == 7 && second == 6) {

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
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.statistics_player, personalName, accountId));
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_left));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_player_info, menu);
        MenuItem itemStar = menu.getItem(0);
        if(isFavorite.getValue()){
            itemStar.setIcon(getResources().getDrawable(R.mipmap.ic_star_white_48dp));
        } else {
            itemStar.setIcon(getResources().getDrawable(R.mipmap.ic_star_outline_white_48dp));
        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.star && isFavorite.getValue()){
            item.setIcon(getResources().getDrawable(R.mipmap.ic_star_outline_white_48dp));
            viewModel.deletePlayerWithFavoriteList(accountId);
            Snackbar.make(getWindow().getDecorView().getRootView(), "Пользователь удален из избранного", Snackbar.LENGTH_SHORT).show();
        } else if(id == R.id.star && !isFavorite.getValue()){
            Log.d(TAG, "click");
            viewModel.insertPlayerToFavoriteList(new FavoritePlayer(accountId, urlPlayer, personalName));
            item.setIcon(getResources().getDrawable(R.mipmap.ic_star_white_48dp));
            Snackbar.make(getWindow().getDecorView().getRootView(), "Пользователь добавлен в избранное", Snackbar.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
