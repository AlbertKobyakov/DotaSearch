package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.PlayerHero;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerInfoHeroesAdapter extends RecyclerView.Adapter<PlayerInfoHeroesAdapter.MyViewHolder> {

    private static final String TAG = "PlayerInfoHeroesAdapter";
    private static final int LAYOUT = R.layout.heroes_player_list_row;

    private List<PlayerHero> playerHeroesForAdapter;
    private SparseArray<Hero> heroes;
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
        TextView winRateHeroPercent;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public PlayerInfoHeroesAdapter(Context context) {
        this.context = context;
    }

    public PlayerHero getPlayerHeroByPosition(int position){
        return playerHeroesForAdapter.get(position);
    }

    @NonNull
    @Override
    public PlayerInfoHeroesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new PlayerInfoHeroesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerInfoHeroesAdapter.MyViewHolder holder, int position) {
        if (playerHeroesForAdapter != null) {
            PlayerHero playerHero = playerHeroesForAdapter.get(position);

            int heroId = Integer.parseInt(playerHero.getHeroId());

            if (heroId != 0) {  // 0 hero id not exist
                String heroImage = heroes.get(heroId).getImg();

                holder.gamesHero.setText(context.getResources().getString(R.string.games_hero, playerHero.getGames()));

                holder.winRateHero.setText(context.getResources().getString(R.string.win_rate_hero, playerHero.getWin()));

                RequestOptions fitCenter = new RequestOptions()
                        .fitCenter();

                Glide.with(context)
                        .load(heroImage)
                        .error(Glide.with(context).load(R.drawable.avatar_unknown_medium))
                        .apply(fitCenter)
                        .into(holder.heroImage);

                long lastPlayed = playerHero.getLastPlayed();

                if (lastPlayed != 0) {
                    CharSequence updateTimeString = DateUtils.getRelativeTimeSpanString(
                            playerHero.getLastPlayed() * 1000, System.currentTimeMillis(), 0);

                    holder.lastPlayedHero.setText(updateTimeString);
                } else {
                    holder.lastPlayedHero.setText(context.getString(R.string.unknown));
                }

                double winRate = ((double) playerHero.getWin() / (double) playerHero.getGames()) * 100;

                holder.winRateHeroPercent.setText(twoNumberAfterPoint(winRate));
            }
        }
    }

    private String twoNumberAfterPoint(double number) {
        Log.e(TAG, number + "");
        if (String.valueOf(number).equals("NaN")) {
            return "0%";
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(number) + "%";
        }
    }

    public void setData(List<PlayerHero> playerHeroes, SparseArray<Hero> heroes) {
        this.playerHeroesForAdapter = playerHeroes;
        this.heroes = heroes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (playerHeroesForAdapter != null) {
            return playerHeroesForAdapter.size();
        } else {
            return 0;
        }
    }
}
