package com.example.albert.dotasearch.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.albert.dotasearch.dao.HeroDao;
import com.example.albert.dotasearch.model.Hero;

@Database(entities = {Hero.class}, version = 1)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract HeroDao userDao();
}
