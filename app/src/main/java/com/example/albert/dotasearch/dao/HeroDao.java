package com.example.albert.dotasearch.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.albert.dotasearch.model.Hero;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface HeroDao {
    @Query("SELECT * FROM hero")
    List<Hero> getAll();

    @Query("SELECT * FROM hero")
    Single<List<Hero>> getAllRx();

    @Insert
    void insertAll(List<Hero> heroes);

    @Delete
    void delete(Hero user);

    @Update
    void updateAll(List<Hero> heroes);
}
