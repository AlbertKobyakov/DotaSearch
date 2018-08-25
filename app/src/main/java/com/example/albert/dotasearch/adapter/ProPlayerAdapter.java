package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.tabs.TabProPlayers;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProPlayerAdapter extends RecyclerView.Adapter<ProPlayerAdapter.MyViewHolder>{

    public static final String TAG = "ProPlayerAdapter";
    public static final int LAYOUT = R.layout.pro_player_list_row;


    private List<ProPlayer> proPlayersCopy;
    public Context context;


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_pro_player) TextView nameProPlayer;
        @BindView(R.id.personal_name_pro_playere) TextView personalNameProPlayer;
        @BindView(R.id.teame_name_pro_playere) TextView teamNameProPlayer;
        @BindView(R.id.imageView) ImageView imageView;


        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }


    public ProPlayerAdapter(List<ProPlayer> proPlayerList, Context context) {
        this.proPlayersCopy = new ArrayList<>(proPlayerList);
        this.context = context;
    }

    @Override
    public ProPlayerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new ProPlayerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProPlayerAdapter.MyViewHolder holder, int position) {
        ProPlayer proPlayer = TabProPlayers.proPlayers.get(position);

        if(proPlayer.getName() != null && proPlayer.getName().trim().length() > 0){
            holder.nameProPlayer.setText(proPlayer.getName());
        } else {
            holder.nameProPlayer.setText(context.getResources().getString(R.string.unknown));
        }

        if(proPlayer.getPersonaname() != null && proPlayer.getPersonaname().trim().length() > 0){
            holder.personalNameProPlayer.setText(proPlayer.getPersonaname());
        } else {
            holder.personalNameProPlayer.setText(context.getResources().getString(R.string.unknown));
        }

        if(proPlayer.getTeamName() != null && proPlayer.getTeamName().trim().length() > 0){
            holder.teamNameProPlayer.setText(proPlayer.getTeamName());
        } else {
            holder.teamNameProPlayer.setText(context.getResources().getString(R.string.unknown));
        }

        if(proPlayer.getAvatarmedium() != null){
            Glide.with(context)
                    .load(proPlayer.getAvatarmedium())
                    .fitCenter()
                    .into(holder.imageView);
        } else {
            Glide.with(context)
                    .load(R.drawable.avatar_unknown_medium)
                    .fitCenter()
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return TabProPlayers.proPlayers.size();
    }

    public void filter(String text) {
        TabProPlayers.proPlayers.clear();
        if(text.isEmpty()){
            TabProPlayers.proPlayers = new ArrayList<>(proPlayersCopy);
        } else{
            text = text.toLowerCase();
            for(ProPlayer item: proPlayersCopy){
                if(item.getPersonaname() != null && item.getName() != null && item.getTeamName() != null){
                    if(
                        item.getPersonaname().toLowerCase().contains(text)
                        || item.getName().toLowerCase().contains(text)
                        || item.getTeamName().toLowerCase().contains(text)
                    ){
                        TabProPlayers.proPlayers.add(item);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
