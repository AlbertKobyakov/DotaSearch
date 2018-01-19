package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.activity.MainActivity;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Match;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchPlayerAdapter extends RecyclerView.Adapter<MatchPlayerAdapter.MyViewHolder>{
    //private List<ProPlayer> proPlayers;
    private List<Match> matchesCopy;
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


    public MatchPlayerAdapter(List<Match> proPlayerList, Context context) {
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
        Match match = matchesCopy.get(position);

        long minutes = match.getDuration()/60;
        long seconds = match.getDuration()-(minutes*60);

        holder.kda.setText(context.getResources().getString(R.string.kda, match.getKills(), match.getDeaths(), match.getAssists()));

        holder.duration.setText(context.getResources().getString(R.string.match_duration,minutes,seconds));

        holder.hero.setText(match.getHeroName());

        /*for(Hero hero : MainActivity.heroList){
            if(match.getHeroId() == hero.getId()){
                holder.hero.setText(hero.getLocalizedName());
                break;
            }
        }*/

        if(match.getPlayerSlot() >= 0 && match.getPlayerSlot() < 5 && match.isRadiantWin()){
            holder.result.setText(context.getResources().getString(R.string.win));
        } else if(match.getPlayerSlot() >= 128 && match.getPlayerSlot() < 133 && !match.isRadiantWin()){
            holder.result.setText(context.getResources().getString(R.string.win));
        } else {
            holder.result.setText(context.getResources().getString(R.string.lose));
        }

        Picasso.with(context).load("https://api.opendota.com" + match.getImgUrl()).fit().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return matchesCopy.size();
    }
}
