package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.activity.FoundUserActivity;
import com.example.albert.dotasearch.model.FoundUser;
import com.example.albert.dotasearch.util.UtilDota;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoundUserAdapter extends RecyclerView.Adapter<FoundUserAdapter.MyViewHolder>{
    public static final String TAG = "FoundUserAdapter";
    public static final int LAYOUT = R.layout.found_user_list_row;

    private List<FoundUser> foundUsersCopy;
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


    public FoundUserAdapter(List<FoundUser> moviesList, Context context) {
        this.foundUsersCopy = new ArrayList<>(moviesList);
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
        FoundUser foundUser = FoundUserActivity.foundUsers.get(position);
        holder.nameFoundPlayer.setText(foundUser.getPersonaname());
        holder.lastMatchFoundPlayer.setText(UtilDota.generateLastMatchTime(foundUser.getLastMatchTime()));
        Picasso.with(context).load(foundUser.getAvatarfull()).fit().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return FoundUserActivity.foundUsers.size();
    }

    public void filter(String text) {
        FoundUserActivity.foundUsers.clear();
        if(text.isEmpty()){
            FoundUserActivity.foundUsers = new ArrayList<>(foundUsersCopy);
        } else{
            text = text.toLowerCase();
            for(FoundUser item: foundUsersCopy){
                if(item.getPersonaname().toLowerCase().contains(text) ){
                    FoundUserActivity.foundUsers.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
