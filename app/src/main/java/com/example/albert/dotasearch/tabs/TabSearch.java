package com.example.albert.dotasearch.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albert.dotasearch.AbstractTabFragment;
import com.example.albert.dotasearch.retrofit.DotaClient;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.activity.FoundUserActivity;
import com.example.albert.dotasearch.model.FoundUser;
import com.example.albert.dotasearch.util.UtilDota;

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

    private static final String FRAGMENT_NAME = "TabSearch";
    //private final static int CACHE_SIZE_BYTES = 1024 * 1024 * 2;
    private final static int LAYOUT = R.layout.fragment_search;

    @BindView(R.id.btn_search) Button btnSearch;
    @BindView(R.id.search_edit) EditText searchEditText;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    //@BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e(FRAGMENT_NAME, "onCreateView");

        final View view = inflater.inflate(LAYOUT, container, false);

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
        final String editText = searchEditText.getText().toString();
        if(editText.trim().length() == 0){
            Toast.makeText(v.getContext(), "Вы не ввели не 1 символа ((", Toast.LENGTH_LONG).show();
        } else {
            btnSearch.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            DotaClient client = new UtilDota().initRetrofit("https://api.opendota.com");
            Call<ArrayList<FoundUser>> call = client.getFoundUsers(editText);
            call.enqueue(new Callback<ArrayList<FoundUser>>() {
                @Override
                public void onResponse(Call<ArrayList<FoundUser>> call, Response<ArrayList<FoundUser>> response) {
                    Intent intent = new Intent(v.getContext(), FoundUserActivity.class);
                    ArrayList<FoundUser> repos = response.body();
                    intent.putExtra("com.example.albert.dotasearch.model.FoundUser", repos);
                    startActivity(intent);
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
}
