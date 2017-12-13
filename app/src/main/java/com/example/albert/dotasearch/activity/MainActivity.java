package com.example.albert.dotasearch.activity;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.albert.dotasearch.R;
import com.example.albert.dotasearch.ServiceGenerator;
import com.example.albert.dotasearch.TabsFragmentAdapter;
import com.example.albert.dotasearch.database.AppDatabase;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.retrofit.DotaClient;
import com.example.albert.dotasearch.util.UtilDota;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.net.HttpURLConnection;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    public Toolbar toolbar;
    public static AppDatabase db;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        ButterKnife.bind(this);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Dota.db").build();

        new DatabaseAsync().execute();

        initToolbar();
        initTabs();


        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.opendota.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        retrofit.create(DotaClient.class)
                .getAllHeroesRx()
                .map(this::getNewList)
                .doOnNext(s -> Log.e("onNext", s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(System.out::println);*/
    }

    private void writeToBD(List<Hero> heroList){
        db.userDao().insertAll(heroList);
    }

    private String getNewList(List<Hero> list){
        return "Hello";
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initTabs() {
        viewPager = findViewById(R.id.viewPager);

        viewPager.setOffscreenPageLimit(2);

        TabsFragmentAdapter adapter = new TabsFragmentAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        //enable search to toolbar
        //toolbar.inflateMenu(R.menu.menu_fragment);
    }

    private static class DatabaseAsync extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... views) {
            List<Hero> heroes = db.userDao().getAll();
            if(heroes.size() == 0){
                Log.e("DatabaseAsyncMain", "Table Hero is empty");
                writeAllHeroesToBD();
                //Snackbar.make(, "Table Hero is empty", Snackbar.LENGTH_LONG).show();
            } else if(heroes.size() != 0){
                Log.e("DatabaseAsyncMain", "Table Hero not is empty");
                UpdateAllHeroesBD();
                //Snackbar.make(, "Table Hero is empty", Snackbar.LENGTH_LONG).show();
            }
            return null;
        }

        public void writeAllHeroesToBD(){
            DotaClient matchService = new UtilDota().initRetrofit("https://api.opendota.com", context);
            Call<List<Hero>> call = matchService.getAllHeroes();
            call.enqueue(new Callback<List<Hero>>() {
                @Override
                public void onResponse(@NonNull Call<List<Hero>> call, @NonNull Response<List<Hero>> response) {
                    if (response.isSuccessful()) {
                        Log.e("OKHTTP", "ЗАполняю таблицу Hero впервые");
                        final List<Hero> heroes = response.body();

                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                db.userDao().insertAll(heroes);
                            }
                        });

                    } else {
                        //Snackbar.make(view, "Что-то пошло не так", Snackbar.LENGTH_LONG).show();
                        Log.e("writeAllHeroesToBD", "Что-то пошло не так");
                    }
                }

                @Override
                public void onFailure(Call<List<Hero>> call, Throwable t) {
                    // something went completely south (like no internet connection)
                    Log.d("Error", t.getMessage());
                }
            });
        }

        public void UpdateAllHeroesBD(){
            DotaClient matchService = new UtilDota().initRetrofit("https://api.opendota.com", context);
            Call<List<Hero>> call = matchService.getAllHeroes();
            call.enqueue(new Callback<List<Hero>>() {
                @Override
                public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                    if (response.isSuccessful() && response.raw().networkResponse().code() != HttpURLConnection.HTTP_NOT_MODIFIED) {
                        Log.e("OKHTTP", "Обновляю таблицу Hero");
                        final List<Hero> heroes = response.body();

                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                db.userDao().updateAll(heroes);
                            }
                        });

                    } else if (response.isSuccessful() && response.raw().networkResponse().code() == HttpURLConnection.HTTP_NOT_MODIFIED) {
                        Log.e("OKHTTP", "Изменения не требуются");
                    } else {
                        //Snackbar.make(view, "Что-то пошло не так", Snackbar.LENGTH_LONG).show();
                        Log.e("writeAllHeroesToBD", "Что-то пошло не так");
                    }
                }

                @Override
                public void onFailure(Call<List<Hero>> call, Throwable t) {
                    // something went completely south (like no internet connection)
                    Log.d("Error", t.getMessage());
                }
            });
        }
    }
}
