package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.Record;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {
    private static final String TAG = "RecordAdapter";
    private static final int LAYOUT = R.layout.record_list_row;
    private static final int DURATION_MILLIS = 1000;

    private List<Record> recordListForAdapter;
    private Context context;
    private String titleScore;
    private String typeRecord;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_player)
        TextView name;
        @BindView(R.id.team_player)
        TextView team;
        @BindView(R.id.hero_img)
        ImageView heroImg;
        @BindView(R.id.score_title)
        TextView scoreTitle;

        MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public RecordAdapter(Context context, String titleTab, String typeRecord) {
        this.context = context;
        this.titleScore = titleTab;
        this.typeRecord = typeRecord;
    }

    @NonNull
    @Override
    public RecordAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new RecordAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.MyViewHolder holder, int position) {
        Record currentRecord = recordListForAdapter.get(position);

        if (typeRecord.equals("duration")) {
            long seconds = Long.parseLong(currentRecord.getScore());
            String duration = DateUtils.formatElapsedTime(seconds);
            holder.name.setText(duration);
        } else if (typeRecord.equals("hero_damage") || typeRecord.equals("tower_damage") || typeRecord.equals("hero_healing")) {
            double damage = Double.parseDouble(currentRecord.getScore());
            String formatDamage = formatNumber(damage);
            holder.name.setText(formatDamage);
        } else {
            holder.name.setText(currentRecord.getScore());
        }

        holder.scoreTitle.setText(titleScore);

        if (currentRecord.getStartTime() != null) {
            Long startTimeMillis = Long.parseLong(currentRecord.getStartTime()) * 1000;
            CharSequence updateTimeString = DateUtils.getRelativeTimeSpanString(
                    startTimeMillis, System.currentTimeMillis(), 0);
            holder.team.setText(updateTimeString);
        } else {
            holder.team.setText(context.getString(R.string.unknown));
        }

        if (currentRecord.getHeroImgUrl() != null) {
            Glide.with(context)
                    .load(currentRecord.getHeroImgUrl())
                    .error(Glide.with(context).load(R.drawable.avatar_unknown_medium))
                    .into(holder.heroImg);
        } else {
            holder.heroImg.setVisibility(View.GONE);
        }

        setFadeAnimation(holder.itemView);
    }

    public void setData(List<Record> records) {
        recordListForAdapter = new ArrayList<>(records);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (recordListForAdapter != null) {
            return recordListForAdapter.size();
        } else {
            return 0;
        }
    }

    public long getMatchIdByPosition(int position) {
        if (recordListForAdapter != null && recordListForAdapter.size() >= position) {
            return Long.parseLong(recordListForAdapter.get(position).getMatchId());
        } else {
            return 0;
        }
    }

    private void setFadeAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(DURATION_MILLIS);
        view.startAnimation(anim);
    }

    private String formatNumber(double d) {

        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();

        formatter.applyPattern("#,###");

        return formatter.format(d);
    }

    public void sortByDateUp() {
        Collections.sort(recordListForAdapter, (record, record1) -> {
            long date1 = Long.parseLong(record.getStartTime());
            long date2 = Long.parseLong(record1.getStartTime());
            return Long.compare(date2, date1);
        });

        notifyDataSetChanged();
    }

    public void sortByDateDown() {
        Collections.sort(recordListForAdapter, (record, record1) -> {
            long date1 = Long.parseLong(record.getStartTime());
            long date2 = Long.parseLong(record1.getStartTime());
            return Long.compare(date1, date2);
        });

        notifyDataSetChanged();
    }

    public void sortByScoreUp() {
        Collections.sort(recordListForAdapter, (record, record1) -> {
            int score1 = Integer.parseInt(record.getScore());
            int score2 = Integer.parseInt(record1.getScore());
            return Long.compare(score1, score2);
        });

        notifyDataSetChanged();
    }

    public void sortByScoreDown() {
        Collections.sort(recordListForAdapter, (record, record1) -> {
            int score1 = Integer.parseInt(record.getScore());
            int score2 = Integer.parseInt(record1.getScore());
            return Long.compare(score2, score1);
        });

        notifyDataSetChanged();
    }
}