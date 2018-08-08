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
import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.MatchDetailActivity;
import com.example.albert.dotasearch.adapter.PlayerOverviewAdapter;
import com.example.albert.dotasearch.model.HeaderPlayerContainsWinLoseAndPlayerInfo;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.example.albert.dotasearch.model.PlayerInfo;
import com.example.albert.dotasearch.model.WinLose;
import com.example.albert.dotasearch.modelfactory.FactoryForPlayerInfoViewModel;
import com.example.albert.dotasearch.util.UtilDota;
import com.example.albert.dotasearch.viewModel.PlayerInfoViewModel;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.albert.dotasearch.activity.PlayerInfoActivity.accountId;

public class TabPlayerOverview extends AbstractTabFragment {

    private static final String TAG = "TabPlayerOverview";
    private static final int LAYOUT = R.layout.fragment_player_overview;

    private PlayerOverviewAdapter mAdapter;
    private List<MatchShortInfo> matchList;
    private List<Hero> heroList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        PlayerInfoViewModel viewModel = ViewModelProviders.of(this, new FactoryForPlayerInfoViewModel(accountId)).get(PlayerInfoViewModel.class);
        LiveData<List<MatchShortInfo>> matches = viewModel.getMatches();
        matches.observe(getActivity(), new Observer<List<MatchShortInfo>>() {
            @Override
            public void onChanged(@Nullable List<MatchShortInfo> matchShortInfos) {
                heroList = viewModel.getHeroes();
                if(matchShortInfos != null && matchShortInfos.size() >= 20){
                    matchList = matchShortInfos.subList(0, 20);
                }

                setAdapterAndRecyclerView();
                System.out.println(matchShortInfos);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        Log.e(TAG, "onCreateView");

        ButterKnife.bind(this, view);

        setHeader();

        return view;
    }

    public Observable<HeaderPlayerContainsWinLoseAndPlayerInfo> getHeaderPlayerInfoByNetwork(){
        Observable<PlayerInfo> playerInfo = UtilDota.initRetrofitRx()
                .getPlayerInfoById(accountId)
                .subscribeOn(Schedulers.io());

        Observable<WinLose> winLose = UtilDota.initRetrofitRx()
                .getPlayerWinLoseById(accountId)
                .subscribeOn(Schedulers.io());

        return Observable.zip(
                playerInfo,
                winLose,
                (playerInfo1, winLose1) -> new HeaderPlayerContainsWinLoseAndPlayerInfo(playerInfo1, winLose1, accountId));
    }

    public void setHeader(){
        Disposable dis1 = Maybe.fromCallable(() -> App.get().getDB().headerPlayerInfoDao()
                .getHeaderPlayerInfoByIdRx(accountId))
                .toObservable()
                .defaultIfEmpty(new HeaderPlayerContainsWinLoseAndPlayerInfo(new PlayerInfo(), new WinLose(), 0))
                .flatMap(new Function<HeaderPlayerContainsWinLoseAndPlayerInfo, ObservableSource<HeaderPlayerContainsWinLoseAndPlayerInfo>>() {
                             @Override
                             public ObservableSource<HeaderPlayerContainsWinLoseAndPlayerInfo> apply(HeaderPlayerContainsWinLoseAndPlayerInfo headerPlayerContainsWinLoseAndPlayerInfo) throws Exception {
                                 if(headerPlayerContainsWinLoseAndPlayerInfo.playerId == 0){
                                     return getHeaderPlayerInfoByNetwork();
                                 } else {
                                     return Observable.just(headerPlayerContainsWinLoseAndPlayerInfo);
                                 }
                             }
                         }
                ).flatMap(new Function<HeaderPlayerContainsWinLoseAndPlayerInfo, ObservableSource<HeaderPlayerContainsWinLoseAndPlayerInfo>>() {
                    @Override
                    public ObservableSource<HeaderPlayerContainsWinLoseAndPlayerInfo> apply(HeaderPlayerContainsWinLoseAndPlayerInfo headerPlayerContainsWinLoseAndPlayerInfo) throws Exception {
                        deleteAllHeaderAndInsetCurrent(headerPlayerContainsWinLoseAndPlayerInfo);
                        return Observable.just(headerPlayerContainsWinLoseAndPlayerInfo);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                                System.out.println(user + "444");
                                setPlayerSoloMMRNameImgAndRankTier(user.getPlayerInfo());
                                setPlayerRecordAndWinRate(user.getWinLose());
                            },
                        Throwable::printStackTrace);
        compositeDisposable.add(dis1);
    }

    public void deleteAllHeaderAndInsetCurrent(HeaderPlayerContainsWinLoseAndPlayerInfo header){
        App.get().getDB().headerPlayerInfoDao().deleteAll();
        App.get().getDB().headerPlayerInfoDao().insert(header);
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

    public void setLocalArrays(HeroAndMatch heroAndMatch) {
        matchList = heroAndMatch.matches;
        heroList = heroAndMatch.heroes;

        playerLastMatch.setText(generateLastMatchTime(matchList.get(0).getStartTime()));
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

    public void setAdapterAndRecyclerView() {
        mAdapter = new PlayerOverviewAdapter(matchList, heroList, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                toMatchDetailActivity(position);
            }
        }));
    }

    public void toMatchDetailActivity(int position) {
        Intent intent = new Intent(getActivity(), MatchDetailActivity.class);
        intent.putExtra("matchId", matchList.get(position).getMatchId());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.dispose();
    }

    class HeroAndMatch {
        List<MatchShortInfo> matches;
        List<Hero> heroes;

        private HeroAndMatch(List<MatchShortInfo> matches, List<Hero> heroes) {
            this.matches = matches;
            this.heroes = heroes;
        }
    }
}