package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.PlayerHero;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerInfoHeroesAdapter extends RecyclerView.Adapter<PlayerInfoHeroesAdapter.MyViewHolder> {

    private static final String TAG = "PlayerInfoHeroesAdapter";
    private static final int LAYOUT = R.layout.heroes_player_list_row;


    private List<PlayerHero> playerHeroes;
    private Map<Integer, Hero> heroes;
    public Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.last_played_hero)
        TextView lastPlayedHero;
        @BindView(R.id.games_hero)
        TextView gamesHero;
        @BindView(R.id.winrate_hero)
        TextView winRateHero;
        @BindView(R.id.hero_image)
        ImageView heroImage;
        @BindView(R.id.winrate_hero_percent)
        TextView winrateHeroPercent;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

    }

    public PlayerInfoHeroesAdapter(Context context) {
        this.context = context;
    }


    @Override
    public PlayerInfoHeroesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new PlayerInfoHeroesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerInfoHeroesAdapter.MyViewHolder holder, int position) {
        if (playerHeroes != null) {
            PlayerHero playerHero = playerHeroes.get(position);

            int heroId = Integer.parseInt(playerHero.getHeroId());

            if (heroId != 0) {  // 0 hero id not exist
                String heroImage = heroes.get(heroId).getImg();

                holder.gamesHero.setText(playerHero.getGames() + "");

                holder.winRateHero.setText(playerHero.getWin() + "");

                Glide.with(context)
                        .load(heroImage)
                        .fitCenter()
                        .into(holder.heroImage);

                long lastPlayed = playerHero.getLastPlayed();

                if (lastPlayed != 0) {
                    CharSequence updateTimeString = DateUtils.getRelativeTimeSpanString(
                            playerHero.getLastPlayed() * 1000, System.currentTimeMillis(), 0);

                    holder.lastPlayedHero.setText(updateTimeString);
                } else {
                    holder.lastPlayedHero.setText(context.getString(R.string.unknown));
                }

                double winRate = ((double)playerHero.getWin() / (double)playerHero.getGames()) * 100;

                holder.winrateHeroPercent.setText(twoNumberAfterPoint(winRate));
            }
        }
    }

    public String twoNumberAfterPoint(double number) {
        Log.e(TAG, number + "");
        if(String.valueOf(number).equals("NaN")){
            return "0%";
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(number) + "%";
        }

    }

    public void setData(List<PlayerHero> playerHeroes, Map<Integer, Hero> heroes) {
        this.playerHeroes = playerHeroes;
        this.heroes = heroes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (playerHeroes != null) {
            return playerHeroes.size();
        } else {
            return 0;
        }
    }
}
