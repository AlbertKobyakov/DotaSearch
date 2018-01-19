package com.example.albert.dotasearch.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.view.Menu;
import android.view.MenuInflater;
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

    public static List<FoundUser> foundUsers;
    private FoundUserAdapter mAdapter;

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_user_activity);

        ButterKnife.bind(this);

        initToolbar();

        Bundle bundle = getIntent().getExtras();
        foundUsers = bundle.getParcelableArrayList("com.example.albert.dotasearch.model.FoundUser");

        mAdapter = new FoundUserAdapter(foundUsers, FoundUserActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                FoundUser foundUser = foundUsers.get(position);

                Toast.makeText(getApplicationContext(), foundUser.getAccountId() + " is selected!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(FoundUserActivity.this, PlayerInfoActivity.class);
                intent.putExtra("accountId", foundUser.getAccountId());
                intent.putExtra("personalName", foundUser.getPersonaname());
                startActivity(intent);
            }

            /*@Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Delete is selected?", Toast.LENGTH_SHORT).show();
            }*/
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void initToolbar() {
            toolbar.setTitle(R.string.search_players);

            toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_left));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            //toolbar.inflateMenu(R.menu.menu_main);
    }
}
