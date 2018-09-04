package com.example.albert.dotasearch.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.TabsFragmentAdapter;
import com.example.albert.dotasearch.tabs.TabProMatch;
import com.example.albert.dotasearch.tabs.TabProPlayers;
import com.example.albert.dotasearch.tabs.TabProTeam;
import com.example.albert.dotasearch.tabs.TabRanking;
import com.example.albert.dotasearch.tabs.TabSearch;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public Toolbar toolbar;
    private static final String TAG = "MainActivity";
    private static final int LAYOUT = R.layout.activity_main;

    private TabSearch tabSearch;
    private TabProPlayers tabProPlayers;
    private TabRanking tabRanking;
    private TabProTeam tabProTeam;
    private TabProMatch tabProMatch;

    @BindView(R.id.bottom_navigation_view) BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.player_overview:
                    tabSearch = new TabSearch();
                    changeFragment(tabSearch);
                    return true;
                case R.id.pros:
                    tabProPlayers = new TabProPlayers();
                    changeFragment(tabProPlayers);
                    return true;
                case R.id.navigation_notifications:
                    tabRanking = new TabRanking();
                    changeFragment(tabRanking);
                    return true;

                case R.id.navigation_notifications1:
                    if(tabProTeam == null){
                        Log.d(TAG, "new");
                        tabProTeam = new TabProTeam();
                    }
                    changeFragment(tabProTeam);
                    return true;

                case R.id.navigation_notifications2:
                    changeFragment(new TabProMatch());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.player_overview:
                        tabSearch = new TabSearch();
                        changeFragment(tabSearch);
                        return true;
                    case R.id.pros:
                        tabProPlayers = new TabProPlayers();
                        changeFragment(tabProPlayers);
                        return true;
                    case R.id.navigation_notifications:
                        tabRanking = new TabRanking();
                        changeFragment(tabRanking);
                        return true;

                    case R.id.navigation_notifications1:
                        if(tabProTeam == null){
                            Log.d(TAG, "new");
                            tabProTeam = new TabProTeam();
                        }
                        changeFragment(tabProTeam);
                        return true;

                    case R.id.navigation_notifications2:
                        changeFragment(new TabProMatch());
                        return true;
                }
                return false;
            }
        });

        if(savedInstanceState==null){
            navigation.setSelectedItemId(R.id.player_overview);
        }

        //remove focus EditText
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initToolbar();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        setSupportActionBar(toolbar);
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }
}
