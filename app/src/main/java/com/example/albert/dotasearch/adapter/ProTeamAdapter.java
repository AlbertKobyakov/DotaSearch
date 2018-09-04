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
import com.example.albert.dotasearch.model.Team;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProTeamAdapter extends RecyclerView.Adapter<ProTeamAdapter.MyViewHolder>{
    private static final String TAG = "ProTeamAdapter";
    private static final int LAYOUT = R.layout.pro_team_list_row;

    private List<Team> teams;
    public Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.team_logo)
        ImageView teamLogo;
        @BindView(R.id.team_win)
        TextView teamWin;
        @BindView(R.id.team_lose)
        TextView teamLose;
        @BindView(R.id.team_name)
        TextView teamName;
        @BindView(R.id.team_tag)
        TextView teamTag;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

    }

    public ProTeamAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ProTeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new ProTeamAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProTeamAdapter.MyViewHolder holder, int position) {
        if (teams != null) {
            Team team = teams.get(position);

            Log.d(TAG, team.getName() + " " + team.getLogoUrl());

            Glide.with(context)
                    .load(team.getLogoUrl())
                    .error(R.drawable.no_logo)
                    .fitCenter()
                    .into(holder.teamLogo);

            holder.teamLose.setText(team.getLosses() + "");
            holder.teamWin.setText(team.getWins() + "");
            holder.teamName.setText(team.getName() + "");
            holder.teamTag.setText(team.getTag() + "");
        }
    }

    public void setData(List<Team> teams) {
        this.teams = teams;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (teams != null) {
            return teams.size();
        } else {
            return 0;
        }
    }
}
