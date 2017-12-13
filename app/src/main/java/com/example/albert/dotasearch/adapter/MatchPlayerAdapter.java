package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.Match;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchPlayerAdapter extends RecyclerView.Adapter<MatchPlayerAdapter.MyViewHolder>{
    //private List<ProPlayer> proPlayers;
    private List<Match> proPlayersCopy;
    public Context context;


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_pro_player) TextView nameProPlayer;
        @BindView(R.id.personal_name_pro_playere) TextView personalNameProPlayer;
        @BindView(R.id.teame_name_pro_playere) TextView teamNameProPlayer;
        @BindView(R.id.win) TextView win;
        //@BindView(R.id.imageView) ImageView imageView;


        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }


    public MatchPlayerAdapter(List<Match> proPlayerList, Context context) {
        //this.proPlayers = new ArrayList<>(proPlayerList);
        this.proPlayersCopy = new ArrayList<>(proPlayerList);
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
        Match proPlayer = proPlayersCopy.get(position);

        holder.nameProPlayer.setText(proPlayer.getMatchId()+"");
        holder.personalNameProPlayer.setText(proPlayer.getStartTime()+"");
        holder.teamNameProPlayer.setText(proPlayer.getHeroId()+"");
        if(proPlayer.getPlayerSlot() >= 0 && proPlayer.getPlayerSlot() < 5 && proPlayer.isRadiantWin()){
            holder.win.setText("Win");
        } else if(proPlayer.getPlayerSlot() >= 128 && proPlayer.getPlayerSlot() < 133 && !proPlayer.isRadiantWin()){
            holder.win.setText("Win");
        } else {
            holder.win.setText("Lose");
        }


        //Picasso.with(context).load(proPlayer.getAvatarmedium()).fit().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return proPlayersCopy.size();
    }
}
