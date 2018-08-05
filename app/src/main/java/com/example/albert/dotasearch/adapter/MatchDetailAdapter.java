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

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.Player;
import com.example.albert.dotasearch.util.UtilDota;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.albert.dotasearch.activity.MatchDetailActivity.itemsMap;

public class MatchDetailAdapter extends RecyclerView.Adapter<MatchDetailAdapter.MyViewHolder>{

    public static final String TAG = "MatchDetailAdapter";
    public static final int LAYOUT = R.layout.match_detail_list_row;

    public List<Player> players;
    public List<Item> items;
    public Context context;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.player_name) TextView playerName;
        @BindView(R.id.player_kda) TextView playerKda;
        @BindView(R.id.player_rank) TextView playerRank;
        @BindView(R.id.item_0) ImageView imageViewItem0;
        @BindView(R.id.item_1) ImageView imageViewItem1;
        @BindView(R.id.item_2) ImageView imageViewItem2;
        @BindView(R.id.item_3) ImageView imageViewItem3;
        @BindView(R.id.item_4) ImageView imageViewItem4;
        @BindView(R.id.item_5) ImageView imageViewItem5;
        @BindView(R.id.player_lvl) TextView playerLvl;
        @BindView(R.id.imageView) ImageView imageView;


        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public MatchDetailAdapter(List<Player> players, List<Item> items, Context context) {
        this.players = players;
        this.items = items;
        this.context = context;
    }

    @Override
    public MatchDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new MatchDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MatchDetailAdapter.MyViewHolder holder, int position) {
        Player currentPlayer = players.get(position);

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

        /*String urlItem0 = itemsMap.get(idItem0).getItemUrl();
        String urlItem1 = itemsMap.get(idItem1).getItemUrl();
        String urlItem2 = itemsMap.get(idItem2).getItemUrl();
        String urlItem3 = itemsMap.get(idItem3).getItemUrl();
        String urlItem4 = itemsMap.get(idItem4).getItemUrl();
        String urlItem5 = itemsMap.get(idItem5).getItemUrl();*/

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

        /*Picasso.with(context).load(urlItem0).error(R.drawable.item_background).fit().into(holder.imageViewItem0);
        Picasso.with(context).load(urlItem1).error(R.drawable.item_background).fit().into(holder.imageViewItem1);
        Picasso.with(context).load(urlItem2).error(R.drawable.item_background).fit().into(holder.imageViewItem2);
        Picasso.with(context).load(urlItem3).error(R.drawable.item_background).fit().into(holder.imageViewItem3);
        Picasso.with(context).load(urlItem4).error(R.drawable.item_background).fit().into(holder.imageViewItem4);
        Picasso.with(context).load(urlItem5).error(R.drawable.item_background).fit().into(holder.imageViewItem5);*/

        for (int i = 0; i < imageViewList.size(); i++) {
            Glide.with(context)
                    .load(urlList.get(i))
                    .placeholder(R.drawable.item_background)
                    .fitCenter()
                    .into(imageViewList.get(i));
            //Picasso.with(context).load(urlList.get(i)).error(R.drawable.item_background).fit().into(imageViewList.get(i));
        }

        if(currentPlayer.getRankTier() != 0){
            holder.playerRank.setText(UtilDota.getRankTier(currentPlayer.getRankTier()));
        }

        if(currentPlayer.getPersonaname() != null){
            holder.playerName.setText(currentPlayer.getPersonaname());
        }

        holder.playerLvl.setText(context.getResources().getString(R.string.level, players.get(position).getLevel()));

        holder.playerKda.setText(context.getResources().getString(R.string.kda, countKill, countDeath, countAssists));

        Disposable dis = App.get().getDB().heroDao().getHeroByIdRx(players.get(position).getHeroId())
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hero -> UtilDota.setImageView(hero.getImg(), R.drawable.avatar_unknown_medium, holder.imageView, context),
                        error -> Log.e(TAG, error.getLocalizedMessage()),
                        () -> Log.d(TAG, "onComplete")
                );

        compositeDisposable.add(dis);
    }

    /*public void setImageView(Hero hero, MatchDetailAdapter.MyViewHolder holder){
        Picasso.with(context).load(hero.getImg()).fit().into(holder.imageView);
    }*/

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void setHeroImageView(String url, int defaultImage, ImageView imageView){
        Glide.with(context)
                .load(url)
                .error(defaultImage)
                .fitCenter()
                .into(imageView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        compositeDisposable.dispose();
    }
}

