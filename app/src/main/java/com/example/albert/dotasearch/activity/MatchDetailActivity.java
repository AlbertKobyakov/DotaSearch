package com.example.albert.dotasearch.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.TabsFragmentMatchDetailAdapter;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.MatchFullInfo;
import com.example.albert.dotasearch.viewModel.MatchDetailViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class MatchDetailActivity extends AppCompatActivity {

    public static final String TAG = "MatchDetailActivity";
    public static final int LAYOUT = R.layout.activity_match_detail;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    public AppDatabase db;

    public static long matchId;
    public static MatchFullInfo matchFullInfo;
    public static List<Item> items;
    public static MatchDetailViewModel viewModel;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(LAYOUT);

        db = App.get().getDB();

        Log.d(TAG, "onCreate");

        ButterKnife.bind(this);

        matchId = getIntent().getLongExtra("matchId", 0);

        Toast.makeText(this, matchId + "", Toast.LENGTH_SHORT).show();

        initToolbar();
        initTabs();
    }

    private void initToolbar() {
        toolbar.setTitle(getResources().getString(R.string.match_detail, matchId));

        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_left));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initTabs() {
        viewPager.setOffscreenPageLimit(2);

        TabsFragmentMatchDetailAdapter adapter = new TabsFragmentMatchDetailAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
