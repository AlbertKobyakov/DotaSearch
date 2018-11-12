package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.albert.dotasearch.AnimationForRecyclerViewItems;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.ProPlayer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProPlayerAdapter extends RecyclerView.Adapter<ProPlayerAdapter.MyViewHolder> implements AnimationForRecyclerViewItems {

    public static final String TAG = "ProPlayerAdapter";
    public static final int LAYOUT = R.layout.pro_player_list_row;

    private List<ProPlayer> proPlayers;
    private List<ProPlayer> proPlayersTempAll;
    public Context context;
    private RequestManager glide;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_pro_player)
        TextView nameProPlayer;
        @BindView(R.id.team_name_pro_player)
        TextView teamNameProPlayer;
        @BindView(R.id.imageView)
        ImageView imageView;
        @Nullable
        @BindView(R.id.country_pro_player)
        TextView countryProPlayer;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public ProPlayerAdapter(Context context, RequestManager glide) {
        this.context = context;
        this.glide = glide;
    }

    public void setData(List<ProPlayer> proPlayersList) {
        proPlayers = new ArrayList<>(proPlayersList);
        proPlayersTempAll = new ArrayList<>(proPlayersList);
        notifyDataSetChanged();
    }

    public List<ProPlayer> getProPlayers() {
        return proPlayers;
    }

    @NonNull
    @Override
    public ProPlayerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new ProPlayerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProPlayerAdapter.MyViewHolder holder, int position) {
        Log.e(TAG, proPlayers.size() + " lll");
        if (proPlayers != null) {
            ProPlayer proPlayer = proPlayers.get(position);
            String avatarMedium = "";

            if (proPlayer.getName() != null && proPlayer.getName().trim().length() > 0) {
                holder.nameProPlayer.setText(proPlayer.getName());
            } else {
                holder.nameProPlayer.setText(context.getResources().getString(R.string.unknown));
            }

            if (proPlayer.getTeamName() != null && proPlayer.getTeamName().trim().length() > 0) {
                holder.teamNameProPlayer.setText(proPlayer.getTeamName());
            } else {
                holder.teamNameProPlayer.setText(context.getResources().getString(R.string.unknown));
            }

            if (proPlayer.getAvatarmedium() != null) {
                avatarMedium = proPlayer.getAvatarmedium();
            }

            glide.load(avatarMedium)
                    .error(glide.load(R.drawable.avatar_unknown_medium).apply(RequestOptions.circleCropTransform()))
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imageView);

            if (Configuration.ORIENTATION_LANDSCAPE == context.getResources().getConfiguration().orientation) {
                if (holder.countryProPlayer != null) {
                    if (proPlayer.getCountryCode().trim().length() > 0) {
                        holder.countryProPlayer.setText(proPlayer.getCountryCode());
                    } else {
                        holder.countryProPlayer.setText(context.getResources().getString(R.string.unknown));
                    }
                }
            }

            setFadeAnimation(holder.itemView);
        }
    }

    @Override
    public int getItemCount() {

        Log.e(TAG, (proPlayers != null) + "kkkk");
        if (proPlayers != null) {
            return proPlayers.size();
        } else {
            return 0;
        }
    }

    public void filter(String text) {
        List<ProPlayer> proPlayersFiltered = new ArrayList<>();

        if (!text.isEmpty() && proPlayersTempAll != null && proPlayersTempAll.size() > 0) {
            text = text.toLowerCase();
            for (ProPlayer proPlayer : proPlayersTempAll) {
                if (proPlayer.getName() != null && proPlayer.getName().toLowerCase().trim().contains(text)) {
                    proPlayersFiltered.add(proPlayer);
                } else if (proPlayer.getTeamName() != null && proPlayer.getTeamName().toLowerCase().trim().contains(text)) {
                    proPlayersFiltered.add(proPlayer);
                }
            }
        } else {
            proPlayersFiltered = proPlayersTempAll;
        }
        proPlayers = proPlayersFiltered;
        notifyDataSetChanged();
    }
}