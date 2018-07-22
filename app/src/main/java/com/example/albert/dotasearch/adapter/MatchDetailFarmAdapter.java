package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.MatchFullInfo;
import com.example.albert.dotasearch.model.Player;
import com.example.albert.dotasearch.util.UtilDota;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.albert.dotasearch.activity.StartActivity.db;

public class MatchDetailFarmAdapter extends RecyclerView.Adapter<MatchDetailFarmAdapter.MyViewHolder>{

    public static final String TAG = "MatchDetailFarmAdapter";
    public static final int LAYOUT = R.layout.match_detail_farm_list_row;

    public List<Player> players;
    public List<Item> items;
    public Context context;


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.player_name) TextView playerName;
        @BindView(R.id.player_rank) TextView playerRank;
        @BindView(R.id.hero_img) ImageView heroImage;
        @BindView(R.id.player_last_hit) TextView playerLastHit;
        @BindView(R.id.player_denies) TextView playerDenies;
        @BindView(R.id.player_gold_per_minute) TextView playerGoldPerMinute;
        @BindView(R.id.player_experience_per_minute) TextView playerExperiencePerMinute;


        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public MatchDetailFarmAdapter(List<Player> players, List<Item> items, Context context) {
        this.players = players;
        this.items = items;
        this.context = context;
    }

    @Override
    public MatchDetailFarmAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new MatchDetailFarmAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MatchDetailFarmAdapter.MyViewHolder holder, int position) {
        Player currentPlayer = players.get(position);

        String lastHits = String.valueOf(currentPlayer.getLastHits());
        String denies = String.valueOf(currentPlayer.getDenies());
        String goldPerMinute = String.valueOf(currentPlayer.getGoldPerMin());
        String experiencePerMinute = String.valueOf(currentPlayer.getXpPerMin());

        if(currentPlayer.getRankTier() != 0){
            holder.playerRank.setText(UtilDota.getRankTier(currentPlayer.getRankTier()));
        }

        if(currentPlayer.getPersonaname() != null){
            holder.playerName.setText(currentPlayer.getPersonaname());
        }

        holder.playerLastHit.setText(lastHits);
        holder.playerDenies.setText(denies);
        holder.playerGoldPerMinute.setText(goldPerMinute);
        holder.playerExperiencePerMinute.setText(experiencePerMinute);

        db.heroDao().getHeroByIdRx(players.get(position).getHeroId())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hero -> UtilDota.setImageView(hero.getImg(), R.drawable.avatar_unknown_medium, holder.heroImage,context),
                        error -> Log.e(TAG, error.getLocalizedMessage()),
                        () -> Log.d(TAG, "onComplete")
                );
    }

    /*public void setImageView(Hero hero, MatchDetailFarmAdapter.MyViewHolder holder){
        Picasso.with(context).load(hero.getImg()).fit().into(holder.heroImage);
    }*/

    @Override
    public int getItemCount() {
        return players.size();
    }
}

