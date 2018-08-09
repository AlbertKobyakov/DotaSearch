package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchPlayerAdapter extends RecyclerView.Adapter<MatchPlayerAdapter.MyViewHolder>{
    private List<MatchShortInfo> matchesCopy;
    private Map<Integer, Hero> heroesMap;
    public Context context;
    private static final String TAG = "MatchPlayerAdapter";

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.match_hero) TextView hero;
        @BindView(R.id.match_result) TextView result;
        @BindView(R.id.match_duration) TextView duration;
        @BindView(R.id.match_kda) TextView kda;
        @BindView(R.id.imageView) ImageView imageView;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public MatchPlayerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MatchPlayerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_player_list_row, parent, false);

        return new MatchPlayerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MatchPlayerAdapter.MyViewHolder holder, int position) {
        if(matchesCopy != null) {
            MatchShortInfo matchShortInfo = matchesCopy.get(position);

            long minutes = matchShortInfo.getDuration() / 60;
            long seconds = matchShortInfo.getDuration() - (minutes * 60);

            int heroId = (int) matchShortInfo.getHeroId();

            String heroName = heroesMap.get(heroId).getLocalizedName();

            String heroImg = heroesMap.get(heroId).getImg();

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

            Picasso.with(context).load(heroImg).fit().into(holder.imageView);
        } else {
            Log.d(TAG, "empty");
        }
    }

    public void setData(List<MatchShortInfo> match, Map<Integer, Hero> heroes){
        matchesCopy = match;
        heroesMap = heroes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(matchesCopy != null){
            return matchesCopy.size();
        } else {
            return 0;
        }
    }
}
