package com.example.albert.dotasearch.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.TabsFragmentPlayerInfoAdapter;
import com.example.albert.dotasearch.adapter.PlayerOverviewAdapter;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.model.PlayerInfo;
import com.example.albert.dotasearch.model.PlayerOverviewCombine;
import com.example.albert.dotasearch.model.WinLose;
import com.example.albert.dotasearch.modelfactory.FactoryForPlayerInfoViewModel;
import com.example.albert.dotasearch.viewModel.PlayerInfoViewModel;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerInfoActivity extends AppCompatActivity {

    public static final String TAG = "PlayerInfoActivity";
    public static final int LAYOUT = R.layout.activity_player_info;

    private ViewPager viewPager;
    public Toolbar toolbar;

    public static long accountId;
    public static String personalName;
    public static String lastMatchStr;

    private PlayerOverviewAdapter mAdapter;
    private List<MatchShortInfo> matchList;
    private Map<Integer, Hero> heroList;

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        ButterKnife.bind(this);

        accountId = getIntent().getLongExtra("accountId", 0);
        personalName = getIntent().getStringExtra("personalName");
        lastMatchStr = getIntent().getStringExtra("lastMatchStr");

        collapsingToolbarLayout.setTitle(getResources().getString(R.string.statistics_player, personalName, accountId));

        Toast.makeText(this, accountId + " ", Toast.LENGTH_SHORT).show();

        initToolbar();

        setAdapterAndRecyclerView();

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.GREEN);
        }*/

        PlayerInfoViewModel viewModel = ViewModelProviders.of(this, new FactoryForPlayerInfoViewModel(accountId)).get(PlayerInfoViewModel.class);
        heroList = viewModel.getHeroes();
        LiveData<PlayerOverviewCombine> playerFullInfo = viewModel.getPlayerFullInfo();
        playerFullInfo.observe(PlayerInfoActivity.this, new Observer<PlayerOverviewCombine>() {
            @Override
            public void onChanged(@Nullable PlayerOverviewCombine playerOverviewCombine) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "", Snackbar.LENGTH_SHORT).show();
                if(playerOverviewCombine != null && playerOverviewCombine.getMatches().size() > 0) {
                    List<MatchShortInfo> matchShortInfos = playerOverviewCombine.getMatches();
                    PlayerInfo playerInfo = playerOverviewCombine.getPlayerInfo();
                    WinLose winLose = playerOverviewCombine.getWinLose();

                    playerLastMatch.setText(generateLastMatchTime(matchShortInfos.get(0).getStartTime()));

                    setPlayerSoloMMRNameImgAndRankTier(playerInfo);
                    setPlayerRecordAndWinRate(winLose);

                    if (matchShortInfos.size() >= 20) {
                        matchList = matchShortInfos.subList(0, 20);
                        mAdapter.setData(matchList, heroList);
                        System.out.println(matchShortInfos);
                    } else if (matchShortInfos.size() >= 1) {
                        matchList = matchShortInfos;
                        mAdapter.setData(matchList, heroList);
                        System.out.println(matchShortInfos);
                    }
                }
            }
        });
        //initTabs();
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new PlayerOverviewAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.e("4444", position + " " + view);
                toMatchDetailActivity(position);
            }
        }));
    }
    public void toMatchDetailActivity(int position) {
        Log.e("44444444", "444444444444442");
        Intent intent = new Intent(this, MatchDetailActivity.class);
        intent.putExtra("matchId", matchList.get(position).getMatchId());
        //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


    public String generateLastMatchTime(long lastMatch) {
        String result;
        if (lastMatch != 0) {

            Calendar lastMatchTime = Calendar.getInstance();
            lastMatchTime.setTimeInMillis(lastMatch * 1000);

            Calendar now = Calendar.getInstance();

            long second = 1000;
            long minute = second * 60;
            long hour = minute * 60;
            long day = hour * 24;
            long month = day * 30;
            long year = month * 12;

            long diff = now.getTimeInMillis() - lastMatchTime.getTimeInMillis();

            if (diff / year == 0) {
                if (diff / month == 0) {
                    if (diff / day == 0) {
                        if (diff / hour == 0) {
                            if (diff / minute < 60) {
                                result = "less hour ago";
                            } else {
                                result = diff / year + " minute ago";
                            }
                        } else {
                            result = diff / hour + " hour ago";
                        }
                    } else {
                        result = diff / day + " day ago";
                    }
                } else {
                    result = diff / month + " month ago";
                }
            } else {
                result = diff / year + " year ago";
            }
        } else {
            result = "";
        }
        return result;
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

    public String getWinRate(double winRateNotRound) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(winRateNotRound) + "%";
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

    public void setPlayerRecordAndWinRate(WinLose winLose) {
        long win = winLose.getWin();
        long lose = winLose.getLose();
        String record = win + " - " + lose;
        double winRateNotRound = ((win * 1.0) / (lose + win)) * 100;

        countLose.setText(getResources().getString(R.string.record, lose));
        countWin.setText(getResources().getString(R.string.record, win));
        playerWinRate.setText(getWinRate(winRateNotRound));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_player_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initTabs() {
        viewPager = findViewById(R.id.viewPager);

        viewPager.setOffscreenPageLimit(2);

        TabsFragmentPlayerInfoAdapter adapter = new TabsFragmentPlayerInfoAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle(getResources().getString(R.string.statistics_player, personalName, accountId));

        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_left));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        //enable search to toolbar
        //toolbar.inflateMenu(R.menu.menu_fragment);
    }


}
