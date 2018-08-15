package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.MatchDetailActivity;
import com.example.albert.dotasearch.adapter.PlayerOverviewAdapter;
import com.example.albert.dotasearch.model.FavoritePlayer;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.model.PlayerInfo;
import com.example.albert.dotasearch.model.PlayerOverviewCombine;
import com.example.albert.dotasearch.model.WinLose;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

import static com.example.albert.dotasearch.activity.PlayerInfoActivity.personalName;
import static com.example.albert.dotasearch.activity.PlayerInfoActivity.urlPlayer;
import static com.example.albert.dotasearch.activity.PlayerInfoActivity.viewModel;

public class TabPlayerOverview extends Fragment {

    private static final String TAG = "TabPlayerOverview";
    private static final int LAYOUT = R.layout.fragment_player_overview;

    private PlayerOverviewAdapter mAdapter;
    private List<MatchShortInfo> matchList;
    private Map<Integer, Hero> heroList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private long accountId;

    LiveData<Boolean> isFavorite;

    @BindView(R.id.toolbar) Toolbar toolbar;
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

    PlayerOverviewCombine playerOverviewCombine;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Log.e(TAG, "onCreate");

        accountId = viewModel.getAccountId();
        isFavorite = viewModel.getIsFavoritePlayer();
        /*isFavorite.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.d(TAG, aBoolean + "1");
            }
        });*/
        LiveData<PlayerOverviewCombine> playerFullInfo = viewModel.getPlayerFullInfo();
        playerOverviewCombine = playerFullInfo.getValue();
        heroList = viewModel.getHeroes();


        playerFullInfo.observe(this, new Observer<PlayerOverviewCombine>() {
            @Override
            public void onChanged(@Nullable PlayerOverviewCombine playerOverviewCombine) {
                Log.e(TAG,"7777777777777774777777" + (playerOverviewCombine!=null));
                initToolbar();

                setAdapterAndRecyclerView();

                if(playerOverviewCombine != null){
                    List<MatchShortInfo> matchShortInfos = playerOverviewCombine.getMatches();
                    PlayerInfo playerInfo = playerOverviewCombine.getPlayerInfo();
                    WinLose winLose = playerOverviewCombine.getWinLose();

                    playerLastMatch.setText(generateLastMatchTime(matchShortInfos.get(0).getStartTime()));

                    if (matchShortInfos.size() >= 1) {
                        matchList = matchShortInfos;
                        mAdapter.setData(matchList, heroList);
                    }

                    setPlayerSoloMMRNameImgAndRankTier(playerInfo);
                    setPlayerRecordAndWinRate(winLose);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);

        Log.e(TAG, "onCreateView");
        ButterKnife.bind(this, view);

        return view;
    }

    public void setAdapterAndRecyclerView() {
        mAdapter = new PlayerOverviewAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                toMatchDetailActivity(position);
            }
        }));
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

                int id = getResources().getIdentifier(nameGeneralImg, "drawable", getActivity().getPackageName());
                playerRankTierImage.setImageResource(id);

            } else {
                String nameGeneralImg = "rank_icon_" + first;
                String nameStar = "rank_star_" + second;

                Log.d(TAG, "Img : " + nameGeneralImg + " " + second);
                int id = getResources().getIdentifier(nameGeneralImg, "drawable", getActivity().getPackageName());
                int idStar = getResources().getIdentifier(nameStar, "drawable", getActivity().getPackageName());

                playerRankTierImage.setImageResource(id);
                playerRankTierStarImage.setImageResource(idStar);
            }
        }
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

        Picasso.with(getActivity()).load(urlPlayerImg).into(playerImg);
        playerName.setText(playerPersonalName);
        setRankTier(rankTier, leaderBoard);
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

    public void toMatchDetailActivity(int position) {
        Log.e("44444444", "444444444444442");
        Intent intent = new Intent(getActivity(), MatchDetailActivity.class);
        intent.putExtra("matchId", matchList.get(position).getMatchId());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.dispose();
    }

    private void initToolbar() {
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.statistics_player, personalName, accountId));
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_left));
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> (getActivity()).onBackPressed());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_for_player_info, menu);
        MenuItem itemStar = menu.getItem(0);
        if(isFavorite.getValue()){
            itemStar.setIcon(getResources().getDrawable(R.mipmap.ic_star_white_48dp));
        } else {
            itemStar.setIcon(getResources().getDrawable(R.mipmap.ic_star_outline_white_48dp));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.star && isFavorite.getValue()){
            item.setIcon(getResources().getDrawable(R.mipmap.ic_star_outline_white_48dp));
            viewModel.deletePlayerWithFavoriteList(accountId);
            Snackbar.make(getActivity().findViewById(R.id.navigation), "Пользователь удален из избранного", Snackbar.LENGTH_SHORT).show();
        } else if(id == R.id.star && !isFavorite.getValue()){
            Log.d(TAG, "click");
            viewModel.insertPlayerToFavoriteList(new FavoritePlayer(accountId, urlPlayer, personalName));
            //FavoritePlayersViewModel viewModel1 = ViewModelProviders.of(getActivity()).get(FavoritePlayersViewModel.class);
            //viewModel1.insertPlayerToFavoriteList(new FavoritePlayer(accountId, urlPlayer, personalName));
            item.setIcon(getResources().getDrawable(R.mipmap.ic_star_white_48dp));
            Snackbar.make(getActivity().findViewById(R.id.navigation), "Пользователь добавлен в избранное", Snackbar.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}