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

import com.example.albert.dotasearch.App;
import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.Item;
import com.example.albert.dotasearch.model.ItemsInfoWithSteam;
import com.example.albert.dotasearch.model.LobbyType;
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;

public class StartActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_start;
    private static final String TAG = "StartActivity";
    private static final String KEY = "C35559BD1BEA3B0DC2F958ADF8B7E484";

    public static AppDatabase db;
    private ConnectivityChangedReceiverTest connectivityReceiver;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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

        db = App.get().getDB();
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

    public void loadMainActivity(boolean run){
        if(run){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
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

        Disposable d1 = loadDefaultDataWithApi
                .map(bool -> {
                    if(loadMainActivity) {
                        Log.d(TAG, "insert lobbyType");
                        db.lobbyTypeDao().insertAll(UtilDota.getAllLobbyTypes());
                    }

                    return bool;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        b -> Log.e("onNext", "Success"),
                        error -> Log.e("onError", error.getLocalizedMessage() + "3"),
                        () -> loadMainActivity(loadMainActivity)
                );

        compositeDisposable.add(d1);

    }

    public void runMainActivityAndLoadDefaultData(Boolean success){
        if(success){
            getDataFromApiAndSaveToBD(false);
            loadMainActivity(true);
        } else {
            getDataFromApiAndSaveToBD(true);
        }
    }

    public void checkDefaultInfoInDB(){
        Observable<List<Item>> data1 = db.itemDao().getAllRx().toObservable();
        Observable<List<Hero>> data2 = db.heroDao().getAllRx().toObservable();
        Observable<List<ProPlayer>> data3 = db.proPlayerDao().getAllRx().toObservable();
        Observable<List<LobbyType>> data4 = db.lobbyTypeDao().getAllRx().toObservable();
        Observable<Boolean> zip = Observable.zip(data1, data2, data3, data4, new Function4<List<Item>, List<Hero>, List<ProPlayer>, List<LobbyType>, Boolean>() {
            @Override
            public Boolean apply(List<Item> items, List<Hero> heroes, List<ProPlayer> proPlayers, List<LobbyType> lobbyTypes) throws Exception {
                return items.size() > 0 && heroes.size() > 0 && proPlayers.size() > 0 && lobbyTypes.size() > 0;
            }
        });

        Disposable d2 = zip.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        StartActivity.this::runMainActivityAndLoadDefaultData
                );

        compositeDisposable.add(d2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //compositeDisposable.dispose();
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
            NetworkInfo activeNetwork = null;
            if (cm != null) {
                activeNetwork = cm.getActiveNetworkInfo();
            }
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
