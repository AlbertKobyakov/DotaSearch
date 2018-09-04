package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.ProMatch;
import com.example.albert.dotasearch.model.Team;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProMatchAdapter extends RecyclerView.Adapter<ProMatchAdapter.MyViewHolder>{
    private static final String TAG = "ProMatchAdapter";
    private static final int LAYOUT = R.layout.pro_match_list_row;

    private List<ProMatch> proMatches;
    public Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.team_first_logo) ImageView teamFirstLogo;
        @BindView(R.id.team_second_logo) ImageView teamSecondLogo;
        @BindView(R.id.team_first_name) TextView teamFirstName;
        @BindView(R.id.team_second_name) TextView teamSecondName;
        @BindView(R.id.teams_score) TextView teamsScore;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

    }

    public ProMatchAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ProMatchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new ProMatchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProMatchAdapter.MyViewHolder holder, int position) {
        if (proMatches != null) {
            ProMatch proMatch = proMatches.get(position);

            /*Glide.with(context)
                    .load(team.getLogoUrl())
                    .fitCenter()
                    .into(holder.teamLogo);*/

            holder.teamFirstName.setText(proMatch.getDireName() + "");
            holder.teamSecondName.setText(proMatch.getRadiantName() + "");
            holder.teamsScore.setText(proMatch.getDireScore() + ":" + proMatch.getRadiantScore());
            //holder.teamTag.setText(team.getTag() + "");
        }
    }

    public void setData(List<ProMatch> proMatches) {
        this.proMatches = proMatches;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (proMatches != null) {
            return proMatches.size();
        } else {
            return 0;
        }
    }
}
