package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.MatchShortInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchPlayerAdapter extends RecyclerView.Adapter<MatchPlayerAdapter.MyViewHolder>{
    //private List<ProPlayer> proPlayers;
    private List<MatchShortInfo> matchesCopy;
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


    public MatchPlayerAdapter(List<MatchShortInfo> proPlayerList, Context context) {
        //this.proPlayers = new ArrayList<>(proPlayerList);
        this.matchesCopy = new ArrayList<>(proPlayerList);
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
        MatchShortInfo matchShortInfo = matchesCopy.get(position);

        long minutes = matchShortInfo.getDuration()/60;
        long seconds = matchShortInfo.getDuration()-(minutes*60);

        holder.kda.setText(context.getResources().getString(R.string.kda, matchShortInfo.getKills(), matchShortInfo.getDeaths(), matchShortInfo.getAssists()));

        holder.duration.setText(context.getResources().getString(R.string.match_duration,minutes,seconds));

        holder.hero.setText(matchShortInfo.getHeroName());

        /*for(Hero hero : MainActivity.heroList){
            if(matchShortInfo.getHeroId() == hero.getId()){
                holder.hero.setText(hero.getLocalizedName());
                break;
            }
        }/*/

        if(matchShortInfo.getPlayerSlot() >= 0 && matchShortInfo.getPlayerSlot() < 5 && matchShortInfo.isRadiantWin()){
            holder.result.setText(context.getResources().getString(R.string.win));
        } else if(matchShortInfo.getPlayerSlot() >= 128 && matchShortInfo.getPlayerSlot() < 133 && !matchShortInfo.isRadiantWin()){
            holder.result.setText(context.getResources().getString(R.string.win));
        } else {
            holder.result.setText(context.getResources().getString(R.string.lose));
        }

        Picasso.with(context).load("https://api.opendota.com" + matchShortInfo.getImgUrl()).fit().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return matchesCopy.size();
    }
}
