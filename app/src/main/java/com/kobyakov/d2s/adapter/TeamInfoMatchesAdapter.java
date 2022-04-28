package com.kobyakov.d2s.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.kobyakov.d2s.R;
import com.kobyakov.d2s.model.TeamMatch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeamInfoMatchesAdapter extends RecyclerView.Adapter<TeamInfoMatchesAdapter.MyViewHolder> {
    private static final String TAG = "TeamInfoMatchesAdapter";
    private static final int LAYOUT = R.layout.match_team_list_row;

    private List<TeamMatch> matchesCopy;
    public Context context;
    private RequestManager glide;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.opposing_team_name)
        TextView opposingTeamName;
        @BindView(R.id.match_result)
        TextView matchResult;

        @BindView(R.id.match_duration)
        TextView matchDuration;
        @BindView(R.id.opposing_team_logo)
        ImageView opposingTeamLogo;
        @BindView(R.id.league_name)
        TextView leagueName;
        @BindView(R.id.match_date)
        TextView matchDate;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public TeamInfoMatchesAdapter(Context context, RequestManager glide) {
        this.context = context;
        this.glide = glide;
    }


    @NonNull
    @Override
    public TeamInfoMatchesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new TeamInfoMatchesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamInfoMatchesAdapter.MyViewHolder holder, int position) {
        if (matchesCopy != null) {
            TeamMatch teamMatch = matchesCopy.get(position);

            if (teamMatch != null) {
                String opposingTeamLogo = teamMatch.getOpposingTeamLogo();
                String opposingTeamName = !teamMatch.getOpposingTeamName().trim().equals("") ? teamMatch.getOpposingTeamName() : context.getString(R.string.unknown);
                String league = teamMatch.getLeagueName();
                boolean isWin;
                long seconds = teamMatch.getDuration();
                String duration = DateUtils.formatElapsedTime(seconds);
                long secondsMatchStartTime = (teamMatch.getStartTime()) * 1000;
                String matchStartDate = DateUtils.getRelativeTimeSpanString(secondsMatchStartTime).toString();

                if (teamMatch.isRadiant() && teamMatch.isRadiantWin()) {
                    isWin = true;
                } else if (teamMatch.isRadiant() && !teamMatch.isRadiantWin()) {
                    isWin = false;
                } else if (!teamMatch.isRadiant() && !teamMatch.isRadiantWin()) {
                    isWin = true;
                } else {
                    isWin = false;
                }

                String result = isWin ? context.getString(R.string.win) : context.getString(R.string.lose);

                holder.matchResult.setText(result);
                holder.opposingTeamName.setText(opposingTeamName);
                holder.matchDuration.setText(duration);
                holder.leagueName.setText(league);
                holder.matchDate.setText(matchStartDate);

                glide.load(opposingTeamLogo)
                        .error(glide.load(R.drawable.help))
                        .into(holder.opposingTeamLogo);
            }
        }
    }

    public void setData(List<TeamMatch> match) {
        this.matchesCopy = match;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (matchesCopy != null) {
            return matchesCopy.size();
        } else {
            return 0;
        }
    }
}