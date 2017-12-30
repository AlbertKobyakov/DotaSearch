package com.example.albert.dotasearch.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.albert.dotasearch.dao.HeroDao;
import com.example.albert.dotasearch.dao.ProPlayerDao;
import com.example.albert.dotasearch.model.Hero;
import com.example.albert.dotasearch.model.ProPlayer;

@Database(entities = {Hero.class, ProPlayer.class}, version = 1)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract HeroDao heroDao();
        public abstract ProPlayerDao proPlayerDao();
}
