package com.example.albert.dotasearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.albert.dotasearch.activity.FoundUserActivity;
import com.example.albert.dotasearch.adapter.DotaAdapter;
import com.example.albert.dotasearch.adapter.FoundUserAdapter;
import com.example.albert.dotasearch.model.FoundUser;

import java.util.ArrayList;
import java.util.List;

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

public class MainActivity extends AppCompatActivity {

    private final static int CACHE_SIZE_BYTES = 1024 * 1024 * 2;

    @BindView(R.id.search_edit) EditText searchEditText;
    @BindView(R.id.btn_search) Button btnSearch;
    //@BindView(R.id.imageView) ImageView imageView;
    //@BindView(R.id.pagination_list) ListView listView;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private FoundUserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(btnSearch != null && btnSearch.getVisibility() == View.INVISIBLE){
            btnSearch.setVisibility(View.VISIBLE);
        }
        if(progressBar != null && progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.btn_search)
    public void onClick(View v) {
        btnSearch.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        String editText = searchEditText.getText().toString();

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.cache(new Cache(getApplicationContext().getCacheDir(), CACHE_SIZE_BYTES));
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


                Intent intent = new Intent(MainActivity.this, FoundUserActivity.class);
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
                Toast.makeText(MainActivity.this, t.getMessage() + " " + 44, Toast.LENGTH_LONG).show();
            }
        });

        /*DotaClient client = new UtilDota().initRetrofit();
        Call<List<ProPlayer>> call = client.getProPlayer();
        call.enqueue(new Callback<List<ProPlayer>>() {
            @Override
            public void onResponse(Call<List<ProPlayer>> call, Response<List<ProPlayer>> response) {
                List<ProPlayer> repos = response.body();
                Toast.makeText(MainActivity.this, repos.get(5).getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<ProPlayer>> call, Throwable t) {
                Log.e("ERRRO", t.getMessage() + " " + t.getLocalizedMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });*/
    }
}
