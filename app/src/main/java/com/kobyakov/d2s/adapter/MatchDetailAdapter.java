package com.kobyakov.d2s.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.kobyakov.d2s.ClickListener;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.model.Hero;
import com.kobyakov.d2s.model.Item;
import com.kobyakov.d2s.model.ItemsWithMatchDetail;
import com.kobyakov.d2s.model.MatchFullInfo;
import com.kobyakov.d2s.model.Player;
import com.kobyakov.d2s.util.UtilDota;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchDetailAdapter extends RecyclerView.Adapter<MatchDetailAdapter.MyViewHolder> {

    public static final String TAG = "MatchDetailAdapter";
    public static final int LAYOUT = R.layout.match_detail_list_row;

    private List<Player> players;
    private SparseArray<Item> items;
    private Context context;
    private RequestManager glide;
    private MatchFullInfo matchFullInfo;
    private SparseArray<Hero> heroSparseArray;
    private boolean isRadiantWin;

    private int mExpandedPosition = -1;
    private int previousExpandedPosition = -1;

    private ClickListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        @BindView(R.id.header)
        LinearLayout header;
        @BindView(R.id.team_type)
        TextView teamType;
        @BindView(R.id.content)
        LinearLayout content;
        @BindView(R.id.trophy)
        ImageView trophy;
        @BindView(R.id.items)
        LinearLayout blockItems;

        @BindView(R.id.block_details)
        LinearLayout blockDetail;
        @BindView(R.id.btn_profile)
        Button btnProfile;

        @BindView(R.id.player_gold_per_minute)
        TextView goldPerMinutes;
        @BindView(R.id.player_hero_damage)
        TextView heroDamage;
        @BindView(R.id.player_heal)
        TextView heal;
        @BindView(R.id.player_last_hit)
        TextView lastHits;
        @BindView(R.id.player_randomed)
        TextView random;
        @BindView(R.id.tower_damage)
        TextView towerDamage;
        @BindView(R.id.player_experience_per_minute)
        TextView xpPerMinutes;
        @BindView(R.id.player_total_gold)
        TextView totalGold;
        @BindView(R.id.player_denies)
        TextView denies;


        private WeakReference<ClickListener> listenerRef;

        MyViewHolder(View view, ClickListener listener) {
            super(view);

            ButterKnife.bind(this, view);

            listenerRef = new WeakReference<>(listener);
            btnProfile.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            boolean isGoToPlayerActivity = false;
            if (view.getId() == btnProfile.getId()) {
                isGoToPlayerActivity = true;
            }

            listenerRef.get().onPositionClicked(getAdapterPosition(), isGoToPlayerActivity);
        }
    }

    public MatchDetailAdapter(Context context, RequestManager glide, ClickListener listener) {
        this.context = context;
        this.glide = glide;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MatchDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new MatchDetailAdapter.MyViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchDetailAdapter.MyViewHolder holder, int position) {
        Player currentPlayer = players.get(position);
        String radiantTeam = matchFullInfo.getRadiantTeam() != null && matchFullInfo.getRadiantTeam().getName().trim().length() != 0 ? matchFullInfo.getRadiantTeam().getName() : context.getString(R.string.radiant);
        String direTeam = matchFullInfo.getDireTeam() != null && matchFullInfo.getDireTeam().getName().trim().length() != 0 ? matchFullInfo.getDireTeam().getName() : context.getString(R.string.dire);

        if (position == 0) {
            holder.teamType.setText(radiantTeam);
            if (isRadiantWin) {
                holder.trophy.setVisibility(View.VISIBLE);
            } else {
                holder.trophy.setVisibility(View.GONE);
            }
            holder.header.setVisibility(View.VISIBLE);
        } else if (position == 5) {
            holder.teamType.setText(direTeam);
            if (!isRadiantWin) {
                holder.trophy.setVisibility(View.VISIBLE);
            } else {
                holder.trophy.setVisibility(View.GONE);
            }
            holder.header.setVisibility(View.VISIBLE);
        } else {
            holder.header.setVisibility(View.GONE);
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

        urlList.add(items.get((int) idItem0).getItemUrl());
        urlList.add(items.get((int) idItem1).getItemUrl());
        urlList.add(items.get((int) idItem2).getItemUrl());
        urlList.add(items.get((int) idItem3).getItemUrl());
        urlList.add(items.get((int) idItem4).getItemUrl());
        urlList.add(items.get((int) idItem5).getItemUrl());

        imageViewList.add(holder.imageViewItem0);
        imageViewList.add(holder.imageViewItem1);
        imageViewList.add(holder.imageViewItem2);
        imageViewList.add(holder.imageViewItem3);
        imageViewList.add(holder.imageViewItem4);
        imageViewList.add(holder.imageViewItem5);

        for (int i = 0; i < imageViewList.size(); i++) {
            RequestOptions placeholder = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.item_background);
            RequestManager manager = Glide.with(context);
            RequestBuilder<Drawable> load;
            if (!urlList.get(i).contains("empty_lg")) {
                load = manager.load(urlList.get(i));
            } else {
                load = manager.load(R.drawable.item_background);
            }
            load.apply(placeholder).into(imageViewList.get(i));
        }

        if (currentPlayer.getRankTier() != 0) {
            holder.playerRank.setText(context.getString(R.string.only_string_value, UtilDota.getRankTier(currentPlayer.getRankTier())));
        } else {
            holder.playerRank.setText(context.getString(R.string.only_string_value, context.getString(R.string.unknown)));
        }

        holder.playerName.setText(getRealPlayerName(currentPlayer));

        holder.playerKda.setText(context.getString(R.string.kda, countKill, countDeath, countAssists));

        RequestOptions centerCrop = new RequestOptions()
                .centerCrop();

        Log.d(TAG, currentPlayer.getHeroId() + " glide");
        if (heroSparseArray.get(currentPlayer.getHeroId()) != null) {
            glide.load(heroSparseArray.get(currentPlayer.getHeroId()).getImg())
                    .error(glide.load(R.drawable.avatar_unknown_medium))
                    .apply(centerCrop)
                    .into(holder.imageView);
        }

        holder.goldPerMinutes.setText(context.getString(R.string.only_digital_value, currentPlayer.getGoldPerMin()));
        holder.heroDamage.setText(context.getString(R.string.only_digital_value, currentPlayer.getHeroDamage()));
        holder.heal.setText(context.getString(R.string.only_digital_value, currentPlayer.getHeroHealing()));
        holder.lastHits.setText(context.getString(R.string.only_digital_value, currentPlayer.getLastHits()));
        holder.playerLvl.setText(context.getString(R.string.only_digital_value, currentPlayer.getLevel()));
        holder.random.setText(currentPlayer.isRandomed() ? context.getString(R.string.yes) : context.getString(R.string.no));
        holder.towerDamage.setText(context.getString(R.string.only_digital_value, currentPlayer.getTowerDamage()));
        holder.xpPerMinutes.setText(context.getString(R.string.only_digital_value, currentPlayer.getXpPerMin()));
        holder.totalGold.setText(context.getString(R.string.only_digital_value, currentPlayer.getTotalGold()));
        holder.denies.setText(context.getString(R.string.only_digital_value, currentPlayer.getDenies()));

        final boolean isExpanded = position == mExpandedPosition;
        holder.blockDetail.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            previousExpandedPosition = position;

        holder.itemView.setOnClickListener(v -> {
            mExpandedPosition = isExpanded ? -1 : position;
            notifyItemChanged(previousExpandedPosition);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        if (players != null) {
            return players.size();
        } else {
            return 0;
        }
    }

    public void setData(ItemsWithMatchDetail itemsWithMatchDetail) {
        this.matchFullInfo = itemsWithMatchDetail.getMatchFullInfo();
        this.players = itemsWithMatchDetail.getMatchFullInfo().getPlayers();
        this.items = itemsWithMatchDetail.getItems();
        this.heroSparseArray = itemsWithMatchDetail.getHeroes();
        this.isRadiantWin = matchFullInfo.isRadiantWin();

        notifyDataSetChanged();
    }

    private String getRealPlayerName(Player playerInfo) {
        String name;
        if (playerInfo.getName() != null) {
            name = playerInfo.getName();
        } else if (playerInfo.getPersonaname() != null) {
            name = playerInfo.getPersonaname();
        } else {
            name = context.getResources().getString(R.string.anonymous);
        }

        return name;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
}