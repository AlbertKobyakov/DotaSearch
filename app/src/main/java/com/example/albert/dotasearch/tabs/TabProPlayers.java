package com.example.albert.dotasearch.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.adapter.ProPlayerAdapter;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.retrofit.DotaClient;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabProPlayers extends AbstractTabFragment {

    private static final String FRAGMENT_NAME = "TabProPlayers";
    private static final int LAYOUT = R.layout.fragment_pro_players;

    public static List<ProPlayer> proPlayers = new ArrayList<>();
    private ProPlayerAdapter mAdapter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.searchView) SearchView searchView;
    @BindView(R.id.searchPlayer) EditText editText;
    @BindView(R.id.text_view_no_internet) TextView textViewNotInternet;

    public static TabProPlayers getInstance(Context context) {
        Bundle args = new Bundle();
        TabProPlayers fragment = new TabProPlayers();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_pro_players));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        Log.e(FRAGMENT_NAME, "onCreateView");

        ButterKnife.bind(this, view);

        if(proPlayers.size() < 10){
            getAllProPlayers(view);
        } else {

            Toast.makeText(view.getContext(), "Load to cash", Toast.LENGTH_LONG).show();

            setAdapterAndRecyclerView();
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(view.getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ProPlayer proPlayer = proPlayers.get(position);
                Toast.makeText(view.getContext(), proPlayer.getAccountId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            /*@Override
            public void onLongClick(View view, int position) {
                Toast.makeText(view.getContext(), "Delete is selected?", Toast.LENGTH_SHORT).show();
            }*/
        }));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(mAdapter != null){
                    mAdapter.filter(newText);
                }
                return true;
            }
        });
        return view;
    }

    public void getAllProPlayers(final View view){
        DotaClient client = new UtilDota().initRetrofit("https://api.opendota.com");
        Call<List<ProPlayer>> call = client.getProPlayer();
        call.enqueue(new Callback<List<ProPlayer>>() {
            @Override
            public void onResponse(Call<List<ProPlayer>> call, Response<List<ProPlayer>> response) {
                proPlayers = response.body();
                setProPlayerDefaultPersonalNameTeamNameAndAvatar(proPlayers);

                if(proPlayers != null){
                    setAdapterAndRecyclerView();
                    Toast.makeText(view.getContext(), proPlayers.get(5).getName(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(view.getContext(), "Что-то пошло не так", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProPlayer>> call, Throwable t) {
                searchView.setVisibility(View.GONE);
                editText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);

                if(t.getMessage().equals("timeout")){
                    textViewNotInternet.setText("Ошибка сервера. Пожалуйста попробуйте повторить попытку позже(((");
                    textViewNotInternet.setVisibility(View.VISIBLE);
                } else {
                    textViewNotInternet.setVisibility(View.VISIBLE);
                }
                Log.e("ERRRO", t.getMessage() + " " + t.getLocalizedMessage());
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setAdapterAndRecyclerView(){
        mAdapter = new ProPlayerAdapter(proPlayers, view.getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    public void setProPlayerDefaultPersonalNameTeamNameAndAvatar(List<ProPlayer> proPlayers){
        for(ProPlayer proPlayer : proPlayers){
            if(proPlayer.getPersonaname() == null || proPlayer.getPersonaname().trim().length() == 0){
                proPlayer.setPersonaname(getString(R.string.unknown));
            }
            if(proPlayer.getTeamName() == null || proPlayer.getTeamName().trim().length() == 0){
                proPlayer.setTeamName(getString(R.string.unknown));
            }
            if(proPlayer.getAvatarmedium() == null){
                proPlayer.setAvatarmedium("https://steamcdn-a.akamaihd.net/steamcommunity/public" +
                        "/images/avatars/fe/fef49e7fa7e1997310d705b2a6158ff8dc1cdfeb_medium.jpg");
            }
        }
    }
}
