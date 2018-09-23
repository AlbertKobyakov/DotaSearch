package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.Pros;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerProsAdapter extends RecyclerView.Adapter<PlayerProsAdapter.MyViewHolder> {
    private List<Pros> pros;
    public Context context;
    private static final String TAG = "PlayerProsAdapter";
    private static final int LAYOUT = R.layout.player_pros_list_row;
    private RequestManager glide;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pro_name)
        TextView proName;
        @BindView(R.id.pro_team)
        TextView proTeam;
        @BindView(R.id.pro_all_matches)
        TextView proAllMatches;
        @BindView(R.id.pros_avatar)
        ImageView prosAvatar;

        @Nullable
        @BindView(R.id.matches_with_win)
        TextView matchesWithWin;
        @Nullable
        @BindView(R.id.matches_against_win)
        TextView matchesAgainstWin;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public PlayerProsAdapter(Context context, RequestManager glide) {
        this.context = context;
        this.glide = glide;
    }

    @NonNull
    @Override
    public PlayerProsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new PlayerProsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerProsAdapter.MyViewHolder holder, int position) {
        if (pros != null) {
            Pros proPlayer = pros.get(position);

            holder.proName.setText(proPlayer.getName());

            if (proPlayer.getTeamName() != null && proPlayer.getTeamName().trim().length() > 0) {
                holder.proTeam.setText(proPlayer.getTeamName());
            } else {
                holder.proTeam.setText(R.string.unknown);
            }

            holder.proAllMatches.setText(context.getResources().getString(R.string.pro_all_matches, proPlayer.getGames()));

            RequestOptions fitCenter = new RequestOptions()
                    .fitCenter();

            glide.load(proPlayer.getAvatarmedium())
                    .error(glide.load(R.drawable.avatar_unknown_medium))
                    .apply(fitCenter)
                    .into(holder.prosAvatar);

            if (Configuration.ORIENTATION_LANDSCAPE == context.getResources().getConfiguration().orientation) {
                double withWin = (double) proPlayer.getWithWin() / (double) proPlayer.getWithGames() * 100;
                double againstWin = (double) proPlayer.getAgainstWin() / (double) proPlayer.getAgainstGames() * 100;

                if (proPlayer.getWithWin() != 0) {
                    holder.matchesWithWin.setText(context.getResources().getString(R.string.matches_with_win, twoNumberAfterPoint(withWin, proPlayer.getWithGames())));
                } else {
                    holder.matchesWithWin.setText(context.getResources().getString(R.string.matches_with_win, "0"));
                }

                if (proPlayer.getAgainstWin() != 0) {
                    holder.matchesAgainstWin.setText(context.getResources().getString(R.string.matches_with_win, twoNumberAfterPoint(againstWin, proPlayer.getAgainstGames())));
                } else {
                    holder.matchesAgainstWin.setText(context.getResources().getString(R.string.matches_with_win, "0"));
                }
            }

        } else {
            Log.d(TAG, "empty");
        }
    }

    private String twoNumberAfterPoint(double number, long allGames) {
        DecimalFormat df = new DecimalFormat("#.##");
        return allGames + " (" + df.format(number) + "%)";
    }

    public void setData(List<Pros> prosResponse) {
        pros = prosResponse;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (pros != null) {
            return pros.size();
        } else {
            return 0;
        }
    }
}