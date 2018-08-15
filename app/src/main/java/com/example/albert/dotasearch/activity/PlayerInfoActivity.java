package com.example.albert.dotasearch.activity;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.model.PlayerOverviewCombine;
import com.example.albert.dotasearch.modelfactory.FactoryForPlayerInfoViewModel;
import com.example.albert.dotasearch.tabs.FragmentForPlayerActivity1;
import com.example.albert.dotasearch.tabs.FragmentForPlayerActivity2;
import com.example.albert.dotasearch.tabs.TabPlayerOverview;
import com.example.albert.dotasearch.viewModel.PlayerInfoViewModel;

public class PlayerInfoActivity extends AppCompatActivity {

    public static final String TAG = "PlayerInfoActivity";
    public static final int LAYOUT = R.layout.activity_player_info;
    public Toolbar toolbar;

    public static long accountId;
    public static String personalName;
    public static String urlPlayer;
    public static PlayerInfoViewModel viewModel;

    private TabPlayerOverview tabPlayerOverview;
    private FragmentForPlayerActivity1 fragmentForPlayerActivity1;
    private FragmentForPlayerActivity2 fragmentForPlayerActivity2;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.player_overview:
                    tabPlayerOverview = new TabPlayerOverview();
                    changeFragment(tabPlayerOverview);
                    return true;
                case R.id.navigation_dashboard:
                    fragmentForPlayerActivity1 = new FragmentForPlayerActivity1();
                    changeFragment(fragmentForPlayerActivity1);
                    return true;
                case R.id.navigation_notifications:
                    fragmentForPlayerActivity2 = new FragmentForPlayerActivity2();
                    changeFragment(fragmentForPlayerActivity2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState==null){
            navigation.setSelectedItemId(R.id.player_overview);
        }

        accountId = getIntent().getLongExtra("accountId", 0);
        personalName = getIntent().getStringExtra("personalName");
        urlPlayer = getIntent().getStringExtra("urlPlayer");

        viewModel = ViewModelProviders.of(this, new FactoryForPlayerInfoViewModel(accountId)).get(PlayerInfoViewModel.class);
        LiveData<PlayerOverviewCombine> playerFullInfo = viewModel.getPlayerFullInfo();
        playerFullInfo.observe(this, new Observer<PlayerOverviewCombine>() {
            @Override
            public void onChanged(@Nullable PlayerOverviewCombine playerOverviewCombine) {
                Log.e("FRAGMENT","777777777777777777777");
                if (navigation.getVisibility() == View.INVISIBLE){
                    navigation.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }
}
