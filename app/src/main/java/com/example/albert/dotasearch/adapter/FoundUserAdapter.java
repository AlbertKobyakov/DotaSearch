package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albert.dotasearch.MainActivity;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.FoundUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoundUserAdapter extends RecyclerView.Adapter<FoundUserAdapter.MyViewHolder>{
    private List<FoundUser> foundUsers;
    private List<FoundUser> foundUsersCopy;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView genre;
        public ImageView imageView;


        public MyViewHolder(View view) {
            super(view);
            //title = view.findViewById(R.id.title);
            genre = view.findViewById(R.id.genre);
            imageView = view.findViewById(R.id.imageView);
            //context = view.getContext();
            //year = view.findViewById(R.id.year);
        }
    }


    public FoundUserAdapter(List<FoundUser> moviesList, Context context) {
        this.foundUsers = new ArrayList<>(moviesList);
        this.foundUsersCopy = new ArrayList<>(moviesList);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.found_user_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FoundUser foundUser = foundUsers.get(position);
        //holder.title.setText(foundUser.getAccountId()+"");
        holder.genre.setText(foundUser.getPersonaname());
        Picasso.with(context).load(foundUser.getAvatarfull()).fit().into(holder.imageView);
        //holder.year.setText(foundUser.getAvatarfull());
    }

    @Override
    public int getItemCount() {
        return foundUsers.size();
    }

    public void filter(String text) {
        foundUsers.clear();
        if(text.isEmpty()){
            foundUsers = new ArrayList<>(foundUsersCopy);
        } else{
            text = text.toLowerCase();
            for(FoundUser item: foundUsersCopy){
                if(item.getPersonaname().toLowerCase().contains(text) ){
                    foundUsers.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
