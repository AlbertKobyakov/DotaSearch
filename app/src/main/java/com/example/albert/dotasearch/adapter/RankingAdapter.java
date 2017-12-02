package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.activity.RankingActivity;
import com.example.albert.dotasearch.model.Leaderboard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {
    //private List<ProPlayer> proPlayers;
    private List<Leaderboard> leaderboardsCopy;
    public Context context;


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.position_text) TextView position;
        @BindView(R.id.name_player) TextView name;
        @BindView(R.id.team_player) TextView team;


        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }


    public RankingAdapter(List<Leaderboard> leaderboards, Context context) {
        //this.proPlayers = new ArrayList<>(proPlayerList);
        this.leaderboardsCopy = new ArrayList<>(leaderboards);
        this.context = context;
    }

    @Override
    public RankingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leader_board_list_row, parent, false);

        return new RankingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RankingAdapter.MyViewHolder holder, int position) {
        Leaderboard leaderboard = RankingActivity.leaderboards.get(position);
        long teamId = leaderboard.getTeamId();

        holder.position.setText(leaderboard.getPosition()+"");
        holder.name.setText(leaderboard.getName());

        if(teamId == 0){
            holder.team.setText("Нет команды");
        } else {
            holder.team.setText(leaderboard.getTeamId() + "");
        }
    }

    @Override
    public int getItemCount() {
        return RankingActivity.leaderboards.size();
    }

    public void filter(String text) {
        RankingActivity.leaderboards.clear();
        if(text.isEmpty()){
            RankingActivity.leaderboards = new ArrayList<>(leaderboardsCopy);
        } else{
            text = text.toLowerCase();
            for(Leaderboard item: leaderboardsCopy){
                if(item.getName() != null){
                    if(item.getName().toLowerCase().contains(text)){
                        RankingActivity.leaderboards.add(item);
                    }
                }

            }
        }
        notifyDataSetChanged();
    }

}
