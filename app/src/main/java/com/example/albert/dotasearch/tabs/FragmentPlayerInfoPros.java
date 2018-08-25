package com.example.albert.dotasearch.tabs;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.activity.PlayerInfoActivity;
import com.example.albert.dotasearch.adapter.PlayerProsAdapter;
import com.example.albert.dotasearch.model.Pros;

import java.util.List;

import static com.example.albert.dotasearch.activity.PlayerInfoActivity.viewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentPlayerInfoPros extends Fragment {
    private static final String TAG = "FragmentPlayerInfoPros";
    private static final int LAYOUT = R.layout.fragment_player_info_pros;
    private View view;
    public PlayerProsAdapter adapter;
    public List<Pros> allPros;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        view = inflater.inflate(LAYOUT, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel.getPros().observe(this, new Observer<List<Pros>>() {
            @Override
            public void onChanged(@Nullable List<Pros> pros) {
                if(pros != null){
                    setAdapterAndRecyclerView();

                    allPros = pros;
                    adapter.setData(pros);
                    Log.d(TAG, pros.size() + "");
                }
            }
        });
    }

    public void setAdapterAndRecyclerView() {
        adapter = new PlayerProsAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                toPlayerInfoActivity(position);
            }
        }));
    }

    public void toPlayerInfoActivity(int position) {
        Log.e(TAG, "2222");
        Intent intent = new Intent(getActivity(), PlayerInfoActivity.class);
        intent.putExtra("accountId", allPros.get(position).getAccountId());
        intent.putExtra("personalName", allPros.get(position).getName());
        intent.putExtra("urlPlayer", allPros.get(position).getAvatarmedium());
        startActivity(intent);
    }
}
