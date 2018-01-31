package com.example.albert.dotasearch.activity;

import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.ItemsInfoWithSteam;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class StartActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_start;
    private static final String TAG = "StartActivity";
    private static final String KEY = "F1F5462B91733A2DFE3E51604517D8B1";

    public static AppDatabase db;
    private ConnectivityChangedReceiverTest connectivityReceiver;
    @BindView(R.id.loading) TextView textViewLoading;
    @BindView(R.id.btn_exit) Button btnExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        ButterKnife.bind(this);

        connectivityReceiver = new ConnectivityChangedReceiverTest();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectivityReceiver, intentFilter);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Dota.db").build();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(connectivityReceiver != null){
            unregisterReceiver(connectivityReceiver);
            connectivityReceiver = null;
            Log.e(TAG, "unregisterReceiver");
        }
    }

    public void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void getDataFromApiAndSaveToBD(boolean loadMainActivity){
        Observable<List<Hero>> heroApi = UtilDota.initRetrofitRx()
                .getAllHeroesRx()
                .doOnNext(UtilDota::storeHeroesInDB); //сохраняю в бд

        Observable<List<ProPlayer>> proPlayerApi = UtilDota.initRetrofitRx()
                .getAllProPlayerRx()
                .doOnNext(UtilDota::storeProPlayersInDB); //сохраняю в бд

        Observable<ItemsInfoWithSteam> itemsSteamApi = UtilDota.initRetrofitRxSteame()
                .getItemInfoSteamRx(KEY)
                .doOnNext(UtilDota::storeItemsSteamInDB); //сохраняю в бд

        Observable<Boolean> loadDefaultDataWithApi = Observable.zip(heroApi, proPlayerApi, itemsSteamApi, new Function3<List<Hero>, List<ProPlayer>, ItemsInfoWithSteam, Boolean>() {
                    @Override
                    public Boolean apply(List<Hero> heroes, List<ProPlayer> proPlayers, ItemsInfoWithSteam itemsInfoWithSteam) throws Exception {
                        return heroes.size() > 0 && itemsInfoWithSteam.getResult().getItems().size() > 0 && proPlayers.size() > 0;
                    }
                });

        if(loadMainActivity){
            loadDefaultDataWithApi.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            b -> Log.e("onNext", "Success"),
                            error -> Log.e("onError", error.getLocalizedMessage() + "3"),
                            this::loadMainActivity
                    );
        } else {
            loadDefaultDataWithApi.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            b -> Log.e("onNext", "Success"),
                            error -> Log.e("onError", error.getLocalizedMessage() + "3"),
                            () -> Log.d(TAG, "onComplete")
                    );
        }


    }

    public void runMainActivityAndLoadDefaultData(Boolean success){
        if(success){
            getDataFromApiAndSaveToBD(false);
            loadMainActivity();
        } else {
            getDataFromApiAndSaveToBD(true);
        }
    }

    public void checkDefaultInfoInDB(){
        Observable<List<Item>> data1 = db.itemDao().getAllRx().toObservable();
        Observable<List<Hero>> data2 = db.heroDao().getAllRx().toObservable();
        Observable<List<ProPlayer>> data3 = db.proPlayerDao().getAllRx().toObservable();
        Observable<Boolean> zip = Observable.zip(data1, data2, data3, new Function3<List<Item>, List<Hero>, List<ProPlayer>, Boolean>() {
            @Override
            public Boolean apply(List<Item> items, List<Hero> heroes, List<ProPlayer> proPlayers) throws Exception {
                return items.size() > 0 && heroes.size() > 0 && proPlayers.size() > 0;
            }
        });
        zip.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        StartActivity.this::runMainActivityAndLoadDefaultData
                );
    }


    @OnClick(R.id.btn_exit)
    public void onClick(View view){
        finish();
    }

    private class ConnectivityChangedReceiverTest extends BroadcastReceiver {

        public static final String TAG = "ConnectivyReceiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnected()){
                Log.e(TAG, "Data connected");
                textViewLoading.setText("Загрузка...");
                if(btnExit.getVisibility() == View.VISIBLE){
                    btnExit.setVisibility(View.INVISIBLE);
                }

                checkDefaultInfoInDB();

            } else {
                textViewLoading.setText("Интернет соединение отсутствует...");
                btnExit.setVisibility(View.VISIBLE);
                Log.e(TAG, "Data not connected");
            }
        }
    }
}
