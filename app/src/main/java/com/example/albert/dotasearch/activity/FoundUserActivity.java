package com.example.albert.dotasearch.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.RecyclerTouchListener;
import com.example.albert.dotasearch.adapter.FoundUserAdapter;
import com.example.albert.dotasearch.model.FoundUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoundUserActivity extends AppCompatActivity {

    private List<FoundUser> movieList;
    private FoundUserAdapter mAdapter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.searchView) SearchView searchView;
    @BindView(R.id.searchPlayer) EditText editText;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_user_activity);

        ButterKnife.bind(this);

        initToolbar();

        Bundle bundle = getIntent().getExtras();
        movieList = bundle.getParcelableArrayList("com.example.albert.dotasearch.model.FoundUser");

        /*movieList = getIntent().getParcelableExtra("FoundUserList");
        ArrayList<FoundUser> ml = getIntent().getParcelableExtra("FoundUserList");*/

        mAdapter = new FoundUserAdapter(movieList, FoundUserActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                FoundUser foundUser = movieList.get(position);
                Toast.makeText(getApplicationContext(), foundUser.getAccountId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            /*@Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Delete is selected?", Toast.LENGTH_SHORT).show();
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
                mAdapter.filter(newText);
                return true;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initToolbar() {
        //toolbar = findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle(R.string.search_players);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return false;
                }
            });
        }
    }

    private void prepareMovieData() {
        movieList = getIntent().getParcelableExtra("FoundUserList");

        //mAdapter.notifyDataSetChanged();
    }
}
