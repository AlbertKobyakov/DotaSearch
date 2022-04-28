package com.kobyakov.d2s.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.model.Leaderboard;
import com.kobyakov.d2s.model.TimeRefreshLeaderBoard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> /*implements AnimationForRecyclerViewItems*/ {
    private static final String TAG = "RankingAdapter";
    private static final int LAYOUT = R.layout.leader_board_list_row;

    private TimeRefreshLeaderBoard timeRefreshLeaderBoardTemp;
    private List<Leaderboard> leaderboardList;
    private List<Leaderboard> leaderboardListForAdapter;
    public Context context;
    private RequestManager glide;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.position_text)
        TextView position;
        @BindView(R.id.name_player)
        TextView name;
        @BindView(R.id.team_player)
        TextView team;
        @BindView(R.id.country_flag)
        ImageView countryFlag;
        @BindView(R.id.rank_img)
        ImageView rankImg;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public RankingAdapter(Context context, RequestManager glide) {
        this.context = context;
        this.glide = glide;
    }

    @NonNull
    @Override
    public RankingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new RankingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingAdapter.MyViewHolder holder, int position) {
        if (timeRefreshLeaderBoardTemp != null) {
            Leaderboard leaderboard = leaderboardListForAdapter.get(position);

            long playerPosition = leaderboard.getRank();
            String teamTag = leaderboard.getTeamTag();

            if (leaderboard.getRank() == 1) {
                holder.rankImg.setVisibility(View.VISIBLE);
                holder.rankImg.setImageResource(R.drawable.star_circle_outline_gold);
                holder.position.setVisibility(View.GONE);
            } else if (leaderboard.getRank() == 2) {
                holder.rankImg.setVisibility(View.VISIBLE);
                holder.rankImg.setImageResource(R.drawable.star_circle_outline_silver);
                holder.position.setVisibility(View.GONE);
            } else if (leaderboard.getRank() == 3) {
                holder.rankImg.setVisibility(View.VISIBLE);
                holder.rankImg.setImageResource(R.drawable.star_circle_outline_bronze);
                holder.position.setVisibility(View.GONE);
            } else {
                holder.rankImg.setVisibility(View.GONE);
                holder.position.setVisibility(View.VISIBLE);
                holder.position.setText(context.getResources().getString(R.string.ranking_player_position, playerPosition));
            }

            holder.name.setText(leaderboard.getName());

            if (teamTag != null && teamTag.trim().length() > 0) {
                holder.team.setText(leaderboard.getTeamTag());
            } else {
                holder.team.setText("Нет");
            }

            if (leaderboard.getCountry() != null) {

                final int resourceId = getResourceDrawableByName(leaderboard.getCountry(), context);

                RequestOptions fitCenter = new RequestOptions()
                        .fitCenter();

                //glide.load("https://www.countryflags.io/" + leaderboard.getCountry() + "/flat/64.png")
                glide.load(resourceId)
                        .error(glide.load(R.drawable.avatar_unknown_medium))
                        .apply(fitCenter)
                        .into(holder.countryFlag);
            } else {
                glide.clear(holder.countryFlag);
            }

            //setFadeAnimationVerTwo(holder.itemView);
        }
    }

    public void setData(TimeRefreshLeaderBoard timeRefreshLeaderBoard) {
        timeRefreshLeaderBoardTemp = timeRefreshLeaderBoard;
        leaderboardList = new ArrayList<>(timeRefreshLeaderBoard.getLeaderboard());
        leaderboardListForAdapter = new ArrayList<>(timeRefreshLeaderBoard.getLeaderboard());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (leaderboardListForAdapter != null) {
            return leaderboardListForAdapter.size();
        } else {
            return 0;
        }
    }

    public void filter(String text) {
        List<Leaderboard> leaderboardListFiltered = new ArrayList<>();

        if (!text.isEmpty()) {
            text = text.toLowerCase();
            for (Leaderboard leaderboard : leaderboardList) {
                if (leaderboard != null) {
                    if (leaderboard.getName().toLowerCase().contains(text)) {
                        leaderboardListFiltered.add(leaderboard);
                    }
                }
            }
        } else {
            leaderboardListFiltered = leaderboardList;
        }

        leaderboardListForAdapter = leaderboardListFiltered;

        notifyDataSetChanged();
    }

    private int getResourceDrawableByName(String nameDrawable, Context context){
        Resources resources = context.getResources();
        return resources.getIdentifier(nameDrawable, "drawable", context.getPackageName());
    }
}