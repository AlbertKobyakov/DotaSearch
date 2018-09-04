package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerInfoMatchesAdapter extends RecyclerView.Adapter<PlayerInfoMatchesAdapter.MyViewHolder> {

    private static final String TAG = "PlayerMatchesAdapter";
    private static final int LAYOUT = R.layout.match_player_list_row;


    private List<MatchShortInfo> matchesCopy;
    private Map<Integer, Hero> heroes;
    public Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.match_hero)
        TextView hero;
        @BindView(R.id.match_result)
        TextView result;
        @BindView(R.id.match_duration)
        TextView duration;
        @BindView(R.id.match_kda)
        TextView kda;
        @BindView(R.id.imageView)
        ImageView imageView;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public PlayerInfoMatchesAdapter(Context context) {
        this.context = context;
    }


    @Override
    public PlayerInfoMatchesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new PlayerInfoMatchesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerInfoMatchesAdapter.MyViewHolder holder, int position) {

        if (matchesCopy != null) {
            MatchShortInfo matchShortInfo = matchesCopy.get(position);

            int heroId = (int) matchShortInfo.getHeroId();

            long minutes = matchShortInfo.getDuration() / 60;
            long seconds = matchShortInfo.getDuration() - (minutes * 60);

            String heroName;
            String heroImg = "";

            if(heroes.get(heroId) != null){
                heroImg = heroes.get(heroId).getImg();
                heroName = heroes.get(heroId).getLocalizedName();
            } else {
                heroName = context.getResources().getString(R.string.unknown);
            }

            holder.kda.setText(context.getResources().getString(R.string.kda, matchShortInfo.getKills(), matchShortInfo.getDeaths(), matchShortInfo.getAssists()));

            holder.duration.setText(context.getResources().getString(R.string.match_duration, minutes, seconds));

            holder.hero.setText(heroName);

            if (matchShortInfo.getPlayerSlot() >= 0 && matchShortInfo.getPlayerSlot() < 5 && matchShortInfo.isRadiantWin()) {
                holder.result.setText(context.getResources().getString(R.string.win));
            } else if (matchShortInfo.getPlayerSlot() >= 128 && matchShortInfo.getPlayerSlot() < 133 && !matchShortInfo.isRadiantWin()) {
                holder.result.setText(context.getResources().getString(R.string.win));
            } else {
                holder.result.setText(context.getResources().getString(R.string.lose));
            }

            Glide.with(context)
                    .load(heroImg)
                    .error(R.drawable.avatar_unknown_medium)
                    .fitCenter()
                    .into(holder.imageView);
        }
    }

    public void setData(List<MatchShortInfo> match, Map<Integer, Hero> heroes) {
        this.matchesCopy = match;
        this.heroes = heroes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.e(TAG, "kkk1111k");
        if (matchesCopy != null) {
            return matchesCopy.size();
        } else {
            return 0;
        }
    }
}
