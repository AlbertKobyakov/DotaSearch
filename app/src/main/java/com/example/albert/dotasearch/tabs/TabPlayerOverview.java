package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.MatchDetailActivity;
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
import io.reactivex.disposables.CompositeDisposable;

import static com.example.albert.dotasearch.activity.PlayerInfoActivity.accountId;

public class TabPlayerOverview extends AbstractTabFragment {

    private static final String TAG = "TabPlayerOverview";
    private static final int LAYOUT = R.layout.fragment_player_overview;

    private PlayerOverviewAdapter mAdapter;
    private List<MatchShortInfo> matchList;
    private Map<Integer, Hero> heroList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static PlayerInfoViewModel viewModel;

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
    @BindView(R.id.record_value)
    TextView playerRecord;
    @BindView(R.id.solo_mmr_value)
    TextView playerSoloMMR;
    @BindView(R.id.last_match_value)
    TextView playerLastMatch;

    public static TabPlayerOverview getInstance(Context context) {
        Bundle args = new Bundle();
        TabPlayerOverview fragment = new TabPlayerOverview();
        fragment.setArguments(args);
        fragment.setTitle(context.getString(R.string.overview));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, new FactoryForPlayerInfoViewModel(accountId)).get(PlayerInfoViewModel.class);
        heroList = viewModel.getHeroes();
        LiveData<PlayerOverviewCombine> playerFullInfo = viewModel.getPlayerFullInfo();
        playerFullInfo.observe(getActivity(), new Observer<PlayerOverviewCombine>() {
            @Override
            public void onChanged(@Nullable PlayerOverviewCombine playerOverviewCombine) {
                if(playerOverviewCombine != null) {
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        Log.e(TAG, "onCreateView");

        ButterKnife.bind(this, view);

        setAdapterAndRecyclerView();

        return view;
    }


    public void setAdapterAndRecyclerView() {
        mAdapter = new PlayerOverviewAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.e("4444", position + " " + view);
                toMatchDetailActivity(position);
            }
        }));
    }

    public void setPlayerSoloMMRNameImgAndRankTier(PlayerInfo playerInfo) {
        long soloMmr = playerInfo.getSoloCompetitiveRank();
        String urlPlayerImg = playerInfo.getProfile().getAvatarfull();
        String playerPersonalName = playerInfo.getProfile().getPersonaname();
        long rankTier = playerInfo.getRankTier();
        long leaderBoard = playerInfo.getLeaderboardRank();

        if (soloMmr != 0) {
            playerSoloMMR.setText(getActivity().getResources().getString(R.string.solo_mmr, soloMmr));
        }

        Picasso.with(getActivity()).load(urlPlayerImg).into(playerImg);
        playerName.setText(playerPersonalName);
        setRankTier(rankTier, leaderBoard);
    }

    public void setPlayerRecordAndWinRate(WinLose winLose) {
        long win = winLose.getWin();
        long lose = winLose.getLose();
        String record = win + " - " + lose;
        double winRateNotRound = ((win * 1.0) / (lose + win)) * 100;

        playerRecord.setText(record);
        playerWinRate.setText(getWinRate(winRateNotRound));
    }

    public void errorHandling(Throwable throwable) {
        Log.e(TAG, throwable.getLocalizedMessage());
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

                int id = getActivity().getResources().getIdentifier(nameGeneralImg, "drawable", context.getPackageName());
                playerRankTierImage.setImageResource(id);

            } else {
                String nameGeneralImg = "rank_icon_" + first;
                String nameStar = "rank_star_" + second;

                Log.d(TAG, "Img : " + nameGeneralImg + " " + second);
                int id = getActivity().getResources().getIdentifier(nameGeneralImg, "drawable", getActivity().getPackageName());
                int idStar = getActivity().getResources().getIdentifier(nameStar, "drawable", getActivity().getPackageName());

                playerRankTierImage.setImageResource(id);
                playerRankTierStarImage.setImageResource(idStar);
            }
        }
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
        //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.dispose();
    }
}