package com.example.albert.dotasearch;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.albert.dotasearch.database.AppDatabase;

public class App extends Application {

    public static App INSTANCE;
    private static final String DATABASE_NAME = "Dota.db";

    private AppDatabase database;

    public static App get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // create database
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();

        INSTANCE = this;
    }

    public AppDatabase getDB() {
        return database;
    }
}
