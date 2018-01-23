package com.example.albert.dotasearch.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.TabsFragmentMatchDetailAdapter;
import com.example.albert.dotasearch.TabsFragmentPlayerInfoAdapter;
import com.example.albert.dotasearch.model.MatchFullInfo;
import com.example.albert.dotasearch.util.UtilDota;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MatchDetailActivity extends AppCompatActivity {

    public static final String TAG = "MatchDetailActivity";
    public static final int LAYOUT = R.layout.activity_match_detail;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.viewPager) ViewPager viewPager;

    public static long matchId;
    public static MatchFullInfo matchFullInfo;

    public void setMatchFullInfo(MatchFullInfo matchFullInfoTemp){
        matchFullInfo = matchFullInfoTemp;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        Log.d(TAG, "onCreate");

        ButterKnife.bind(this);

        matchId = getIntent().getLongExtra("matchId", 0);

        initToolbar();
        //initTabs();

        UtilDota.initRetrofitRx()
                .getMatchFullInfoRx(matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        //matchFullInfo -> Log.d(TAG, (matchFullInfo.getDuration()/60) + ":" + (matchFullInfo.getDuration()-((matchFullInfo.getDuration()/60)*60))),
                        this::setMatchFullInfo,
                        error -> Log.e(TAG, error.getLocalizedMessage()),
                        this::initTabs);
    }

    private void initToolbar() {
        toolbar.setTitle(getResources().getString(R.string.match_detail, matchId));

        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_left));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initTabs() {
        viewPager.setOffscreenPageLimit(2);

        TabsFragmentMatchDetailAdapter adapter = new TabsFragmentMatchDetailAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
