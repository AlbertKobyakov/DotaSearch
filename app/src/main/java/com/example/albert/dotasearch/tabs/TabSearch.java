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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TabSearch extends AbstractTabFragment {
    private final static int LAYOUT = R.layout.fragment_search;
    private final static String TAG = "TabSearch";

    private Unbinder unbinder;

    @BindView(R.id.btn_search) Button btnSearch;
    @BindView(R.id.search_edit) EditText searchEditText;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");

        final View view = inflater.inflate(LAYOUT, container, false);

        unbinder = ButterKnife.bind(this, view);

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

            UtilDota.initRetrofitRx().getFoundUsersRx(editText)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            foundUsers -> apiSuccess(foundUsers, v),
                            error -> apiError(error, v));
        }
    }

    public void apiError(Throwable t, View v){
        btnSearch.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Log.e(TAG, "apiError: " + t.getMessage() + " " + t.getLocalizedMessage());
        Toast.makeText(v.getContext(), t.getMessage() + " " + 44, Toast.LENGTH_LONG).show();
    }

    public void apiSuccess(List<FoundUser> foundUsers, View v){
        Log.e(TAG, "apiSuccess");
        Intent intent = new Intent(v.getContext(), FoundUserActivity.class);
        ArrayList<FoundUser> repos = new ArrayList<>(foundUsers);
        intent.putExtra("com.example.albert.dotasearch.model.FoundUser", repos);
        startActivity(intent);
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
