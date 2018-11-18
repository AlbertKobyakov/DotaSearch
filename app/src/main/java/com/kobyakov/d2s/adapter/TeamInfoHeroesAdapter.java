package com.kobyakov.d2s.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.model.TeamHero;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamInfoHeroesAdapter extends RecyclerView.Adapter<TeamInfoHeroesAdapter.MyViewHolder> {

    private static final String TAG = "PlayerInfoHeroesAdapter";
    private static final int LAYOUT = R.layout.heroes_team_list_row;

    private List<TeamHero> teamHeroesForAdapter;
    public Context context;
    private RequestManager glide;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.games_hero)
        TextView gamesHero;
        @BindView(R.id.hero_image)
        ImageView heroImage;
        @BindView(R.id.winrate_hero_percent)
        TextView winRateHeroPercent;
        @BindView(R.id.hero_name)
        TextView heroName;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public TeamInfoHeroesAdapter(Context context, RequestManager glide) {
        this.context = context;
        this.glide = glide;
    }

    public TeamHero getPlayerHeroByPosition(int position) {
        return teamHeroesForAdapter.get(position);
    }

    @NonNull
    @Override
    public TeamInfoHeroesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new TeamInfoHeroesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamInfoHeroesAdapter.MyViewHolder holder, int position) {
        if (teamHeroesForAdapter != null) {
            TeamHero teamHero = teamHeroesForAdapter.get(position);

            Log.d(TAG, teamHero.getHeroName());

            int heroId = teamHero.getHeroId();

            if (heroId != 0) {  // 0 hero id not exist
                String heroImage = teamHero.getHeroImg();

                holder.gamesHero.setText(context.getResources().getString(R.string.games_hero, teamHero.getGamesPlayed()));

                holder.heroName.setText(teamHero.getHeroName());

                RequestOptions fitCenter = new RequestOptions()
                        .fitCenter();

                glide.load(heroImage)
                        .error(glide.load(R.drawable.avatar_unknown_medium))
                        .apply(fitCenter)
                        .into(holder.heroImage);

                double winRate = ((double) teamHero.getWins() / (double) teamHero.getGamesPlayed()) * 100;

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

    public void setData(List<TeamHero> playerHeroes) {
        this.teamHeroesForAdapter = playerHeroes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (teamHeroesForAdapter != null) {
            return teamHeroesForAdapter.size();
        } else {
            return 0;
        }
    }
}