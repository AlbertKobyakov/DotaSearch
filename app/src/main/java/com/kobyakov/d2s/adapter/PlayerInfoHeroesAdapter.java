package com.kobyakov.d2s.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.model.PlayerHero;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerInfoHeroesAdapter extends RecyclerView.Adapter<PlayerInfoHeroesAdapter.MyViewHolder> {

    private static final String TAG = "PlayerInfoHeroesAdapter";
    private static final int LAYOUT = R.layout.heroes_player_list_row;

    private List<PlayerHero> playerHeroesForAdapter;
    public Context context;
    private RequestManager glide;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.last_played_hero)
        TextView lastPlayedHero;
        @BindView(R.id.games_hero)
        TextView gamesHero;
        @BindView(R.id.hero_image)
        ImageView heroImage;
        @BindView(R.id.winrate_hero_percent)
        TextView winRateHeroPercent;
        @BindView(R.id.hero_name)
        TextView heroName;

        @BindView(R.id.winrate_hero_percent_with)
        TextView with;
        @BindView(R.id.winrate_hero_percent_against)
        TextView against;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public PlayerInfoHeroesAdapter(Context context, RequestManager glide) {
        this.context = context;
        this.glide = glide;
    }

    public PlayerHero getPlayerHeroByPosition(int position) {
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
                String heroImage = playerHero.getHeroImg();

                holder.gamesHero.setText(context.getResources().getString(R.string.games_hero, playerHero.getGames()));

                holder.heroName.setText(playerHero.getHeroName());

                RequestOptions fitCenter = new RequestOptions()
                        .fitCenter();

                glide.load(heroImage)
                        .error(glide.load(R.drawable.avatar_unknown_medium))
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

                //if (Configuration.ORIENTATION_LANDSCAPE == context.getResources().getConfiguration().orientation) {
                double withWin = (double) playerHero.getWithWin() / (double) playerHero.getWithGames() * 100;
                double againstWin = (double) playerHero.getAgainstWin() / (double) playerHero.getAgainstGames() * 100;

                if (playerHero.getWithWin() != 0) {
                    holder.with.setText(context.getResources().getString(R.string.matches_with_win, twoNumberAfterPoint(withWin, playerHero.getWithGames())));
                } else {
                    holder.with.setText(context.getResources().getString(R.string.matches_with_win, "0"));
                }

                if (playerHero.getAgainstWin() != 0) {
                    holder.against.setText(context.getResources().getString(R.string.matches_with_win, twoNumberAfterPoint(againstWin, playerHero.getAgainstGames())));
                } else {
                    holder.against.setText(context.getResources().getString(R.string.matches_with_win, "0"));
                }
                //}
            }
        }
    }

    private String twoNumberAfterPoint(double number) {
        if (String.valueOf(number).equals("NaN")) {
            return "0%";
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(number) + "%";
        }
    }

    private String twoNumberAfterPoint(double number, long allGames) {
        DecimalFormat df = new DecimalFormat("#.##");
        return allGames + " (" + df.format(number) + "%)";
    }

    public void setData(List<PlayerHero> playerHeroes) {
        this.playerHeroesForAdapter = playerHeroes;
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