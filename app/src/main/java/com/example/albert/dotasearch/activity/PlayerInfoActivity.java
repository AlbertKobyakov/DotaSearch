package com.example.albert.dotasearch.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.TabsFragmentPlayerInfoAdapter;

import butterknife.ButterKnife;

public class PlayerInfoActivity extends AppCompatActivity {

    public static final String TAG = "PlayerInfoActivity";
    public static final int LAYOUT = R.layout.activity_player_info;

    private ViewPager viewPager;
    public Toolbar toolbar;

    public static long accountId;
    public static String personalName;
    public static String lastMatchStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        ButterKnife.bind(this);

        accountId = getIntent().getLongExtra("accountId", 0);
        personalName = getIntent().getStringExtra("personalName");
        lastMatchStr = getIntent().getStringExtra("lastMatchStr");

        Toast.makeText(this, accountId + " ", Toast.LENGTH_SHORT).show();

        initToolbar();
        initTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_for_player_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initTabs() {
        viewPager = findViewById(R.id.viewPager);

        viewPager.setOffscreenPageLimit(2);

        TabsFragmentPlayerInfoAdapter adapter = new TabsFragmentPlayerInfoAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.statistics_player, personalName, accountId));

        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_left));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        //enable search to toolbar
        //toolbar.inflateMenu(R.menu.menu_fragment);
    }


}
