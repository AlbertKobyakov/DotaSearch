package com.kobyakov.d2s.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.model.TeamPlayer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamPlayerAdapter extends RecyclerView.Adapter<TeamPlayerAdapter.MyViewHolder> {
    private List<TeamPlayer> teamPlayers = new ArrayList<>();
    public Context context;
    private static final String TAG = "TeamPlayerAdapter";
    private static final int LAYOUT = R.layout.team_player_list_row_with_header;
    private RequestManager glide;
    private int countPlayerInTeam;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_player)
        TextView namePlayer;
        @BindView(R.id.all_matches_played)
        TextView allMatchesPlayed;
        @BindView(R.id.matches_win)
        TextView matchesWin;
        @BindView(R.id.player_img)
        ImageView playerImg;

        @BindView(R.id.header)
        LinearLayout header;
        @BindView(R.id.players_type)
        TextView playersType;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public TeamPlayerAdapter(Context context, RequestManager glide) {
        this.context = context;
        this.glide = glide;
    }

    @NonNull
    @Override
    public TeamPlayerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new TeamPlayerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamPlayerAdapter.MyViewHolder holder, int position) {
        if (teamPlayers != null) {
            TeamPlayer teamPlayer = teamPlayers.get(position);

            if (countPlayerInTeam != 0) {
                if (position == 0) {
                    holder.header.setVisibility(View.VISIBLE);
                    holder.playersType.setText(R.string.current_team);
                } else if (position == countPlayerInTeam) {
                    holder.header.setVisibility(View.VISIBLE);
                    holder.playersType.setText(R.string.former_players);
                } else {
                    holder.header.setVisibility(View.GONE);
                }
            } else {
                if (position == 0) {
                    holder.header.setVisibility(View.VISIBLE);
                    holder.playersType.setText(R.string.former_players);
                } else {
                    holder.header.setVisibility(View.GONE);
                }
            }


            if (teamPlayer != null) {
                String name = teamPlayer.getName() != null && !teamPlayer.getName().trim().equals("") ? teamPlayer.getName() : context.getString(R.string.unknown);

                String allMatches = teamPlayer.getGamesPlayed() + "";
                double win = (double) teamPlayer.getWins() / (double) teamPlayer.getGamesPlayed() * 100;

                holder.namePlayer.setText(name);
                holder.allMatchesPlayed.setText(allMatches);
                holder.matchesWin.setText(twoNumberAfterPoint(win));
                //holder.isCurrentTeamMember.setText(currentMemberStatus);

                int resourceIdTemp = getResourceDrawableByName("player_" + teamPlayer.getAccountId(), context);
                int resourceId = resourceIdTemp != 0 ? resourceIdTemp : R.drawable.ic_portrait;

                glide.load(resourceId)
                        .error(glide.load(R.drawable.ic_portrait))
                        .into(holder.playerImg);
            }
        } else {
            Log.d(TAG, "empty");
        }
    }

    private int getResourceDrawableByName(String nameDrawable, Context context) {
        Resources resources = context.getResources();
        return resources.getIdentifier(nameDrawable, "drawable", context.getPackageName());
    }

    private String twoNumberAfterPoint(double number) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(number) + "%";
    }

    public void setData(List<TeamPlayer> teamPlayersResponse) {

        Collections.sort(teamPlayersResponse, (teamPlayer, t1) -> Boolean.compare(t1.isIsCurrentTeamMember(), teamPlayer.isIsCurrentTeamMember()));

        teamPlayers = teamPlayersResponse;

        for (TeamPlayer teamPlayer : teamPlayers) {
            if (teamPlayer.isIsCurrentTeamMember()) {
                countPlayerInTeam++;
            } else {
                break;
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (teamPlayers != null) {
            return teamPlayers.size();
        } else {
            return 0;
        }
    }
}