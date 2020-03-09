package com.kobyakov.d2s.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.model.MatchShortInfo;

import java.util.List;
import java.util.Objects;

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

        @BindView(R.id.match_duration)
        TextView duration;
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

            //if (Configuration.ORIENTATION_LANDSCAPE == context.getResources().getConfiguration().orientation) {
            long seconds = matchShortInfo.getDuration();
            String duration = DateUtils.formatElapsedTime(seconds);
            long secondsMatchStartTime = (matchShortInfo.getStartTime()) * 1000;
            String matchStartDate = DateUtils.getRelativeTimeSpanString(secondsMatchStartTime).toString();

            Objects.requireNonNull(holder.duration).setText(context.getResources().getString(R.string.match_duration, duration));
            Objects.requireNonNull(holder.date).setText(matchStartDate);
            //}

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