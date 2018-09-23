package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.Player;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MatchDetailAdapter extends RecyclerView.Adapter<MatchDetailAdapter.MyViewHolder> {

    public static final String TAG = "MatchDetailAdapter";
    public static final int LAYOUT = R.layout.match_detail_list_row;

    private List<Player> players;
    public List<Item> items;
    public Context context;
    private Map<Long, Item> itemsMap;
    private RequestManager glide;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.player_name)
        TextView playerName;
        @BindView(R.id.player_kda)
        TextView playerKda;
        @BindView(R.id.player_rank)
        TextView playerRank;
        @BindView(R.id.item_0)
        ImageView imageViewItem0;
        @BindView(R.id.item_1)
        ImageView imageViewItem1;
        @BindView(R.id.item_2)
        ImageView imageViewItem2;
        @BindView(R.id.item_3)
        ImageView imageViewItem3;
        @BindView(R.id.item_4)
        ImageView imageViewItem4;
        @BindView(R.id.item_5)
        ImageView imageViewItem5;
        @BindView(R.id.player_lvl)
        TextView playerLvl;
        @BindView(R.id.imageView)
        ImageView imageView;

        @BindView(R.id.cardView)
        CardView cardView;

        @Nullable
        @BindView(R.id.player_gold_per_minute)
        TextView goldPerMinutes;
        @Nullable
        @BindView(R.id.player_experience_per_minute)
        TextView xpPerMinuter;
        @Nullable
        @BindView(R.id.player_last_hit)
        TextView lastHits;
        @Nullable
        @BindView(R.id.player_denies)
        TextView denies;
        @Nullable
        @BindView(R.id.player_heal)
        TextView heal;
        @Nullable
        @BindView(R.id.player_hero_damage)
        TextView heroDamage;
        @Nullable
        @BindView(R.id.player_total_gold)
        TextView totalGold;


        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public MatchDetailAdapter(Context context, RequestManager glide) {
        this.context = context;
        this.glide = glide;
    }

    @NonNull
    @Override
    public MatchDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new MatchDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchDetailAdapter.MyViewHolder holder, int position) {

        Player currentPlayer = players.get(position);

        if (Configuration.ORIENTATION_LANDSCAPE == context.getResources().getConfiguration().orientation) {
            if (holder.goldPerMinutes != null) {
                holder.goldPerMinutes.setText(context.getResources().getString(R.string.gold_per_min_value, currentPlayer.getGoldPerMin()));
            }
            if (holder.xpPerMinuter != null) {
                holder.xpPerMinuter.setText(context.getResources().getString(R.string.xp_per_min_value, currentPlayer.getXpPerMin()));
            }
            if (holder.lastHits != null) {
                holder.lastHits.setText(context.getResources().getString(R.string.last_hits_value, currentPlayer.getLastHits()));
            }
            if (holder.denies != null) {
                holder.denies.setText(context.getResources().getString(R.string.denies_value, currentPlayer.getDenies()));
            }
            if (holder.heal != null) {
                holder.heal.setText(context.getResources().getString(R.string.heal_value, currentPlayer.getHeroHealing()));
            }
            if (holder.heroDamage != null) {
                holder.heroDamage.setText(context.getResources().getString(R.string.hero_damage_value, currentPlayer.getHeroDamage()));
            }
            if (holder.totalGold != null) {
                holder.totalGold.setText(context.getResources().getString(R.string.total_gold_value, currentPlayer.getTotalGold()));
            }
        }

        List<String> urlList = new ArrayList<>();
        List<ImageView> imageViewList = new ArrayList<>();

        long idItem0 = currentPlayer.getItem0();
        long idItem1 = currentPlayer.getItem1();
        long idItem2 = currentPlayer.getItem2();
        long idItem3 = currentPlayer.getItem3();
        long idItem4 = currentPlayer.getItem4();
        long idItem5 = currentPlayer.getItem5();
        long countKill = players.get(position).getKills();
        long countDeath = players.get(position).getDeaths();
        long countAssists = players.get(position).getAssists();

        Log.d(TAG, items.toString());
        Log.d(TAG, itemsMap.get(idItem0).getItemUrl() + "");
        Log.e(TAG, currentPlayer.getItem0() + " id" + " position: " + position);
        Log.e(TAG, currentPlayer.toString());

        urlList.add(itemsMap.get(idItem0).getItemUrl());
        urlList.add(itemsMap.get(idItem1).getItemUrl());
        urlList.add(itemsMap.get(idItem2).getItemUrl());
        urlList.add(itemsMap.get(idItem3).getItemUrl());
        urlList.add(itemsMap.get(idItem4).getItemUrl());
        urlList.add(itemsMap.get(idItem5).getItemUrl());

        imageViewList.add(holder.imageViewItem0);
        imageViewList.add(holder.imageViewItem1);
        imageViewList.add(holder.imageViewItem2);
        imageViewList.add(holder.imageViewItem3);
        imageViewList.add(holder.imageViewItem4);
        imageViewList.add(holder.imageViewItem5);

        for (int i = 0; i < imageViewList.size(); i++) {
            if (!urlList.get(i).contains("empty_lg")) {
                RequestOptions placeholder = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.item_background);

                Glide.with(context)
                        .load(urlList.get(i))
                        .apply(placeholder)
                        .into(imageViewList.get(i));
            }
        }

        if (currentPlayer.getRankTier() != 0) {
            holder.playerRank.setText(UtilDota.getRankTier(currentPlayer.getRankTier()));
        }

        if (currentPlayer.getPersonaname() != null) {
            holder.playerName.setText(currentPlayer.getPersonaname());
        }

        holder.playerLvl.setText(context.getResources().getString(R.string.level, players.get(position).getLevel()));

        holder.playerKda.setText(context.getResources().getString(R.string.kda, countKill, countDeath, countAssists));

        Disposable dis = App.get().getDB().heroDao().getHeroByIdRx(players.get(position).getHeroId())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hero -> UtilDota.setImageView(hero.getImg(), R.drawable.avatar_unknown_medium, holder.imageView, glide),
                        error -> Log.e(TAG, error.getLocalizedMessage()),
                        () -> Log.d(TAG, "onComplete")
                );

        compositeDisposable.add(dis);
    }

    @Override
    public int getItemCount() {
        if (players != null) {
            return players.size();
        } else {
            return 0;
        }
    }

    public void setData(List<Player> players, List<Item> items) {
        this.players = players;
        this.items = items;
        itemsMap = new HashMap<>();

        for (int i = 0; i < items.size(); i++) {
            itemsMap.put(items.get(i).getId(), items.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        compositeDisposable.dispose();
    }
}

