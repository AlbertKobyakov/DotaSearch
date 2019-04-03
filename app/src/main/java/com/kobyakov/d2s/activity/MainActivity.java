package com.kobyakov.d2s.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.kobyakov.d2s.R;
import com.kobyakov.d2s.tabs.FragmentRecords;
import com.kobyakov.d2s.tabs.TabProPlayers;
import com.kobyakov.d2s.tabs.TabProTeam;
import com.kobyakov.d2s.tabs.TabRanking;
import com.kobyakov.d2s.tabs.TabSearch;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int LAYOUT = R.layout.activity_main;

    private TabSearch tabSearch = new TabSearch();
    private TabProPlayers tabProPlayers = new TabProPlayers();
    private TabProTeam tabProTeam = new TabProTeam();
    private FragmentRecords tabProMatch = new FragmentRecords();

    private TabRanking tabRanking = new TabRanking();

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView navigation;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.player_overview:
                    changeFragment(tabSearch, "tabSearch");
                    return true;
                case R.id.pros:
                    changeFragment(tabProPlayers, "tabProPlayers");
                    return true;
                case R.id.navigation_notifications:
                    changeFragment(tabRanking, "tabRanking");
                    return true;
                case R.id.navigation_notifications1:
                    changeFragment(tabProTeam, "tabProTeam");
                    return true;
                case R.id.navigation_notifications2:
                    changeFragment(tabProMatch, "tabProMatch");
                    return true;
            }
            return false;
        });


        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.player_overview);
        }

        //remove focus EditText
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(findViewById(R.id.bottom_navigation_view), getResources().getString(R.string.double_click_to_exit), Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    public void changeFragment(Fragment fragment, String tagFragmentName) {

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment curFrag = mFragmentManager.getPrimaryNavigationFragment();
        if (curFrag != null) {
            fragmentTransaction.detach(curFrag);
        }

        Fragment fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction.add(R.id.content, fragmentTemp, tagFragmentName);
        } else {
            fragmentTransaction.attach(fragmentTemp);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitAllowingStateLoss();
    }
    

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}