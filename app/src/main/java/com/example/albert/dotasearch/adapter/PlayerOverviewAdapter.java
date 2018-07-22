package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerOverviewAdapter extends RecyclerView.Adapter<PlayerOverviewAdapter.MyViewHolder>{

    private static final String TAG = "PlayerOverviewAdapter";
    private static final int LAYOUT = R.layout.match_player_list_row;


    private List<MatchShortInfo> matchesCopy;
    private SparseArray<Hero> heroesMap;
    public Context context;

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

    public PlayerOverviewAdapter(List<MatchShortInfo> matches, List<Hero> heroes, Context context) {
        this.matchesCopy = new ArrayList<>(matches);
        this.context = context;
        this.heroesMap = convertListToMap(heroes);
    }

    public SparseArray<Hero> convertListToMap(List<Hero> heroesCopy){
        heroesMap = new SparseArray<>();
        for(int i = 0; i < heroesCopy.size(); i++){
            int heroId = (int)heroesCopy.get(i).getId();
            heroesMap.append(heroId, heroesCopy.get(i));
        }
        return heroesMap;
    }

    @Override
    public PlayerOverviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new PlayerOverviewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerOverviewAdapter.MyViewHolder holder, int position) {
        MatchShortInfo matchShortInfo = matchesCopy.get(position);

        long minutes = matchShortInfo.getDuration()/60;
        long seconds = matchShortInfo.getDuration()-(minutes*60);

        int heroId = (int)matchShortInfo.getHeroId();
        String heroName = heroesMap.get(heroId).getLocalizedName();
        String heroImg = heroesMap.get(heroId).getImg();

        holder.kda.setText(context.getResources().getString(R.string.kda, matchShortInfo.getKills(), matchShortInfo.getDeaths(), matchShortInfo.getAssists()));

        holder.duration.setText(context.getResources().getString(R.string.match_duration,minutes,seconds));

        holder.hero.setText(heroName);

        if(matchShortInfo.getPlayerSlot() >= 0 && matchShortInfo.getPlayerSlot() < 5 && matchShortInfo.isRadiantWin()){
            holder.result.setText(context.getResources().getString(R.string.win));
        } else if(matchShortInfo.getPlayerSlot() >= 128 && matchShortInfo.getPlayerSlot() < 133 && !matchShortInfo.isRadiantWin()){
            holder.result.setText(context.getResources().getString(R.string.win));
        } else {
            holder.result.setText(context.getResources().getString(R.string.lose));
        }

        //Picasso.with(context).load(heroImg).fit().into(holder.imageView);
        Glide.with(context)
                .load(heroImg)
                .fitCenter()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return matchesCopy.size();
    }
}
