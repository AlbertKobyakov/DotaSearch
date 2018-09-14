package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.FavoritePlayer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritePlayersAdapter extends RecyclerView.Adapter<FavoritePlayersAdapter.MyViewHolder> implements View.OnLongClickListener {
    public static final String TAG = "FoundPlayerAdapter";
    public static final int LAYOUT = R.layout.favorite_player_list_row;

    private List<FavoritePlayer> favoritePlayers;
    private Context context;

    public FavoritePlayersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (favoritePlayers != null) {
            FavoritePlayer favoritePlayer = favoritePlayers.get(position);
            holder.namePlayer.setText(favoritePlayer.getPersonaname());

            RequestOptions fitCenter = new RequestOptions()
                    .fitCenter();

            Glide.with(context)
                    .load(favoritePlayer.getAvatarfull())
                    .error(Glide.with(context).load(R.drawable.avatar_unknown_medium).apply(RequestOptions.circleCropTransform()))
                    .apply(fitCenter)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imagePlayer);
        } else {
            holder.namePlayer.setText("список избранных пользователей пуст");
        }
    }

    public void setData(List<FavoritePlayer> favoritePlayers) {
        this.favoritePlayers = favoritePlayers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (favoritePlayers != null) {
            return favoritePlayers.size();
        }
        return 0;
    }

    @Override
    public boolean onLongClick(View view) {
        return true;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_player)
        TextView namePlayer;
        @BindView(R.id.image_player)
        ImageView imagePlayer;

        public MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }
}
