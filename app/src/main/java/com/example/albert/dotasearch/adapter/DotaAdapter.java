package com.example.albert.dotasearch.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.FoundUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DotaAdapter extends ArrayAdapter<FoundUser>  {
    private Context context;
    private List<FoundUser> values;

    public DotaAdapter(@NonNull Context context, List<FoundUser> values) {
        super(context, R.layout.list_item_pagination, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_pagination, parent, false);
        }

        TextView textView = row.findViewById(R.id.list_item_pagination_text);
        TextView textViewId = row.findViewById(R.id.list_item_pagination_id);
        ImageView imageView = row.findViewById(R.id.imageView);

        FoundUser item = values.get(position);
        Picasso.with(context).load(item.getAvatarfull()).fit().into(imageView);
        String nickname = item.getPersonaname();
        long id = item.getAccountId();
        Log.e("WotAdapter", id + " " + nickname);
        textViewId.setText(id+"");
        textView.setText(nickname);

        return row;
    }
}
