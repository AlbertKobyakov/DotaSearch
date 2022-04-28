package com.kobyakov.d2s.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.model.FoundPlayer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FoundPlayerAdapter extends RecyclerView.Adapter<FoundPlayerAdapter.MyViewHolder> {
    private static final String TAG = "FoundPlayerAdapter";
    private static final int LAYOUT = R.layout.found_user_list_row;
    private static final long THREE_HOURS = 10800000;

    private List<FoundPlayer> foundUsersCopy;
    private List<FoundPlayer> foundPlayersForAdapter;
    public Context context;
    private RequestManager glide;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_found_player)
        TextView nameFoundPlayer;
        @BindView(R.id.last_match_found_player)
        TextView lastMatchFoundPlayer;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.block_last_match_time)
        LinearLayout blockLastMatchTime;


        public MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public FoundPlayerAdapter(Context context, RequestManager glide) {
        this.context = context;
        this.glide = glide;
    }

    public FoundPlayer getFoundPlayerByPosition(int position) {
        return foundPlayersForAdapter.get(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new MyViewHolder(itemView);
    }

    public void setData(List<FoundPlayer> foundPlayers) {
        foundPlayersForAdapter = new ArrayList<>(foundPlayers);
        foundUsersCopy = new ArrayList<>(foundPlayers);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (foundPlayersForAdapter != null) {
            FoundPlayer foundPlayer = foundPlayersForAdapter.get(position);
            String lastMatchTime = generateLastMatchTime(foundPlayer.getLastMatchTime());

            holder.nameFoundPlayer.setText(foundPlayer.getPersonaname());
            if (lastMatchTime.trim().length() > 0) {
                holder.blockLastMatchTime.setVisibility(View.VISIBLE);
                holder.lastMatchFoundPlayer.setText(lastMatchTime);
            }

            Log.d(TAG, "onBindViewHolder " + position + " " + foundPlayer.getLastMatchTime());

            RequestOptions fitCenter = new RequestOptions()
                    .fitCenter();

            glide.load(foundPlayer.getAvatarfull())
                    .error(glide.load(R.drawable.avatar_unknown_medium).apply(fitCenter))
                    .apply(fitCenter)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (foundPlayersForAdapter != null && foundPlayersForAdapter.size() > 0) {
            return foundPlayersForAdapter.size();
        } else {
            return 0;
        }
    }

    public void filter(String text) {
        List<FoundPlayer> foundPlayersFiltered = new ArrayList<>();

        if (!text.isEmpty()) {
            text = text.toLowerCase();
            for (FoundPlayer foundPlayer : foundUsersCopy) {
                if (foundPlayer != null && foundPlayer.getPersonaname() != null) {
                    if (foundPlayer.getPersonaname().toLowerCase().contains(text)) {
                        foundPlayersFiltered.add(foundPlayer);
                    }
                }
            }
        } else {
            foundPlayersFiltered = foundUsersCopy;
        }

        foundPlayersForAdapter = foundPlayersFiltered;

        notifyDataSetChanged();
    }

    private String generateLastMatchTime(String lastMatch) {
        String result = "";
        if (lastMatch != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

            Calendar lastMatchTime = Calendar.getInstance();
            try {
                lastMatchTime.setTime(format.parse(lastMatch));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long startTimeMillis = lastMatchTime.getTimeInMillis() + THREE_HOURS;
            result = DateUtils.getRelativeTimeSpanString(
                    startTimeMillis, System.currentTimeMillis(), 0).toString();
        }
        return result;
    }
}
