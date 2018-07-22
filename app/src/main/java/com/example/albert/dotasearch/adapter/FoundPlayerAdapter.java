package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.activity.FoundPlayerActivity;
import com.example.albert.dotasearch.model.FoundPlayer;
import com.example.albert.dotasearch.util.UtilDota;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoundPlayerAdapter extends RecyclerView.Adapter<FoundPlayerAdapter.MyViewHolder>{
    public static final String TAG = "FoundPlayerAdapter";
    public static final int LAYOUT = R.layout.found_user_list_row;

    private List<FoundPlayer> foundUsersCopy;
    public Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_found_player) TextView nameFoundPlayer;
        @BindView(R.id.last_match_found_player) TextView lastMatchFoundPlayer;
        @BindView(R.id.imageView) ImageView imageView;


        public MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }


    public FoundPlayerAdapter(List<FoundPlayer> foundPlayers, Context context) {
        this.foundUsersCopy = new ArrayList<>(foundPlayers);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(LAYOUT, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FoundPlayer foundPlayer = FoundPlayerActivity.foundPlayers.get(position);
        holder.nameFoundPlayer.setText(foundPlayer.getPersonaname());
        holder.lastMatchFoundPlayer.setText(generateLastMatchTime(foundPlayer.getLastMatchTime()));
        Log.d(TAG, "onBindViewHolder " + position + " " + foundPlayer.getLastMatchTime());
        //Picasso.with(context).load(foundPlayer.getAvatarfull()).fit().into(holder.imageView);
        Glide.with(context)
                .load(foundPlayer.getAvatarfull())
                .fitCenter()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return FoundPlayerActivity.foundPlayers.size();
    }

    public void filter(String text) {
        FoundPlayerActivity.foundPlayers.clear();
        if(text.isEmpty()){
            FoundPlayerActivity.foundPlayers = new ArrayList<>(foundUsersCopy);
        } else{
            text = text.toLowerCase();
            for(FoundPlayer item: foundUsersCopy){
                if(item.getPersonaname().toLowerCase().contains(text) ){
                    FoundPlayerActivity.foundPlayers.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static String generateLastMatchTime(String lastMatch){
        String result;
        if(lastMatch != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

            Calendar lastMatchTime = Calendar.getInstance();
            try {
                lastMatchTime.setTime(format.parse(lastMatch));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar now = Calendar.getInstance();

            long second = 1000;
            long minute = second * 60;
            long hour = minute * 60;
            long day = hour*24;
            long month = day * 30;
            long year = month * 12;

            long diff = now.getTimeInMillis() - lastMatchTime.getTimeInMillis();

            //Log.d(TAG, diff + "");
            if(diff/year == 0){
                if(diff/month == 0){
                    if(diff/day == 0){
                        if(diff/hour == 0){
                            if(diff/minute < 60){
                                result = diff/year + "less 1 minute ago";
                            } else {
                                result = diff/year + " minute ago";
                            }
                        } else {
                            result = diff/hour + " hour ago";
                        }
                    } else {
                        result = diff/day + " day ago";
                    }
                } else {
                    result = diff/month + " month ago";
                }
            } else {
                result = diff/year + " year ago";
            }
        } else {
            result = "";
        }
        return result;
    }
}
