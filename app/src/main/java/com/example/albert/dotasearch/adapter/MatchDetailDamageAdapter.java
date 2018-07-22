package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.Player;
import com.example.albert.dotasearch.util.UtilDota;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.albert.dotasearch.activity.StartActivity.db;

public class MatchDetailDamageAdapter extends RecyclerView.Adapter<MatchDetailDamageAdapter.MyViewHolder>{

    public static final String TAG = "MatchDetailFarmAdapter";
    public static final int LAYOUT = R.layout.match_detail_damage_list_row;

    public List<Player> players;
    public List<Item> items;
    public Context context;


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.player_name) TextView playerName;
        @BindView(R.id.player_rank) TextView playerRank;
        @BindView(R.id.hero_img) ImageView heroImage;
        @BindView(R.id.player_damage) TextView playerDamage;
        @BindView(R.id.player_heal) TextView playerHeal;
        @BindView(R.id.player_damage_building) TextView playerDamageBuilding;


        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public MatchDetailDamageAdapter(List<Player> players, List<Item> items, Context context) {
        this.players = players;
        this.items = items;
        this.context = context;
    }

    @Override
    public MatchDetailDamageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new MatchDetailDamageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MatchDetailDamageAdapter.MyViewHolder holder, int position) {
        Player currentPlayer = players.get(position);

        if(currentPlayer.getRankTier() != 0){
            holder.playerRank.setText(UtilDota.getRankTier(currentPlayer.getRankTier()));
        }

        if(currentPlayer.getPersonaname() != null){
            holder.playerName.setText(currentPlayer.getPersonaname());
        }

        String heroDamage = UtilDota.formatLongNumberToFloatWitK(currentPlayer.getHeroDamage());
        String heroHeal = UtilDota.formatLongNumberToFloatWitK(currentPlayer.getHeroHealing());
        String towerDamage = UtilDota.formatLongNumberToFloatWitK(currentPlayer.getTowerDamage());

        holder.playerDamage.setText(heroDamage);
        holder.playerHeal.setText(heroHeal);
        holder.playerDamageBuilding.setText(towerDamage);

        db.heroDao().getHeroByIdRx(players.get(position).getHeroId())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hero -> UtilDota.setImageView(hero.getImg(), R.drawable.avatar_unknown_medium, holder.heroImage, context),
                        error -> Log.e(TAG, error.getLocalizedMessage()),
                        () -> Log.d(TAG, "onComplete")
                );
    }

    /*public void setImageView(Hero hero, MatchDetailDamageAdapter.MyViewHolder holder){
        Picasso.with(context).load(hero.getImg()).fit().into(holder.heroImage);
    }*/

    @Override
    public int getItemCount() {
        return players.size();
    }


}


