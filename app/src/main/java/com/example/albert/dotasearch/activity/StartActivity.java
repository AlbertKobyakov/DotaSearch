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
import com.example.albert.dotasearch.model.ProPlayer;
import com.example.albert.dotasearch.util.UtilDota;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class StartActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_start;
    private static final String TAG = "StartActivity";

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

    public void getDataFromApiAndSaveToBD(){
        Observable<List<Hero>> heroApi = UtilDota.initRetrofitRx().getAllHeroesRx()
                .doOnNext(UtilDota::storeHeroesInDB) //сохраняю в бд
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<List<ProPlayer>> proPlayerApi = UtilDota.initRetrofitRx().getAllProPlayerRx()
                .doOnNext(UtilDota::storeProPlayersInDB) //сохраняю в бд
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<LoadDataHeroAndProPlayerApi> loadDefaultDataWithApi = Observable.zip(heroApi, proPlayerApi, new BiFunction<List<Hero>, List<ProPlayer>, LoadDataHeroAndProPlayerApi>() {
            @Override
            public LoadDataHeroAndProPlayerApi apply(List<Hero> heroes, List<ProPlayer> proPlayers) throws Exception {
                return new LoadDataHeroAndProPlayerApi(heroes, proPlayers);
            }
        });

        loadDefaultDataWithApi.subscribe(
                loadDataHeroAndProPlayerApi -> Log.e("onNext", loadDataHeroAndProPlayerApi.heroes.size() + " "),
                error -> Log.e("onError", error.getLocalizedMessage()),
                this::loadMainActivity
        );
    }

    @OnClick(R.id.btn_exit)
    public void onClick(View view){
        finish();
    }

    private class LoadDataHeroAndProPlayerApi{
        List<Hero> heroes;
        List<ProPlayer> proPlayers;

        public LoadDataHeroAndProPlayerApi(List<Hero> heroes, List<ProPlayer> proPlayers) {
            this.heroes = heroes;
            this.proPlayers = proPlayers;
        }
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
                getDataFromApiAndSaveToBD();
            } else {
                textViewLoading.setText("Интернет соединение отсутствует...");
                btnExit.setVisibility(View.VISIBLE);
                Log.e(TAG, "Data not connected");
            }
        }
    }
}
