package com.example.albert.dotasearch.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.TabsFragmentMatchDetailAdapter;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.MatchFullInfo;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

import static com.example.albert.dotasearch.activity.StartActivity.db;

public class MatchDetailActivity extends AppCompatActivity {

    public static final String TAG = "MatchDetailActivity";
    public static final int LAYOUT = R.layout.activity_match_detail;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.viewPager) ViewPager viewPager;

    public static long matchId;
    public static MatchFullInfo matchFullInfo;
    public static List<Item> items;
    public static Map<Long, Item> itemsMap;

    public void setLocalMatchFullInfoAndItems(MatchDetailAndItems matchDetailAndItems){
        matchFullInfo = matchDetailAndItems.matchFullInfo;

        items = matchDetailAndItems.items;

        itemsMap = new HashMap<>();

        for(int i = 0; i < items.size(); i++){
            itemsMap.put(items.get(i).getId(), items.get(i));
        }
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

        Observable<MatchFullInfo> matchFullInfoObservable = UtilDota.initRetrofitRx()
                .getMatchFullInfoRx(matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<List<Item>> itemsObservable = db.itemDao().getAllRx()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<MatchDetailAndItems> matchDetailAndItemsObservableZip = Observable.zip(matchFullInfoObservable, itemsObservable, new BiFunction<MatchFullInfo, List<Item>, MatchDetailAndItems>() {
            @Override
            public MatchDetailAndItems apply(MatchFullInfo matchFullInfo, List<Item> items) throws Exception {
                return new MatchDetailAndItems(matchFullInfo, items);
            }
        });

        matchDetailAndItemsObservableZip.subscribe(
                this::setLocalMatchFullInfoAndItems,
                error -> Log.e(TAG, error.getLocalizedMessage()),
                this::initTabs
        );
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

    private class MatchDetailAndItems{
        MatchFullInfo matchFullInfo;
        List<Item> items;

        public MatchDetailAndItems(MatchFullInfo matchFullInfo, List<Item> items) {
            this.matchFullInfo = matchFullInfo;
            this.items = items;
        }
    }
}
