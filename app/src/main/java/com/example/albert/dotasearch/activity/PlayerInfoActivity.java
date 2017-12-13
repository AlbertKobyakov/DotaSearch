package com.example.albert.dotasearch.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.TabsFragmentAdapter;
import com.example.albert.dotasearch.TabsFragmentPlayerInfoAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerInfoActivity extends AppCompatActivity {

    private ViewPager viewPager;
    public Toolbar toolbar;

    //@BindView(R.id.testText) TextView test;
    public static long accountId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);

        ButterKnife.bind(this);

        accountId = getIntent().getLongExtra("accountId", 0);
        initToolbar();
        initTabs();
    }

    private void initTabs() {
        viewPager = findViewById(R.id.viewPager);

        viewPager.setOffscreenPageLimit(2);

        TabsFragmentPlayerInfoAdapter adapter = new TabsFragmentPlayerInfoAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        //enable search to toolbar
        //toolbar.inflateMenu(R.menu.menu_fragment);
    }
}
