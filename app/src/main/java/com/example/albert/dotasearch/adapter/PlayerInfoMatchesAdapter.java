package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.MatchShortInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerInfoMatchesAdapter extends RecyclerView.Adapter<PlayerInfoMatchesAdapter.MyViewHolder> {
    private static final String TAG = "PlayerMatchesAdapter";
    private static final int LAYOUT = R.layout.match_player_list_row;

    private List<MatchShortInfo> matchesCopy;
    public Context context;
    private RequestManager glide;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.match_hero)
        TextView hero;
        @BindView(R.id.match_result)
        TextView result;
        @BindView(R.id.match_kda)
        TextView kda;
        @BindView(R.id.imageView)
        ImageView imageView;

        @Nullable
        @BindView(R.id.match_duration)
        TextView duration;
        @Nullable
        @BindView(R.id.match_date)
        TextView date;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public PlayerInfoMatchesAdapter(Context context, RequestManager glide) {
        this.context = context;
        this.glide = glide;
    }


    @NonNull
    @Override
    public PlayerInfoMatchesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new PlayerInfoMatchesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerInfoMatchesAdapter.MyViewHolder holder, int position) {
        if (matchesCopy != null) {
            MatchShortInfo matchShortInfo = matchesCopy.get(position);

            if (Configuration.ORIENTATION_LANDSCAPE == context.getResources().getConfiguration().orientation) {
                long seconds = matchShortInfo.getDuration();
                String duration = DateUtils.formatElapsedTime(seconds);

                String matchStartDate = DateUtils.getRelativeTimeSpanString(
                        matchShortInfo.getStartTime() * 1000
                ).toString();

                holder.duration.setText(context.getResources().getString(R.string.match_duration, duration));
                holder.date.setText(matchStartDate);

            }

            String heroImg = matchShortInfo.getHeroImageUrl();
            String heroName = matchShortInfo.getHeroName();

            if (heroName.trim().length() == 0) {
                heroName = context.getResources().getString(R.string.unknown);
            }

            holder.kda.setText(context.getResources().getString(R.string.kda, matchShortInfo.getKills(), matchShortInfo.getDeaths(), matchShortInfo.getAssists()));

            holder.hero.setText(heroName);

            if (matchShortInfo.getPlayerSlot() >= 0 && matchShortInfo.getPlayerSlot() < 5 && matchShortInfo.isRadiantWin()) {
                holder.result.setText(context.getResources().getString(R.string.win));
            } else if (matchShortInfo.getPlayerSlot() >= 128 && matchShortInfo.getPlayerSlot() < 133 && !matchShortInfo.isRadiantWin()) {
                holder.result.setText(context.getResources().getString(R.string.win));
            } else {
                holder.result.setText(context.getResources().getString(R.string.lose));
            }

            glide.load(heroImg)
                    .error(glide.load(R.drawable.avatar_unknown_medium))
                    .into(holder.imageView);
        }
    }

    public void setData(List<MatchShortInfo> match) {
        this.matchesCopy = match;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (matchesCopy != null) {
            return matchesCopy.size();
        } else {
            return 0;
        }
    }
}