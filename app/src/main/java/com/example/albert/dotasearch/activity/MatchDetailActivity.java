package com.example.albert.dotasearch.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.TabsFragmentMatchDetailAdapter;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.MatchDetailWithItems;
import com.example.albert.dotasearch.model.MatchFullInfo;
import com.example.albert.dotasearch.modelfactory.FactoryForMatchDetailViewModel;
import com.example.albert.dotasearch.modelfactory.FactoryForPlayerInfoViewModel;
import com.example.albert.dotasearch.util.UtilDota;
import com.example.albert.dotasearch.viewModel.MatchDetailViewModel;
import com.example.albert.dotasearch.viewModel.PlayerInfoViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

import static com.example.albert.dotasearch.activity.PlayerInfoActivity.accountId;

public class MatchDetailActivity extends AppCompatActivity {

    public static final String TAG = "MatchDetailActivity";
    public static final int LAYOUT = R.layout.activity_match_detail;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.viewPager) ViewPager viewPager;

    public AppDatabase db;

    public static long matchId;
    public static MatchFullInfo matchFullInfo;
    public static List<Item> items;
    public static Map<Long, Item> itemsMap;
    public static MatchDetailViewModel viewModel;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void setLocalMatchFullInfoAndItems(MatchDetailWithItems matchDetailWithItems){
        matchFullInfo = matchDetailWithItems.getMatchFullInfo();

        items = matchDetailWithItems.getItems();

        itemsMap = new HashMap<>();

        for(int i = 0; i < items.size(); i++){
            itemsMap.put(items.get(i).getId(), items.get(i));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = getApplication().getResources().getConfiguration().orientation;
       // if(orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(this, "Portrait", Toast.LENGTH_SHORT).show();
            setContentView(LAYOUT);
        //}

        db = App.get().getDB();

        Log.d(TAG, "onCreate");

        ButterKnife.bind(this);

        matchId = getIntent().getLongExtra("matchId", 0);

        Toast.makeText(this, matchId + "", Toast.LENGTH_SHORT).show();

        initToolbar();
        initTabs();
        //

        /*viewModel = ViewModelProviders.of(this, new FactoryForMatchDetailViewModel(matchId)).get(MatchDetailViewModel.class);
        LiveData<MatchDetailWithItems> matchDetail = viewModel.getMatchDetail();
        matchDetail.observe(this, new Observer<MatchDetailWithItems>() {
            @Override
            public void onChanged(@Nullable MatchDetailWithItems matchDetailWithItems) {
                if(matchDetailWithItems != null){
                    Log.e(TAG, matchDetailWithItems.getMatchFullInfo().getDuration() + "");
                    setLocalMatchFullInfoAndItems(matchDetailWithItems);
                    initTabs();
                }

            }
        });*/

        /*Observable<MatchFullInfo> matchFullInfoObservable = UtilDota.initRetrofitRx()
                .getMatchFullInfoRx(matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<List<Item>> itemsObservable = db.itemDao().getAllRx()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<MatchDetailAndItems> matchDetailAndItemsObservableZip = Observable.zip(matchFullInfoObservable, itemsObservable, MatchDetailAndItems::new);

        Disposable d1 = matchDetailAndItemsObservableZip.subscribe(
                this::setLocalMatchFullInfoAndItems,
                error -> Log.e(TAG, error.getLocalizedMessage()),
                this::initTabs
        );

        compositeDisposable.add(d1);*/


    }

    private void initToolbar() {
        toolbar.setTitle(getResources().getString(R.string.match_detail, matchId));

        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_arrow_left));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchDetailActivity.super.onBackPressed();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private class MatchDetailAndItems {
        MatchFullInfo matchFullInfo;
        List<Item> items;

        private MatchDetailAndItems(MatchFullInfo matchFullInfo, List<Item> items) {
            this.matchFullInfo = matchFullInfo;
            this.items = items;
        }
    }
}
