package com.example.albert.dotasearch.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.retrofit.DotaClient;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.activity.FoundUserActivity;
import com.example.albert.dotasearch.model.FoundUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabSearch extends AbstractTabFragment {

    @BindView(R.id.btn_search) Button btnSearch;
    @BindView(R.id.search_edit) EditText searchEditText;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private final static int CACHE_SIZE_BYTES = 1024 * 1024 * 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e("onCreateView", "onCreateViewFirstPage");

        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(btnSearch != null && btnSearch.getVisibility() == View.INVISIBLE){
            btnSearch.setVisibility(View.VISIBLE);
        }
        if(progressBar != null && progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public static TabSearch getInstance(Context context) {
        Bundle args = new Bundle();
        TabSearch fragment = new TabSearch();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.search));
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @OnClick(R.id.btn_search)
    public void onClick(final View v) {
        btnSearch.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        String editText = searchEditText.getText().toString();

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.cache(new Cache(v.getContext().getCacheDir(), CACHE_SIZE_BYTES));
        OkHttpClient clientAok = builder.build();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl("https://api.opendota.com").client(clientAok).addConverterFactory(GsonConverterFactory.create());
        //retrofitBuilder.baseUrl("https://api.opendota.com").client(clientAok).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = retrofitBuilder.build();

        DotaClient client = retrofit.create(DotaClient.class);

        //DotaClient client = new UtilDota().initRetrofit();
        Call<ArrayList<FoundUser>> call = client.getFoundUsers(editText);
        call.enqueue(new Callback<ArrayList<FoundUser>>() {
            @Override
            public void onResponse(Call<ArrayList<FoundUser>> call, Response<ArrayList<FoundUser>> response) {
                //listView.setAdapter(new DotaAdapter(MainActivity.this, response.body()));

                /*mAdapter = new FoundUserAdapter(response.body(), MainActivity.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();*/


                Intent intent = new Intent(v.getContext(), FoundUserActivity.class);
                ArrayList<FoundUser> repos = response.body();
                intent.putExtra("com.example.albert.dotasearch.model.FoundUser", repos);
                startActivity(intent);
                /*List<FoundUser> repos = response.body();
                Picasso.with(getApplicationContext()).load(repos.get(1).getAvatarfull()).fit().into(imageView);
                Toast.makeText(MainActivity.this, repos.get(1).getAccountId()+"", Toast.LENGTH_LONG).show();*/
            }

            @Override
            public void onFailure(Call<ArrayList<FoundUser>> call, Throwable t) {
                btnSearch.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("ERRRO", t.getMessage() + " " + t.getLocalizedMessage());
                Toast.makeText(v.getContext(), t.getMessage() + " " + 44, Toast.LENGTH_LONG).show();
            }
        });
    }
}
