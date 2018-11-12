package com.example.albert.dotasearch;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.example.albert.dotasearch.database.AppDatabase;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class App extends Application implements Application.ActivityLifecycleCallbacks {

    public static App INSTANCE;
    private static final String DATABASE_NAME = "Dota.db";

    private RefWatcher refWatcher;

    private AppDatabase database;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static App get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // create database
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();

        INSTANCE = this;

        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }

    public AppDatabase getDB() {
        return database;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
