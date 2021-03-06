package com.kobyakov.d2s.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.kobyakov.d2s.AnimationForRecyclerViewItems;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.model.Team;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProTeamAdapter extends RecyclerView.Adapter<ProTeamAdapter.MyViewHolder> implements AnimationForRecyclerViewItems {
    private static final String TAG = "ProTeamAdapter";
    private static final int LAYOUT = R.layout.pro_team_list_row;

    private List<Team> teamsForAdapter;
    private List<Team> teamsAll;
    public Context context;
    private RequestManager glide;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.team_logo)
        ImageView teamLogo;
        @BindView(R.id.team_name)
        TextView teamName;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public ProTeamAdapter(Context context, RequestManager glide) {
        this.context = context;
        this.glide = glide;
    }


    @NonNull
    @Override
    public ProTeamAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new ProTeamAdapter.MyViewHolder(itemView);
    }

    public List<Team> getTeamsForAdapter() {
        return teamsForAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull ProTeamAdapter.MyViewHolder holder, int position) {
        if (teamsForAdapter != null) {
            Team team = teamsForAdapter.get(position);

            Log.d(TAG, team.getName() + " " + team.getLogoUrl());

            RequestOptions fitCenter = new RequestOptions()
                    .fitCenter()
                    .override(100, 100);

            glide.load(team.getLogoUrl())
                    .error(glide.load(R.drawable.help))
                    .apply(fitCenter)
                    .into(holder.teamLogo);

            if (team.getName() == null || team.getName().trim().length() == 0) {
                holder.teamName.setText(context.getResources().getString(R.string.unknown));
            } else {
                holder.teamName.setText(context.getResources().getString(R.string.team_mame, team.getName()));
            }

            setFadeAnimation(holder.itemView);
        }
    }

    public void setData(List<Team> teams) {
        this.teamsForAdapter = teams;
        this.teamsAll = teams;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (teamsForAdapter != null) {
            return teamsForAdapter.size();
        } else {
            return 0;
        }
    }

    public void filter(String text) {
        List<Team> teamsFiltered = new ArrayList<>();

        if (!text.isEmpty()) {
            text = text.toLowerCase();
            for (Team team : teamsAll) {
                if (team.getName() != null && team.getTag() != null) {
                    if (team.getName().toLowerCase().contains(text)
                            || team.getTag().toLowerCase().contains(text)) {
                        teamsFiltered.add(team);
                    }
                }
            }
        } else {
            teamsFiltered = teamsAll;
        }
        teamsForAdapter = teamsFiltered;
        notifyDataSetChanged();
    }
}